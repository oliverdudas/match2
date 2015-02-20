package com.dudas.game;

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

    int getIndex();

    void setIndex(int index);

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
