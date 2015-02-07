package com.dudas.game.provider;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Gem;

/**
 * Created by foxy on 07/02/2015.
 */
public interface GemsProvider {

    public Array<Gem> getGems(float width, float height);
}
