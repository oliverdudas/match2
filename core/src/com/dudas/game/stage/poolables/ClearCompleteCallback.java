package com.dudas.game.stage.poolables;

import com.dudas.game.model.GemType;
import com.dudas.game.stage.GemActor;

/**
 * Created by foxy on 06/02/2015.
 */
public class ClearCompleteCallback extends BasePool<ClearCompleteCallback> implements Runnable {

    private GemActor gemActor;

    @Override
    public void run() {
        gemActor.setVisible(false);
        gemActor.setScale(1);
        gemActor.setRotation(0);
        gemActor.getGem().setType(GemType.EMPTY);

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
