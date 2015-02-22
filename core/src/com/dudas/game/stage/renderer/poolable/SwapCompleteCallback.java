package com.dudas.game.stage.renderer.poolable;

import com.dudas.game.controller.event.BoardEvent;

/**
 * Created by foxy on 06/02/2015.
 */
public class SwapCompleteCallback extends BasePool<SwapCompleteCallback> implements Runnable {

    private BoardEvent event;

    @Override
    public void run() {
        event.complete();
        returnToPool(this);
    }

    public void setEvent(BoardEvent event) {
        this.event = event;
    }

    @Override
    public void reset() {
        this.event = null;
        super.reset();
    }
}
