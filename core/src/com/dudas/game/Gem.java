package com.dudas.game;

import com.dudas.game.model.GemModel;

/**
 * Created by foxy on 04/02/2015.
 */
public interface Gem {

    float getX();

    void setX(float x);

    float getY();

    void setY(float y);

    GemModel.GemType getType();

    void setType(GemModel.GemType type);

    GemModel.GemState getState();

    void setState(GemModel.GemState state);

    void block();

    void setReady();

    boolean isReady();

    boolean isBlocked();

}
