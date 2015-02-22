package com.dudas.game.controller.event;

/**
 * Created by OLO on 15. 2. 2015
 */
public interface EventManager {

    void fireSwap(TwoGemsBoardEvent event);

    void fireBackSwap(TwoGemsBoardEvent event);

    void fireClearSuccess(BoardEvent event);

    void fireClearFail(BoardEvent event);

    void fireFall(BoardEvent event);

    void attach(BoardEventListener eventListener);
}
