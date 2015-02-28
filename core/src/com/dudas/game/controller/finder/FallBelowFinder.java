package com.dudas.game.controller.finder;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.controller.helper.BoardHelper;
import com.dudas.game.model.Gem;
import com.dudas.game.model.provider.GemsProvider;

/**
 * Created by OLO on 22. 2. 2015
 */
public class FallBelowFinder extends FallFinder {


    public FallBelowFinder(Array<Gem> boardGems, BoardHelper helper, GemsProvider provider) {
        super(boardGems, helper, provider);
    }

    @Override
    public Gem[] find(Gem... sourceGems) {
        sortGems(sourceGems);
        for (Gem gem : sourceGems) {
            int originalIndex = gem.getIndex(helper.getHeight());
            searchForBelowEmpty(originalIndex);
        }

        return resultGems.toArray(Gem.class);
    }

}
