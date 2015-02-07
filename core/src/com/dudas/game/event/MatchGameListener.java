package com.dudas.game.event;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Gem;

/**
 * Created by foxy on 04/02/2015.
 */
public interface MatchGameListener {
    public void onSwap(float fromX, float fromY, float toX, float toY);

    public void onClearSuccess(Array<Gem> gems);

    public void onClearFail(float fromX, float fromY, float toX, float toY);

    public void onFall(Object eventData);

    void onBackSwap(Gem fromGem, Gem toGem);
}