package com.dudas.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.dudas.game.provider.GemsProvider;

/**
 * Created by foxy on 04/02/2015.
 */
public interface Board {

    FloatArray swap(float fromX, float fromY, float toX, float toY);

    Array<Gem> getGems();

    void clear(float fromX, float fromY, float toX, float toY);

    float getWidth();

    float getHeight();

    void setGemsProvider(GemsProvider provider);
}
