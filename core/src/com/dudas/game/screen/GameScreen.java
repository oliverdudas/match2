package com.dudas.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dudas.game.Constants;
import com.dudas.game.controller.BoardController;
import com.dudas.game.controller.GameController;
import com.dudas.game.stage.GameStage;
import com.dudas.game.stage.MainStage;

/**
 * Created by OLO on 31. 1. 2015
 */
public class GameScreen extends AbstractGameScreen {

    private static final String TAG = GameScreen.class.getName();

    private GameController gameController;
    private BoardController boardController;
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
            gameController.update(deltaTime);
            boardController.update(deltaTime);
        }

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
        SpriteBatch batch = new SpriteBatch();
        mainStage = new MainStage(batch);
        gameStage = new GameStage(batch, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        gameController = new GameController();
        boardController = new BoardController();
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide() {
        Gdx.input.setCatchBackKey(false);
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
        mainStage.dispose();
        gameStage.dispose();
    }

}
