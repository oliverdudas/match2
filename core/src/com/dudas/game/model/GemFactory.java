package com.dudas.game.model;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by OLO on 17. 2. 2015
 */
public class GemFactory {

    public static Gem getGem(GemType gemType, float x, float y) {
        return new GemModel(gemType, x, y);
    }

    public static Gem getGem(float x, float y) {
        GemType randomGemType = GemType.values()[MathUtils.random(0, GemType.values().length - 2)];// -1 to exclude EMPTY
        return new GemModel(randomGemType, x, y);
    }
}
