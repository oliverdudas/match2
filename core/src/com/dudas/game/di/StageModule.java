package com.dudas.game.di;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dudas.game.Constants;
import com.dudas.game.controller.Board;
import com.dudas.game.controller.BoardController;
import com.dudas.game.controller.event.BoardEventManager;
import com.dudas.game.controller.event.EventManager;
import com.dudas.game.controller.helper.BoardHelper;
import com.dudas.game.controller.helper.DefaultBoardHelper;
import com.dudas.game.model.provider.GemsProvider;
import com.dudas.game.model.provider.RandomGemsProvider;
import com.dudas.game.stage.GameStage;
import com.dudas.game.stage.MainStage;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by olo on 5. 12. 2015.
 */
@Module
public class StageModule {

    @Provides
    @Singleton
    MainStage providesMainStage(Batch batch) {
        return new MainStage(batch);
    }

    @Provides
    @Singleton
    GameStage providesGameStage(Batch batch, Board board) {
        return new GameStage(batch, board);
    }

    @Provides
    @Singleton
    Batch providesBatch() {
        return new SpriteBatch();
    }

    @Provides
    @Singleton
    Board providesBoard(GemsProvider gemsProvider, EventManager eventManager, BoardHelper boardHelper) {
        return new BoardController(
                Constants.BOARD_WIDTH,
                Constants.BOARD_HEIGHT,
                gemsProvider,
                eventManager,
                boardHelper
        );
    }

//    @Provides
//    @Named(Constants.WIDTH)
//    float providesWidth() {
//        return Constants.BOARD_WIDTH;
//    }
//
//    @Provides
//    @Named(Constants.HEIGHT)
//    float providesHeight() {
//        return Constants.BOARD_HEIGHT;
//    }

    @Provides
    @Singleton
    GemsProvider providesGemsProvider() {
        return new RandomGemsProvider();
    }

    @Provides
    @Singleton
    EventManager providesEventManager() {
        return new BoardEventManager();
    }

    @Provides
    @Singleton
    BoardHelper providesBoardHelper() {
        return new DefaultBoardHelper(
                Constants.BOARD_WIDTH,
                Constants.BOARD_HEIGHT
        );
    }

}
