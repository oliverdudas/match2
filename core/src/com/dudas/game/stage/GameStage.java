package com.dudas.game.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dudas.game.util.ExtendViewportWithRightCamera;

/**
 * Created by foxy on 04/02/2015.
 */
public class GameStage extends Stage {

    public static final int GAME_STAGE_WIDTH = 9;
    public static final int GAME_STAGE_HEIGHT = 9;

    public GameStage(Batch batch) {
        super(new ExtendViewportWithRightCamera(GAME_STAGE_WIDTH, GAME_STAGE_HEIGHT), batch);
        init();
    }

    private void init() {
        addActor(new PixmapGemActor(0, 0));
        addActor(new PixmapGemActor(0, 1));
        addActor(new PixmapGemActor(0, 2));
        addActor(new PixmapGemActor(0, 3));
        addActor(new PixmapGemActor(0, 4));
        addActor(new PixmapGemActor(0, 5));
        addActor(new PixmapGemActor(0, 6));
        addActor(new PixmapGemActor(0, 7));
        addActor(new PixmapGemActor(0, 8));

        addActor(new PixmapGemActor(0, 8));
        addActor(new PixmapGemActor(1, 8));
        addActor(new PixmapGemActor(2, 8));
        addActor(new PixmapGemActor(3, 8));
        addActor(new PixmapGemActor(4, 8));
        addActor(new PixmapGemActor(5, 8));
        addActor(new PixmapGemActor(6, 8));
        addActor(new PixmapGemActor(7, 8));

        addActor(new PixmapGemActor(8, 8));
        addActor(new PixmapGemActor(8, 7));
        addActor(new PixmapGemActor(8, 6));
        addActor(new PixmapGemActor(8, 5));
        addActor(new PixmapGemActor(8, 4));
        addActor(new PixmapGemActor(8, 3));
        addActor(new PixmapGemActor(8, 2));
        addActor(new PixmapGemActor(8, 1));

        addActor(new PixmapGemActor(8, 0));
        addActor(new PixmapGemActor(7, 0));
        addActor(new PixmapGemActor(6, 0));
        addActor(new PixmapGemActor(5, 0));
        addActor(new PixmapGemActor(4, 0));
        addActor(new PixmapGemActor(3, 0));
        addActor(new PixmapGemActor(2, 0));
        addActor(new PixmapGemActor(1, 0));

        addActor(new PixmapGemActor(5, 3));
        addActor(new PixmapGemActor(4, 3));
        addActor(new PixmapGemActor(3, 3));
        addActor(new PixmapGemActor(2, 6));
        addActor(new PixmapGemActor(6, 6));
    }

    public void resize(int width, int height) {
        getViewport().update(width, height);
    }
}
