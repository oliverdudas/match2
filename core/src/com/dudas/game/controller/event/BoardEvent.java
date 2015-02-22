package com.dudas.game.controller.event;

import com.dudas.game.model.Gem;

/**
 * Created by OLO on 20. 2. 2015
 */
public interface BoardEvent {

    Gem[] getGems();

    void complete();
}
