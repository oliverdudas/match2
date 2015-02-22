package com.dudas.game.stage.renderer.poolable;

import com.badlogic.gdx.utils.Pool;

/**
 * Created by OLO on 12. 2. 2015
 */
public class BasePool<T> implements Pool.Poolable {

    private Pool<T> pool;

    @Override
    public void reset() {
        this.pool = null;
    }

    protected void returnToPool(T poolObject) {
        pool.free(poolObject);
    }

    public Pool<T> getPool() {
        return pool;
    }

    public void setPool(Pool<T> pool) {
        this.pool = pool;
    }
}
