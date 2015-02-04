package com.dudas.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

/**
 * Created by foxy on 04/02/2015.
 */
public interface Board {

    FloatArray swap(float fromX, float fromY, float toX, float toY);

    Array<Gem> getGems();
    
}
