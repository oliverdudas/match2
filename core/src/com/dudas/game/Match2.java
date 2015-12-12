package com.dudas.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.dudas.game.assets.Assets;
import com.dudas.game.screen.GameScreen;

public class Match2 extends Game {

    @Override
    public void create() {
        // Set Libgdx log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        // Load assets
        Assets.instance.init(new AssetManager());

//        setScreen(createScreen(GameScreen.class, new GameScreenModule(this)));
//        setScreen(MatchInjector.injectScreen(this));
//        GameScreen gameScreen = ObjectGraph.create(new ScreenModule(this)).get(GameScreen.class);
        setScreen(new GameScreen(this));
    }

//    private <SCRN extends Screen> SCRN createScreen(Class<SCRN> screenClass, AbstractModule module) {
//        return Guice.createInjector(module).getInstance(screenClass);
//    }

}
