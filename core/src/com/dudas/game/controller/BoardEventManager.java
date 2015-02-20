package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.EventManager;

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

    public void fireSwap(TwoGemsBoardEvent event) {
        if (listeners != null) {
            for (BoardEventListener listener : listeners) {
                listener.onSwap(event);
            }
        }
    }

    public void fireBackSwap(TwoGemsBoardEvent event) {
        if (listeners != null) {
            for (BoardEventListener listener : listeners) {
                listener.onBackSwap(event);
            }
        }
    }

    public void fireClearSuccess(BoardEvent event) {
        if (listeners != null) {
            for (BoardEventListener listener : listeners) {
                listener.onClearSuccess(event);
            }
        }
    }

    public void fireClearFail(BoardEvent event) {
        if (listeners != null) {
            for (BoardEventListener listener : listeners) {
                listener.onClearFail(event);
                break; //hack TODO:
            }
        }
    }

    public void fireFall(BoardEvent event) {
        if (listeners != null) {
            for (BoardEventListener listener : listeners) {
                listener.onFall(event);
            }
        }
    }

    public void clear() {
        listeners.clear();
    }
}
