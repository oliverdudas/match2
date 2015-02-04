package com.dudas.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dudas.game.stage.GameStage;
import com.dudas.game.stage.MainStage;
import com.dudas.game.util.ExtendViewportWithRightCamera;

/**
 * Created by OLO on 31. 1. 2015
 */
public class ExampleScreen extends AbstractGameScreen {

    private static final String TAG = ExampleScreen.class.getName();


    private SpriteBatch batch;

    private MainStage mainStage;
    private GameStage gameStage;

    public ExampleScreen(Game game) {
        super(game);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.draw();
        gameStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        mainStage.resize(width, height);
        gameStage.resize(width, height);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        mainStage = new MainStage(batch);
        gameStage = new GameStage(batch);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {
        mainStage.dispose();
        gameStage.dispose();
    }

}
