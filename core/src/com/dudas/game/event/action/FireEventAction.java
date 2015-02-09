package com.dudas.game.event.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Event;

/**
 * Created by foxy on 09/02/2015.
 */
public class FireEventAction extends Action {

    private Event event;

    public FireEventAction(Event event) {
        this.event = event;
    }

    @Override
    public boolean act(float delta) {
        if (getActor() != null)
            getActor().fire(event);
        return true;
    }
}