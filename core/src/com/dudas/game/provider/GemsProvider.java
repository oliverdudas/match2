package com.dudas.game.provider;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Gem;
import com.dudas.game.model.GemType;

/**
 * Created by foxy on 07/02/2015.
 */
public interface GemsProvider {

    Array<Gem> getGems(float width, float height);

    GemType getRandomGemType();
}
