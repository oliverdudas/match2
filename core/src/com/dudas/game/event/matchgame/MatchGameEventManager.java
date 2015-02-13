package com.dudas.game.event.matchgame;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Gem;

/**
 * Created by foxy on 04/02/2015.
 */
public class MatchGameEventManager {
    private static MatchGameEventManager instance;

    private Array<MatchGameListener> listeners;

    private MatchGameEventManager() {
        listeners = new Array<MatchGameListener>();
    }

    public static MatchGameEventManager get() {
        if (instance == null) instance = new MatchGameEventManager();
        return instance;
    }

    public static void destroy() {
        instance = null;
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

    public void fireSwap(Gem to, Gem from) {
        if (listeners != null) {
            for (MatchGameListener listener : listeners) {
                listener.onSwap(to, from);
            }
        }
    }

    public void fireBackSwap(Gem fromGem, Gem toGem) {
        if (listeners != null) {
            for (MatchGameListener listener : listeners) {
                listener.onBackSwap(fromGem, toGem);
            }
        }
    }

    public void fireClearSuccess(Array<Gem> gems, Gem unclearedGem) {
        if (listeners != null) {
            for (MatchGameListener listener : listeners) {
                listener.onClearSuccess(gems, unclearedGem);
            }
        }
    }

    public void fireClearFail(float fromX, float fromY, float toX, float toY) {
        if (listeners != null) {
            for (MatchGameListener listener : listeners) {
                listener.onClearFail(fromX, fromY, toX, toY);
                break; //hack TODO:
            }
        }
    }

    public void fireFall(Array<Gem> gems) {
        if (listeners != null) {
            for (MatchGameListener listener : listeners) {
                listener.onFall(gems);
            }
        }
    }

    public void clear() {
        listeners.clear();
    }

}

