package com.dudas.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dudas.game.controller.Board;
import com.dudas.game.stage.GameStage;
import com.dudas.game.stage.MainStage;
import com.dudas.game.util.inject.AppInjector;
import com.google.inject.Inject;

/**
 * Created by OLO on 31. 1. 2015
 */
public class GameScreen extends AbstractGameScreen {

    private static final String TAG = GameScreen.class.getName();

    private MainStage mainStage;
    private GameStage gameStage;
    private boolean paused;

    @Inject
    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void render(float deltaTime) {

        // Do not update game world when paused.
        if (!paused) {
        }

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.draw();

        gameStage.act(deltaTime);
        gameStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        mainStage.resize(width, height);
        gameStage.resize(width, height);
    }

    @Override
    public void show() {
        SpriteBatch batch = new SpriteBatch();
        mainStage = new MainStage(batch);
        Board board = AppInjector.createBoard();
        gameStage = new GameStage(mainStage.getBatch(), board);
//        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide() {
//        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        // Only called on Android!
        paused = false;
    }

    @Override
    public void dispose() {
        super.dispose();
        mainStage.dispose();
        gameStage.dispose();
    }

}
