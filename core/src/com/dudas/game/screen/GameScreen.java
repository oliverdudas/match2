package com.dudas.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dudas.game.Constants;
import com.dudas.game.controller.BoardController;
import com.dudas.game.provider.TestGemsProvider;
import com.dudas.game.stage.GameStage;
import com.dudas.game.stage.MainStage;

/**
 * Created by OLO on 31. 1. 2015
 */
public class GameScreen extends AbstractGameScreen {

    private static final String TAG = GameScreen.class.getName();

    private MainStage mainStage;
    private GameStage gameStage;
    private boolean paused;

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
        BoardController board = new BoardController(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT, new TestGemsProvider());
        gameStage = new GameStage(batch, board);
//        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(gameStage);
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
