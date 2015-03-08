package com.dudas.game.model;

/**
 * Created by OLO on 17. 2. 2015
 */
public class GemFactory {

    public static Gem getGem(GemType gemType, float x, float y, int id) {
        return new GemModel(gemType, x, y, id);
    }

}
