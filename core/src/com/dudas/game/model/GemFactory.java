package com.dudas.game.model;

/**
 * Created by OLO on 17. 2. 2015
 */
public class GemFactory {

    public static Gem getGem(GemType gemType, float x, float y, int id) {
        return getGem(gemType, GemState.READY, x, y, id, false);
    }

    public static Gem getGem(GemType gemType, GemState state, float x, float y, int id, boolean newGem) {
        GemModel gemModel = new GemModel(gemType, x, y, id);
        gemModel.setState(state);
        gemModel.setNew(newGem);
        return gemModel;
    }

}
