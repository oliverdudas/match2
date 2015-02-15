package com.dudas.game.stage.poolables;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by OLO on 12. 2. 2015
 */
public class BasePoolableEvent extends Event {

    private Pool pool;

    public void returnToPool() {
        pool.free(this);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public Pool getPool() {
        return pool;
    }

    @Override
    public void reset() {
        setPool(null);
//        super.reset(); TODO: when it is uncomment it crashes in Action.notify !!! check this
    }
}
