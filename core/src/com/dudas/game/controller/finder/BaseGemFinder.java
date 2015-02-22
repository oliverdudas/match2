package com.dudas.game.controller.finder;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.model.Gem;

/**
 * Created by OLO on 22. 2. 2015
 */
public abstract class BaseGemFinder implements GemFinder {

    public abstract Array<Gem> find(Array<Gem> gems);
}
