package com.dudas.game.stage;

import com.badlogic.gdx.utils.Pool;
import com.dudas.game.model.GemType;

/**
 * Created by foxy on 06/02/2015.
 */
public class FallCompleteCallback implements Runnable {

    private GemActor gemActor;
    private Pool<FallCompleteCallback> pool;

    @Override
    public void run() {
        gemActor.getGem().setReady();
        pool.free(this); // on end of run release this object back to pool
    }

    public void addGemActor(GemActor actor) {
        this.gemActor = actor;
    }

    public void addPool(Pool<FallCompleteCallback> clearCompleteCallbackPool) {
        this.pool = clearCompleteCallbackPool;
    }
}
