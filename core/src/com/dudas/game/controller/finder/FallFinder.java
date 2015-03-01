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
public class FallFinder extends BaseGemFinder {

    protected Array<Gem> fallGems;
    private GemsProvider provider;

    public FallFinder(Array<Gem> boardGems, BoardHelper helper, GemsProvider provider) {
        super(boardGems, helper);
        fallGems = new Array<Gem>();
        this.provider = provider;
    }

    @Override
    public Gem[] find(Gem... sourceGems) {
        addAll(sourceGems);

        GemsSorter.sortGems(sourceGems);
        for (Gem gem : sourceGems) {
            processFall(gem.getIndex(helper.getHeight()), gem);
        }

        return fallGems.toArray(Gem.class);
    }

    private void addAll(Gem[] sourceGems) {
        for (Gem gem : sourceGems) {
            if (!containsGem(gem)) {
                fallGems.add(gem);
            }
        }
    }

    @Override
    public Gem[] find(Gem[]... gemArrays) {
        for (Gem[] gemArray : gemArrays) {
            find(gemArray);
        }
        return fallGems.toArray(Gem.class);
    }

    private void processFall(int index, Gem gem) {
        moveGemToTop(index);
        toggleGem(gem);
    }

    private void toggleGem(Gem gem) {
        if (helper.isTopBoardIndex(gem.getIndex(helper.getHeight()))) {
            gem.setNew(true);
            gem.setType(provider.getRandomGemType());
        } else {
            gem.setType(GemType.EMPTY);
        }
    }

    /**
     * Moving the gem in its column from original position(0, 1, ..., height)
     * to top(height). The gem is repeatedly swaped with every above
     * gem till it reaches the top. So every gem above the first should be
     * moved below its original position.
     */
    private void moveGemToTop(int gemIndex) {
        int aboveGemIndex = helper.getAboveNeighborIndex(gemIndex);
        if (isPossibleMove(gemIndex, aboveGemIndex)) {
            helper.swapSynchronized(gemIndex, aboveGemIndex, boardGems);
            moveGemToTop(aboveGemIndex);
        }
        Gem gem = helper.findGem(gemIndex, boardGems); // TODO: refactor
        if (!containsGem(gem)) { // maybe a Set would be better, to prevent duplicate items
            gem.block();
            fallGems.add(gem);
        }
    }

    private boolean containsGem(Gem gem) {
        return fallGems.contains(gem, false);
    }

    private boolean isPossibleMove(int gemIndex, int nextGemIndex) {
        return helper.isValidIndex(gemIndex) &&
                helper.isValidIndex(nextGemIndex) &&
                (helper.findGem(nextGemIndex, boardGems).isReady() || containsGem(helper.findGem(nextGemIndex, boardGems)));
    }
}
