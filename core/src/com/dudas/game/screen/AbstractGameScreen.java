package com.dudas.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.dudas.game.assets.Assets;

/**
 * Created by OLO on 19. 1. 2015
 */
public abstract class AbstractGameScreen implements Screen {

    protected Game game;

    public AbstractGameScreen(Game game) {
        this.game = game;
    }

    public abstract void render(float deltaTime);

    public abstract void resize(int width, int height);

    public abstract void show();

    public abstract void hide();

    public abstract void pause();

    public void resume() {
        Assets.instance.init(new AssetManager());
    }

    public void dispose() {
        Assets.instance.dispose();
    }
}
