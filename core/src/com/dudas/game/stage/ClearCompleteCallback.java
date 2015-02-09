package com.dudas.game.stage;

import com.badlogic.gdx.utils.Pool;
import com.dudas.game.model.GemType;

/**
 * Created by foxy on 06/02/2015.
 */
public class ClearCompleteCallback implements Runnable {

    private GemActor gemActor;
    private Pool<ClearCompleteCallback> pool;

    @Override
    public void run() {
        gemActor.setVisible(false);
        gemActor.setScale(1);
        gemActor.getGem().setType(GemType.EMPTY);
        pool.free(this); // on end of run release this object back to pool
    }

    public void addGemActor(GemActor actor) {
        this.gemActor = actor;
    }

    public void addPool(Pool<ClearCompleteCallback> clearCompleteCallbackPool) {
        this.pool = clearCompleteCallbackPool;
    }
}
