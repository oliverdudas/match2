package com.dudas.game.controller.finder;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.controller.helper.BoardHelper;
import com.dudas.game.model.Gem;
import com.dudas.game.model.GemType;
import com.dudas.game.model.provider.GemsProvider;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by OLO on 22. 2. 2015
 */
public class FallFinder extends BaseGemFinder {

    protected Array<Gem> resultGems;
    private GemsProvider provider;

    public FallFinder(Array<Gem> boardGems, BoardHelper helper, GemsProvider provider) {
        super(boardGems, helper);
        resultGems = new Array<Gem>();
        this.provider = provider;
    }

    /**
     * Sort gems by X and Y coordinates
     *
     * @param gems gems to sort
     */
    protected void sortGems(Gem... gems) {
        Arrays.sort(gems, new Comparator<Gem>() {
            @Override
            public int compare(Gem o1, Gem o2) {
                int byX = (int) o1.getX() - (int) o2.getX();
                if (byX != 0) {
                    return byX;
                } else {
                    return (int) o1.getY() - (int) o2.getY();
                }
            }
        });
    }

    @Override
    public Gem[] find(Gem... sourceGems) {
        resultGems.addAll(sourceGems);

        sortGems(sourceGems);
        for (Gem gem : sourceGems) {
            int originalIndex = gem.getIndex(helper.getHeight());
            processFall(gem.getIndex(helper.getHeight()), gem, originalIndex);
        }

        return resultGems.toArray(Gem.class);
    }

    private void processFall(int index, Gem gem, int index2) {
        moveGemToTop(index);
        toggleGem(gem);
        searchForBelowEmpty(index2);
    }

    private void toggleGem(Gem gem) {
        if (helper.isTopBoardIndex(gem.getIndex(helper.getHeight()))) {
            gem.setNew(true);
            gem.setType(provider.getRandomGemType());
        } else {
            gem.setType(GemType.EMPTY);
        }
    }

    protected void searchForBelowEmpty(int gemIndex) {
        int belowGemIndex = helper.getBelowNeighborIndex(gemIndex);
        if (helper.isValidIndex(gemIndex) && helper.isValidIndex(belowGemIndex) && helper.findGem(belowGemIndex, boardGems).getType().equals(GemType.EMPTY)) {
            processFall(belowGemIndex, helper.findGem(belowGemIndex, boardGems), belowGemIndex);
        } else if (isPossibleMove(gemIndex, belowGemIndex)) {
            searchForBelowEmpty(belowGemIndex);
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
        if (!resultGems.contains(gem, false)) { // maybe a Set would be better, to prevent duplicate items
            gem.block();
            resultGems.add(gem);
        }
    }

    private boolean isPossibleMove(int gemIndex, int nextGemIndex) {
        return helper.isValidIndex(gemIndex) &&
                helper.isValidIndex(nextGemIndex) &&
                (helper.findGem(nextGemIndex, boardGems).isReady() || resultGems.contains(helper.findGem(nextGemIndex, boardGems), false));
    }
}
