package com.dudas.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.dudas.game.Board;
import com.dudas.game.Constants;
import com.dudas.game.Gem;
import com.dudas.game.event.MatchGameEventManager;
import com.dudas.game.event.MatchGameListener;
import com.dudas.game.util.ExtendViewportWithRightCamera;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by foxy on 04/02/2015.
 */
public class GameStage extends Stage implements MatchGameListener {

    public static final String TAG = GameStage.class.getName();
    public static final float SWAP_DURATION = 0.2f;

    private Group group;
    private Board board;
    private Vector2 touchPosition;
    private GemActor selectedActor;
    private GemActor hitActor;
    private boolean swapEnabled;
    private Pool<MoveToAction> moveToActionPool;
    private Pool<SwapCompleteCallback> swapCompleteCallbackPool;

    public GameStage(Batch batch, Board board) {
        super(new ExtendViewportWithRightCamera(board.getWidth(), board.getHeight()), batch);
        this.board = board;
        this.touchPosition = new Vector2();
        this.swapEnabled = true;
        this.moveToActionPool = new Pool<MoveToAction>(){
            protected MoveToAction newObject(){
                return new MoveToAction();
            }
        };
        this.swapCompleteCallbackPool = new Pool<SwapCompleteCallback>(){
            protected SwapCompleteCallback newObject(){
                return new SwapCompleteCallback();
            }
        };
        init();
    }

    private void init() {
        initGroup();
        initBoardActor();
        initGemActors();
        MatchGameEventManager.setMatchGameProcessor(this);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean touchDragged = super.touchDragged(screenX, screenY, pointer);

        if (isSwapEnabled()) {
            screenToStageCoordinates(touchPosition.set(screenX, screenY));
            hitActor = (GemActor) hit(touchPosition.x, touchPosition.y, true);
            if (isSwapPossible()) {
                disableGemActorSwap();
                board.swap(selectedActor.getX(), selectedActor.getY(), hitActor.getX(), hitActor.getY());
                return true;
            } else {
                selectedActor = hitActor;
            }
            return touchDragged;
        }
        return false;
    }

    private boolean isSwapPossible() {
        return selectedActor != null
                && hitActor != null
                && hitActor != selectedActor
                && hitActor.isReady()
                && selectedActor.isReady()
                && isVerticalOrHorizontalSwap();
    }

    private boolean isVerticalOrHorizontalSwap() {
        return selectedActor.getGem().getX() == hitActor.getGem().getX()
                || selectedActor.getGem().getY() == hitActor.getGem().getY();
    }

    @Override
    public void onSwap(float fromX, float fromY, float toX, float toY) {
        SwapCompleteCallback swapCompleteCallback = swapCompleteCallbackPool.obtain();
        swapCompleteCallback.addSwapPair(selectedActor, hitActor);
        swapCompleteCallback.addBoard(board);
        handleSwapAction(selectedActor, fromX, fromY);
        handleSwapAction(hitActor, toX, toY, swapCompleteCallback);

        clearSelection();
    }

    private void handleSwapAction(final GemActor gemActor, float fromX, float fromY) {
        handleSwapAction(gemActor, fromX, fromY, null);
    }

    private void handleSwapAction(final GemActor gemActor, float fromX, float fromY, SwapCompleteCallback swapCompleteCallback) {
        MoveToAction fromTo = moveToActionPool.obtain();
        fromTo.setPosition(fromX, fromY);
        fromTo.setDuration(SWAP_DURATION);

        if (swapCompleteCallback != null) {
            gemActor.addAction(sequence(fromTo, run(swapCompleteCallback)));
        } else {
            gemActor.addAction(fromTo);
        }
    }

    @Override
    public void onClearSuccess(Array<Gem> gems) {
        Gdx.app.debug(TAG, "ClearSuccess(size): " + gems.size);
    }

    @Override
    public void onClearFail(float fromX, float fromY, float toX, float toY) {
        Gdx.app.debug(TAG, "ClearFail: (" + fromX + ", " + fromY + ", " + toX + ", " + toY + ")" );
    }

    @Override
    public void onFall(Object eventData) {

    }

    private void clearSelection() {
        selectedActor = null;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        clearSelection();
        enableSwap();
        return true;
    }

    private void initGroup() {
        group = new Group();
        addActor(group);
        group.setTouchable(Touchable.childrenOnly);
        group.setBounds(Constants.INITIAL_X, Constants.INITIAL_Y, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
    }

    private void initBoardActor() {
        BoardActor boardActor = new BoardActor(Constants.INITIAL_X, Constants.INITIAL_Y, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        group.addActor(boardActor);
    }

    private void initGemActors() {
        Array<Gem> gems = board.getGems();

        for (Gem gem : gems) {
            GemActor gemActor = produceGemActor(gem.getX(), gem.getY());
            gemActor.setUserObject(gem);
            group.addActor(gemActor);
        }
    }

    private GemActor produceGemActor(float x, float y) {
        return new GemActor(x, y, Constants.GEM_WIDTH, Constants.GEM_HEIGHT);
    }

    public void resize(int width, int height) {
        getViewport().update(width, height);
    }

    /**
     * Prevent continuus swaping with one swipe
     */
    private void disableGemActorSwap() {
        swapEnabled = false;
    }

    /**
     * Enable only one swap with one finger swipe.
     */
    private void enableSwap() {
        swapEnabled = true;
    }

    private boolean isSwapEnabled() {
        return swapEnabled;
    }

}
