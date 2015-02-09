package com.dudas.game.event.action;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.dudas.game.stage.GemActor;

/**
 * Created by foxy on 09/02/2015.
 */
public class FallDoneEvent extends Event {

    private GemActor gemActor;

    public FallDoneEvent(GemActor gemActor) {
        this.gemActor = gemActor;
    }
}
