package com.dudas.game.stage.poolables;

import com.badlogic.gdx.scenes.scene2d.actions.EventAction;

/**
 * Created by OLO on 12. 2. 2015
 */
public class BoardCountDownEventAction<T extends BasePoolableEvent> extends EventAction<T> {

    private int count, current;

    public BoardCountDownEventAction(Class<? extends T> eventClass) {
        super(eventClass);
    }

    public boolean handle(T event) {
        current++;
        event.returnToPool();
        return current >= count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
