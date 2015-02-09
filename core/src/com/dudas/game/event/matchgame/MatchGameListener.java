package com.dudas.game.event.matchgame;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Gem;

/**
 * Created by foxy on 04/02/2015.
 */
public interface MatchGameListener {
    public void onSwap(float fromX, float fromY, float toX, float toY);

    public void onClearSuccess(Array<Gem> gems, Gem unclearedGem);

    public void onClearFail(float fromX, float fromY, float toX, float toY);

    public void onFall(Array<Gem> gems);

    void onBackSwap(Gem fromGem, Gem toGem);
}