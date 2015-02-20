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

    void clear(float fromX, float fromY, float toX, float toY);

    float getWidth();

    float getHeight();

    void setGemsProvider(GemsProvider provider);

    void setEventManager(EventManager eventManager);

    void backSwap(float fromX, float fromY, float toX, float toY);

    void setGemReady(float x, float y);

    void fall(Array<Gem> gems);

    void clearFallen(Array<Gem> gems);

    void setEventProcessor(BoardEventListener matchGameListener);
}
