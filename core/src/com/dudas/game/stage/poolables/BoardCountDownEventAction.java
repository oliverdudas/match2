package com.dudas.game.stage.poolables;

import com.badlogic.gdx.scenes.scene2d.actions.EventAction;
import com.badlogic.gdx.utils.Array;
import com.dudas.game.event.action.ClearDoneEvent;

/**
 * Created by OLO on 12. 2. 2015
 */
public class BoardCountDownEventAction<T extends BasePoolableEvent> extends EventAction<T> {

    private int count, current;
    private Array<T> events;

    public BoardCountDownEventAction(Class<? extends T> eventClass) {
        super(eventClass);
        events = new Array<T>();
    }

    public boolean handle(T event) {
        if (isMyEvent(event)) {
            current++;
            if (event.getPool() != null) { // TODO: workaround. During parallel clearing sometimes happens, that the pool is null, because of first clearing mechanism.
                event.returnToPool();
            }
            return current >= count;
        } else {
            return false;
        }
    }

    private boolean isMyEvent(T event) {
        return events.contains(event, false);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addEvent(T clearDoneEvent) {
        events.add(clearDoneEvent);
    }

    @Override
    public void reset() {
        this.count = 0;
        this.current = 0;
        this.events.clear();
        super.reset();
    }
}
