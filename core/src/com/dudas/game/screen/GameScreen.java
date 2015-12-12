package com.dudas.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.dudas.game.di.DaggerStageComponent;
import com.dudas.game.di.StageComponent;
import com.dudas.game.di.StageModule;
import com.dudas.game.stage.GameStage;
import com.dudas.game.stage.MainStage;

import javax.inject.Inject;

/**
 * Created by OLO on 31. 1. 2015
 */
public class GameScreen extends AbstractGameScreen {

    private static final String TAG = GameScreen.class.getName();

    @Inject
    MainStage mainStage;
    @Inject
    GameStage gameStage;
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
        StageComponent stageComponent = DaggerStageComponent.builder()
                .stageModule(new StageModule())
                .build();
        stageComponent.inject(this);

//        SpriteBatch batch = new SpriteBatch();
//        mainStage = new MainStage(batch);
//        Board board = AppInjector.createBoard();
//        gameStage = new GameStage(mainStage.getBatch(), board);
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
