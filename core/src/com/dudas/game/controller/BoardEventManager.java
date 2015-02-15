package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.EventManager;
import com.dudas.game.Gem;

/**
 * Created by OLO on 15. 2. 2015
 */
public class BoardEventManager implements EventManager {

    private Array<BoardEventListener> listeners;

    public void attach(BoardEventListener eventListener) {
        if (listeners == null) {
            listeners = new Array<BoardEventListener>();
        }
        listeners.add(eventListener);
    }

    public void detach(BoardEventListener eventListener) {
        if (listeners != null) {
            listeners.removeValue(eventListener, true);
        }
    }

    public void fireSwap(Gem to, Gem from) {
        if (listeners != null) {
            for (BoardEventListener listener : listeners) {
                listener.onSwap(to, from);
            }
        }
    }

    public void fireBackSwap(Gem fromGem, Gem toGem) {
        if (listeners != null) {
            for (BoardEventListener listener : listeners) {
                listener.onBackSwap(fromGem, toGem);
            }
        }
    }

    public void fireClearSuccess(Array<Gem> gems, Gem unclearedGem) {
        if (listeners != null) {
            for (BoardEventListener listener : listeners) {
                listener.onClearSuccess(gems, unclearedGem);
            }
        }
    }

    public void fireClearFail(float fromX, float fromY, float toX, float toY) {
        if (listeners != null) {
            for (BoardEventListener listener : listeners) {
                listener.onClearFail(fromX, fromY, toX, toY);
                break; //hack TODO:
            }
        }
    }

    public void fireFall(Array<Gem> gems) {
        if (listeners != null) {
            for (BoardEventListener listener : listeners) {
                listener.onFall(gems);
            }
        }
    }

    public void clear() {
        listeners.clear();
    }
}
