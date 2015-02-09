package com.dudas.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.CountdownEventAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Pool;
import com.dudas.game.Board;
import com.dudas.game.Constants;
import com.dudas.game.Gem;
import com.dudas.game.event.action.ClearDoneEvent;
import com.dudas.game.event.action.FireEventAction;
import com.dudas.game.event.matchgame.MatchGameEventManager;
import com.dudas.game.event.matchgame.MatchGameListener;
import com.dudas.game.model.GemType;
import com.dudas.game.util.ExtendViewportWithRightCamera;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by foxy on 04/02/2015.
 */
public class GameStage extends Stage implements MatchGameListener {

    public static final String TAG = GameStage.class.getName();
    public static final float ACTION_DURATION = 0.2f;

    private Group group;
    private Board board;
    private Vector2 touchPosition;
    private GemActor selectedActor;
    private GemActor hitActor;
    private boolean swapEnabled;
    private ArrayMap<Gem, GemActor> gemActors;
    private Pool<MoveToAction> moveToActionPool;
    private Pool<ScaleToAction> scaleToActionPool;
    private Pool<SwapCompleteCallback> swapCompleteCallbackPool;
    private Pool<ClearCompleteCallback> clearCompleteCallbackPool;
    private Pool<FallCompleteCallback> fallCompleteCallbackPool;

    public GameStage(Batch batch, Board board) {
        super(new ExtendViewportWithRightCamera(board.getWidth(), board.getHeight()), batch);
        this.board = board;
        this.touchPosition = new Vector2();
        this.swapEnabled = true;
        this.gemActors = new ArrayMap<Gem, GemActor>();
        this.moveToActionPool = new Pool<MoveToAction>(){
            protected MoveToAction newObject(){
                return new MoveToAction();
            }
        };
        this.scaleToActionPool = new Pool<ScaleToAction>(){
            protected ScaleToAction newObject(){
                return new ScaleToAction();
            }
        };
        this.swapCompleteCallbackPool = new Pool<SwapCompleteCallback>(){
            protected SwapCompleteCallback newObject(){
                return new SwapCompleteCallback();
            }
        };
        this.clearCompleteCallbackPool = new Pool<ClearCompleteCallback>(){
            protected ClearCompleteCallback newObject(){
                return new ClearCompleteCallback();
            }
        };
        this.fallCompleteCallbackPool = new Pool<FallCompleteCallback>(){
            protected FallCompleteCallback newObject(){
                return new FallCompleteCallback();
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
                selectedActor.block();
                hitActor.block();
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

        removeSelection();
    }

    private void handleSwapAction(final GemActor gemActor, float x, float y) {
        handleSwapAction(gemActor, x, y, null);
    }

    private void handleSwapAction(final GemActor gemActor, float x, float y, SwapCompleteCallback swapCompleteCallback) {
        MoveToAction fromTo = moveToActionPool.obtain();
        fromTo.setPosition(x, y);
        fromTo.setDuration(ACTION_DURATION);

        if (swapCompleteCallback != null) {
            gemActor.addAction(sequence(fromTo, run(swapCompleteCallback)));
        } else {
            gemActor.addAction(fromTo);
        }
    }

    @Override
    public void onBackSwap(Gem fromGem, Gem toGem) {
        GemActor fromActor = gemActors.get(fromGem);
        GemActor toActor = gemActors.get(toGem);

        // the gem has ben changed in board, now the gemActor must be synchronized
        handleSwapAction(fromActor, fromGem.getX(), fromGem.getY());
        SwapCompleteCallback swapCompleteCallback = swapCompleteCallbackPool.obtain();
        swapCompleteCallback.addSwapPair(fromActor, toActor);
        swapCompleteCallback.setBackSwap(true);
        handleSwapAction(toActor, toGem.getX(), toGem.getY(), swapCompleteCallback);
    }

    @Override
    public void onClearSuccess(final Array<Gem> gems, Gem unclearedGem) {
        Gdx.app.debug(TAG, "ClearSuccess(size): " + gems.size);

        if (unclearedGem != null) {
            gemActors.get(unclearedGem).setReady();
        }

        CountdownEventAction<ClearDoneEvent> countdownEventAction = new CountdownEventAction<ClearDoneEvent>(ClearDoneEvent.class, gems.size);
        countdownEventAction.setTarget(group);
        group.addAction(sequence(Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        for (Gem gem : gems) {
                            final GemActor gemActor = gemActors.get(gem);
                            gemActor.block();

                            ScaleToAction scaleToAction = scaleToActionPool.obtain();
                            scaleToAction.setScale(0);
                            scaleToAction.setDuration(ACTION_DURATION);

                            ClearCompleteCallback clearCompleteCallback = clearCompleteCallbackPool.obtain();
                            clearCompleteCallback.addGemActor(gemActor);
                            clearCompleteCallback.addPool(clearCompleteCallbackPool);
                            gemActor.addAction(sequence(
                                    scaleToAction,
                                    Actions.run(clearCompleteCallback),
                                    new FireEventAction(new ClearDoneEvent(gemActor))
                            ));
                        }
                    }
                }),
                countdownEventAction,
                run(new Runnable() {
                    @Override
                    public void run() {
                        board.fall(gems);
                    }
                })));
    }

    @Override
    public void onClearFail(float fromX, float fromY, float toX, float toY) {
        Gdx.app.debug(TAG, "ClearFail: (" + fromX + ", " + fromY + ", " + toX + ", " + toY + ")" );
        board.backSwap(fromX, fromY, toX, toY);
    }

    @Override
    public void onFall(final Array<Gem> gems) {
        Gdx.app.debug(TAG, "Fall: count: " + gems.size);

//        CountdownEventAction<FallDoneEvent> countdownEventAction = new CountdownEventAction<FallDoneEvent>(FallDoneEvent.class, gems.size);
//        countdownEventAction.setTarget(group);
        group.addAction(sequence(Actions.run(new Runnable() {
            @Override
            public void run() {
                for (Gem gem : gems) {
                    GemActor gemActor = gemActors.get(gem);

                    if (GemType.EMPTY.equals(gemActor.getType())) {
                        gemActor.setY(getHeight());
                        gemActor.setType(GemType.getRandom());
                        gemActor.setVisible(true);
                    }

                    MoveToAction moveToAction = moveToActionPool.obtain();
                    moveToAction.setPosition(gem.getX(), gem.getY());
                    moveToAction.setDuration(ACTION_DURATION);

                    FallCompleteCallback fallCompleteCallback = fallCompleteCallbackPool.obtain();
                    fallCompleteCallback.addGemActor(gemActor);
                    fallCompleteCallback.addPool(fallCompleteCallbackPool);
                    gemActor.addAction(sequence(
                            moveToAction,
                            Actions.run(fallCompleteCallback)
//                            ,
//                            new FireEventAction(new FallDoneEvent(gemActor))
                    ));
                }
            }
        })));

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        removeSelection();
        enableSwap();
        return true;
    }

    private void removeSelection() {
        selectedActor = null;
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
            gemActors.put(gem, gemActor);
            group.addActor(gemActor);
        }
    }

    private GemActor produceGemActor(float x, float y) { // TODO maybe create a provider for grm actors
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
