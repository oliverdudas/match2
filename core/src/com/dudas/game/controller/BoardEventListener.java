package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Gem;

/**
 * Created by foxy on 04/02/2015.
 */
public interface BoardEventListener {
    public void onSwap(TwoGemsBoardEvent event);

    public void onClearSuccess(Array<Gem> gems, Gem unclearedGem);

    public void onClearFail(BoardEvent event);

    public void onFall(Array<Gem> gems);

    void onBackSwap(TwoGemsBoardEvent event);
}