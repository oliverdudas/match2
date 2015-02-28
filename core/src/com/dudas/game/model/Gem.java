package com.dudas.game.model;

import com.dudas.game.model.GemModel;
import com.dudas.game.model.GemType;

/**
 * Created by foxy on 04/02/2015.
 */
public interface Gem {

    float getX();

    void setX(float x);

    float getY();

    void setY(float y);

    int getIndex(float height);

    void setIndex(int index, int height);

    GemType getType();

    void setType(GemType type);

    GemModel.GemState getState();

    void block();

    void setReady();

    boolean isReady();

    boolean isBlocked();

    void setNew(boolean b);

    boolean isNew();
}
