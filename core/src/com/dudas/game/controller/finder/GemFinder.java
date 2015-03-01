package com.dudas.game.controller.finder;

import com.dudas.game.model.Gem;

/**
 * Created by OLO on 22. 2. 2015
 */
public interface GemFinder {

    Gem[] find(Gem... gems);

    Gem[] find(Gem[]... gemArrays);
}
