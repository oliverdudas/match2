package com.dudas.game.util.inject;

import com.dudas.game.controller.Board;
import com.dudas.game.controller.BoardController;
import com.dudas.game.util.inject.module.BoardModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Central injector which holds our singletons.
 */
public class AppInjector {

    private static Injector injector = null;

    public static Injector getInjector() {
        if (injector == null) {
            injector = Guice.createInjector();
        }
        return injector;
    }

    public static Board createBoard() {
        return Guice.createInjector(new BoardModule()).getInstance(BoardController.class);
    }
}
