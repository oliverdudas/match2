package com.dudas.game;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.controller.BoardEventListener;
import com.dudas.game.provider.GemsProvider;

/**
 * Created by foxy on 04/02/2015.
 */
public interface Board {

    void swap(float fromX, float fromY, float toX, float toY);

    Array<Gem> getGems();

    float getWidth();

    float getHeight();

    void setGemsProvider(GemsProvider provider);

    void setEventManager(EventManager eventManager);

    void setEventProcessor(BoardEventListener matchGameListener);
}
