package com.dudas.game.util.inject.module;

import com.badlogic.gdx.Game;
import com.google.inject.AbstractModule;

/**
 * Created by OLO on 15. 3. 2015
 */
public class GameScreenModule extends AbstractModule {

    private Game game;

    public GameScreenModule(Game game) {
        this.game = game;
    }

    @Override
    protected void configure() {
        bind(Game.class).toInstance(game);
    }
}
