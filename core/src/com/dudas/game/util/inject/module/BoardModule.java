package com.dudas.game.util.inject.module;

import com.dudas.game.Constants;
import com.dudas.game.controller.event.BoardEventManager;
import com.dudas.game.controller.event.EventManager;
import com.dudas.game.controller.helper.BoardHelper;
import com.dudas.game.controller.helper.DefaultBoardHelper;
import com.dudas.game.model.provider.GemsProvider;
import com.dudas.game.model.provider.RandomGemsProvider;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Created by OLO on 15. 3. 2015
 */
public class BoardModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Float.class).annotatedWith(Names.named(Constants.WIDTH)).toInstance(Constants.BOARD_WIDTH);
        bind(Float.class).annotatedWith(Names.named(Constants.HEIGHT)).toInstance(Constants.BOARD_HEIGHT);
        bind(GemsProvider.class).to(RandomGemsProvider.class);
        bind(EventManager.class).to(BoardEventManager.class);
        bind(BoardHelper.class).to(DefaultBoardHelper.class);
    }
}
