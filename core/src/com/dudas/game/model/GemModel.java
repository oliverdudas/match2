package com.dudas.game.model;

/**
 * Created by foxy on 04/02/2015.
 */
public class GemModel implements Gem {

    private float x;
    private float y;
    private GemType type;
    private GemState state;
    private boolean newGem;


    public GemModel(GemType type, float x, float y) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.state = GemState.READY;
        this.newGem = false;
    }

    public enum GemState {
        READY,
        BLOCKED
    }

    public int getIndex(float height) {
        return (int) (x * (int) height + y);
    }

    public void setIndex(int index, int height) {
        x = index / height;
        y = index % height;
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
    public void setNew(boolean newGem) {
        this.newGem = newGem;
    }

    public boolean isNew() {
        return newGem;
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
