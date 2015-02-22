package com.dudas.game.controller.event;

/**
 * Created by foxy on 04/02/2015.
 */
public interface BoardEventListener {

    void onSwap(TwoGemsBoardEvent event);

    void onClearSuccess(BoardEvent event);

    void onClearFail(BoardEvent event);

    void onFall(BoardEvent event);

    void onBackSwap(TwoGemsBoardEvent event);
}