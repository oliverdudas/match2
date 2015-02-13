package com.dudas.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.dudas.game.Board;
import com.dudas.game.Constants;
import com.dudas.game.event.matchgame.MatchGameEventManager;
import com.dudas.game.event.matchgame.MatchGameListener;
import com.dudas.game.util.ExtendViewportWithRightCamera;

/**
 * Created by foxy on 04/02/2015.
 */
public class GameStage extends Stage {

    private Group boardGroup;
    private Board board;
    private Vector2 touchPosition;
    private GemActor selectedActor;
    private GemActor hitActor;
    private boolean swapEnabled;

    public GameStage(Batch batch, Board board) {
        super(new ExtendViewportWithRightCamera(board.getWidth(), board.getHeight()), batch);
        this.board = board;
        this.touchPosition = new Vector2();
        this.swapEnabled = true;
        init();
    }

    private void init() {
        initGroup();
        initBoardActor();
        MatchGameEventManager.setMatchGameProcessor(new BoardEventRenderer(board, boardGroup));
        Gdx.input.setInputProcessor(this);
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
                removeSelection();
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
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        removeSelection();
        enableSwap();
        return true;
    }

    private void removeSelection() {
        selectedActor = null;
    }

    private void initGroup() {
        boardGroup = new Group();
        addActor(boardGroup);
        boardGroup.setTouchable(Touchable.childrenOnly);
        boardGroup.setBounds(Constants.INITIAL_X, Constants.INITIAL_Y, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
    }

    private void initBoardActor() {
        BoardActor boardActor = new BoardActor(Constants.INITIAL_X, Constants.INITIAL_Y, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        boardGroup.addActor(boardActor);
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
