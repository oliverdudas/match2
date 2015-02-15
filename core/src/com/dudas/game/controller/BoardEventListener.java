package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Gem;

/**
 * Created by foxy on 04/02/2015.
 */
public interface BoardEventListener {
    public void onSwap(Gem to, Gem from);

    public void onClearSuccess(Array<Gem> gems, Gem unclearedGem);

    public void onClearFail(float fromX, float fromY, float toX, float toY);

    public void onFall(Array<Gem> gems);

    void onBackSwap(Gem fromGem, Gem toGem);
}