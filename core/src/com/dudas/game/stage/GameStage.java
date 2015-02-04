package com.dudas.game.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dudas.game.Constants;
import com.dudas.game.util.ExtendViewportWithRightCamera;

/**
 * Created by foxy on 04/02/2015.
 */
public class GameStage extends Stage {

    private Group group;

    public GameStage(Batch batch, float boardWidth, float boardHeight) {
        super(new ExtendViewportWithRightCamera(boardWidth, boardHeight), batch);
        init();
    }

    private void init() {
        initGroup();
        initBoardActor();
        initGemActors();
    }

    private void initGroup() {
        group = new Group();
        addActor(group);
        group.setBounds(Constants.INITIAL_X, Constants.INITIAL_Y, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
    }

    private void initBoardActor() {
        BoardActor boardActor = new BoardActor(Constants.INITIAL_X, Constants.INITIAL_Y, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        group.addActor(boardActor);
    }

    private void initGemActors() {
        group.addActor(produceGem(0, 0));
        group.addActor(produceGem(0, 1));
        group.addActor(produceGem(0, 2));
        group.addActor(produceGem(0, 3));
        group.addActor(produceGem(0, 4));
        group.addActor(produceGem(0, 5));
        group.addActor(produceGem(0, 6));
        group.addActor(produceGem(0, 7));
        group.addActor(produceGem(0, 8));

        group.addActor(produceGem(0, 8));
        group.addActor(produceGem(1, 8));
        group.addActor(produceGem(2, 8));
        group.addActor(produceGem(3, 8));
        group.addActor(produceGem(4, 8));
        group.addActor(produceGem(5, 8));
        group.addActor(produceGem(6, 8));
        group.addActor(produceGem(7, 8));

        group.addActor(produceGem(8, 8));
        group.addActor(produceGem(8, 7));
        group.addActor(produceGem(8, 6));
        group.addActor(produceGem(8, 5));
        group.addActor(produceGem(8, 4));
        group.addActor(produceGem(8, 3));
        group.addActor(produceGem(8, 2));
        group.addActor(produceGem(8, 1));

        group.addActor(produceGem(8, 0));
        group.addActor(produceGem(7, 0));
        group.addActor(produceGem(6, 0));
        group.addActor(produceGem(5, 0));
        group.addActor(produceGem(4, 0));
        group.addActor(produceGem(3, 0));
        group.addActor(produceGem(2, 0));
        group.addActor(produceGem(1, 0));

        group.addActor(produceGem(5, 3));
        group.addActor(produceGem(4, 3));
        group.addActor(produceGem(3, 3));
        group.addActor(produceGem(2, 6));
        group.addActor(produceGem(6, 6));
    }

    private Actor produceGem(float x, float y) {
        return new GemActor(x, y, Constants.GEM_WIDTH, Constants.GEM_HEIGHT);
    }

    public void resize(int width, int height) {
        getViewport().update(width, height);
    }
}
