package com.dudas.game.controller.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OLO on 15. 2. 2015
 */
public class BoardEventManager implements EventManager {

    private List<BoardEventListener> listeners;

    public void attach(BoardEventListener eventListener) {
        if (listeners == null) {
            listeners = new ArrayList<BoardEventListener>();
        }
        listeners.add(eventListener);
    }

    public void detach(BoardEventListener eventListener) {
        if (listeners != null) {
            listeners.remove(eventListener);
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
