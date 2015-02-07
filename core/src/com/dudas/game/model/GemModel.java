package com.dudas.game.model;

import com.dudas.game.Gem;

/**
 * Created by foxy on 04/02/2015.
 */
public class GemModel implements Gem {

    private float x;
    private float y;
    private GemType type;
    private GemState state;

    public GemModel(GemType type, float x, float y) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.state = GemState.READY;
    }

    public enum GemState { // TODO: presunut do Actora?
        READY,
        BLOCKED
    }



    public int getIndex() {
        return (int) (x * 9 + y);
    }

    public void setIndex(int index) {
        x = index / 9;
        y = index % 9;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public GemType getType() {
        return type;
    }

    public void setType(GemType type) {
        this.type = type;
    }

    public GemState getState() {
        return state;
    }

    public void setState(GemState state) {
        this.state = state;
    }

    public void block() {
        state = GemState.BLOCKED;
    }

    public void setReady() {
        state = GemState.READY;
    }

    public boolean isReady() {
        return GemState.READY.equals(state);
    }

    public boolean isBlocked() {
        return GemState.BLOCKED.equals(state);
    }

    @Override
    public String toString() {
        return "GemModel{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type.name() +
                ", state=" + state.name() +
                '}';
    }
}
