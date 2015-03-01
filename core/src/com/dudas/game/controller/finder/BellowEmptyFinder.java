package com.dudas.game.controller.finder;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.controller.helper.BoardHelper;
import com.dudas.game.model.Gem;
import com.dudas.game.model.GemType;
import com.dudas.game.model.provider.GemsProvider;
import com.dudas.game.util.GemsSorter;

/**
 * Created by OLO on 22. 2. 2015
 */
public class BellowEmptyFinder extends BaseGemFinder {

    protected Array<Gem> emptyBelowGems;

    public BellowEmptyFinder(Array<Gem> boardGems, BoardHelper helper) {
        super(boardGems, helper);
        emptyBelowGems = new Array<Gem>();
    }

    @Override
    public Gem[] find(Gem... sourceGems) {
        GemsSorter.sortGems(sourceGems);
        for (Gem gem : sourceGems) {
            int originalIndex = gem.getIndex(helper.getHeight());
            searchForBelowEmpty(originalIndex);
        }

        return emptyBelowGems.toArray(Gem.class);
    }

    protected void searchForBelowEmpty(int gemIndex) {
        if (helper.findGem(gemIndex, boardGems).getType().equals(GemType.EMPTY)) {
            addUniqueEmptyGem(gemIndex);
        }

        int belowGemIndex = helper.getBelowNeighborIndex(gemIndex);
        if (isPossibleMove(gemIndex, belowGemIndex)) {
            searchForBelowEmpty(belowGemIndex);
        }
    }

    private void addUniqueEmptyGem(int gemIndex) {
        Gem gem = helper.findGem(gemIndex, boardGems); // TODO: refactor
        if (!emptyBelowGems.contains(gem, false)) { // maybe a Set would be better, to prevent duplicate items
            emptyBelowGems.add(gem);
        }
    }

    private boolean isPossibleMove(int gemIndex, int nextGemIndex) {
        return helper.isValidIndex(gemIndex) &&
                helper.isValidIndex(nextGemIndex) &&
                (helper.findGem(nextGemIndex, boardGems).isReady() || helper.findGem(nextGemIndex, boardGems).getType().equals(GemType.EMPTY));
    }
}
