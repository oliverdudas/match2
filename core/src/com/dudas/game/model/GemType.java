package com.dudas.game.model;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by foxy on 07/02/2015.
 */
public enum GemType {
    RED,
    GREEN,
    BLUE,
    PURPLE,
    YELLOW,
    ORANGE,
    EMPTY;

    public static GemType getRandom() {
        return GemType.values()[MathUtils.random(0, GemType.values().length - 2)]; // -1 to exclude EMPTY
    }
}
