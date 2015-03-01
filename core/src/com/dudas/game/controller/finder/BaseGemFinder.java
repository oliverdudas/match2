package com.dudas.game.controller.finder;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.controller.helper.BoardHelper;
import com.dudas.game.model.Gem;

/**
 * Created by OLO on 22. 2. 2015
 */
public abstract class BaseGemFinder implements GemFinder {

    protected Array<Gem> boardGems;
    protected BoardHelper helper;

    protected BaseGemFinder(Array<Gem> boardGems, BoardHelper helper) {
        this.boardGems = boardGems;
        this.helper = helper;
    }

    public abstract Gem[] find(Gem... gems);

    public Gem[] find(Gem[]... gemArrays) {
        throw new UnsupportedOperationException();
    }

}
