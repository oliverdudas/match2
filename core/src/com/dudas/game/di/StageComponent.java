package com.dudas.game.di;

import com.dudas.game.screen.GameScreen;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by olo on 5. 12. 2015.
 */
@Singleton
@Component(modules = {StageModule.class})
public interface StageComponent {

    void inject(GameScreen gameScreen);
}
