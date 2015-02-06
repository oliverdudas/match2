package com.dudas.game.event;

import com.badlogic.gdx.utils.Array;

/**
 * Created by foxy on 04/02/2015.
 */
public class MatchGameEventManager {
    private static MatchGameEventManager minstance;

    Array<MatchGameListener> listeners;

    private MatchGameEventManager() {
        listeners = new Array<MatchGameListener>();
    }

    public static MatchGameEventManager get() {
        if (minstance == null) minstance = new MatchGameEventManager();
        return minstance;
    }

    public static void setMatchGameProcessor(MatchGameListener eventListener) {
        get().attach(eventListener);
    }

    private void attach(MatchGameListener eventListener) {
        if (listeners == null) {
            listeners = new Array<MatchGameListener>();
        }
        listeners.add(eventListener);
    }

    public void detach(MatchGameListener eventListener) {
        if (listeners != null) {
            listeners.removeValue(eventListener, true);
        }
    }

    public void fireSwap(float fromX, float fromY, float toX, float toY) {
        if (listeners != null) {
            for (MatchGameListener listener : listeners) {
                listener.onSwap(fromX, fromY, toX, toY);
            }
        }
    }

    public void fireClear(Object eventData) {
        if (listeners != null) {
            for (MatchGameListener listener : listeners) {
                listener.onClearSuccess(eventData);
            }
        }
    }

    public void fireFall(Object eventData) {
        if (listeners != null) {
            for (MatchGameListener listener : listeners) {
                listener.onFall(eventData);
            }
        }
    }

    public void clear() {
        listeners.clear();
    }

}

