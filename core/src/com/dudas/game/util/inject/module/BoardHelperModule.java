package com.dudas.game.util.inject.module;

import com.dudas.game.Constants;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Created by OLO on 15. 3. 2015
 */
public class BoardHelperModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Float.class).annotatedWith(Names.named(Constants.WIDTH)).toInstance(Constants.BOARD_WIDTH);
        bind(Float.class).annotatedWith(Names.named(Constants.HEIGHT)).toInstance(Constants.BOARD_HEIGHT);
    }
}
