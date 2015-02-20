package com.dudas.game.controller;

import com.dudas.game.Gem;

/**
 * Created by OLO on 20. 2. 2015
 */
public interface BoardEvent {

    Gem[] getGems();

    void complete();
}
