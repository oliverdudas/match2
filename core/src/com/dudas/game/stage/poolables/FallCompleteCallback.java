package com.dudas.game.stage.poolables;

import com.dudas.game.stage.GemActor;

/**
 * Created by foxy on 06/02/2015.
 */
public class FallCompleteCallback extends BasePool<FallCompleteCallback> implements Runnable {

    private GemActor gemActor;

    @Override
    public void run() {
        gemActor.getGem().setReady();

        returnToPool(this);
    }

    public void addGemActor(GemActor actor) {
        this.gemActor = actor;
    }

    @Override
    public void reset() {
        this.gemActor = null;
        super.reset();
    }
}
