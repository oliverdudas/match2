package com.dudas.game;

import com.dudas.game.controller.BoardEvent;
import com.dudas.game.controller.BoardEventListener;
import com.dudas.game.controller.TwoGemsBoardEvent;

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
