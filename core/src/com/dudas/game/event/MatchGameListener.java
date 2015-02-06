package com.dudas.game.event;

/**
 * Created by foxy on 04/02/2015.
 */
public interface MatchGameListener {
    public void onSwap(float fromX, float fromY, float toX, float toY);

    public void onClearSuccess(Object eventData);

    public void onClearFail(Object eventData);

    public void onFall(Object eventData);
}