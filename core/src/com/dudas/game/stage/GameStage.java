package com.dudas.game.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.dudas.game.Board;
import com.dudas.game.Constants;
import com.dudas.game.Gem;
import com.dudas.game.util.ExtendViewportWithRightCamera;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by foxy on 04/02/2015.
 */
public class GameStage extends Stage {

    public static final String TAG = GameStage.class.getName();

    private Group group;
    private Board board;
    private Vector2 touchPosition;
    private Actor selectedActor;
    private boolean swapEnabled;

    public GameStage(Batch batch, float boardWidth, float boardHeight, Board board) {
        super(new ExtendViewportWithRightCamera(boardWidth, boardHeight), batch);
        this.board = board;
        this.touchPosition = new Vector2();
        clearSelection();
        swapEnabled = true;
        init();
    }

    private void init() {
        initGroup();
        initBoardActor();
        initGemActors();
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean touchDragged = super.touchDragged(screenX, screenY, pointer);

        if (isSwapEnabled()) {

            screenToStageCoordinates(touchPosition.set(screenX, screenY));

            final Actor hitActor = hit(touchPosition.x, touchPosition.y, true);
            boolean isActorHit = hitActor != null;
            boolean existSelectedActor = selectedActor != null;
            if (existSelectedActor && isActorHit && hitActor != selectedActor) {
                FloatArray swapPositions = board.swap(selectedActor.getX(), selectedActor.getY(), hitActor.getX(), hitActor.getY());

                MoveToAction fromTo = new MoveToAction();
                fromTo.setPosition(swapPositions.get(0), swapPositions.get(1));
                fromTo.setDuration(0.2f);
                final Gem fromGem = (Gem) selectedActor.getUserObject();
                selectedActor.addAction(sequence(fromTo, run(new Runnable() {
                    @Override
                    public void run() {
                        fromGem.setReady();
                    }
                })));

                MoveToAction toFrom = new MoveToAction();
                toFrom.setPosition(swapPositions.get(2), swapPositions.get(3));
                toFrom.setDuration(0.2f);
                final Gem toGem = (Gem) hitActor.getUserObject();
                hitActor.addAction(sequence(toFrom, run(new Runnable() {
                    @Override
                    public void run() {
                        toGem.setReady();
                    }
                })));

                enableSwap();
                clearSelection();
                return true;
            } else {
                selectedActor = hitActor;
            }
            return touchDragged;
        }
        return false;
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
            GemActor gemActor = new GemActor(gem.getX(), gem.getY(), Constants.GEM_WIDTH, Constants.GEM_HEIGHT);
            gemActor.setUserObject(gem);
            group.addActor(gemActor);
        }
    }

    private Actor produceGem(float x, float y) {
        return new GemActor(x, y, Constants.GEM_WIDTH, Constants.GEM_HEIGHT);
    }

    public void resize(int width, int height) {
        getViewport().update(width, height);
    }

    private void disableSwap() {
        swapEnabled = false;
    }

    private void enableSwap() {
        swapEnabled = true;
    }

    private boolean isSwapEnabled() {
        return swapEnabled;
    }
}
