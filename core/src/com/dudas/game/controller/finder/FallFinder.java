package com.dudas.game.controller.finder;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.controller.helper.BoardHelper;
import com.dudas.game.model.Gem;
import com.dudas.game.model.provider.GemsProvider;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by OLO on 22. 2. 2015
 */
public class FallFinder extends BaseGemFinder {

    private Array<Gem> resultGems;
    private GemsProvider provider;

    public FallFinder(Array<Gem> boardGems, BoardHelper helper, GemsProvider provider) {
        super(boardGems, helper);
        resultGems = new Array<Gem>();
        this.provider = provider;
    }

    /**
     * Sort gems by X and Y coordinates
     * @param gems gems to sort
     */
    private void sortGems(Gem... gems) {
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

        sortGems(sourceGems);
        for (Gem gem : sourceGems) {
            gem.setNew(true);
            gem.setType(provider.getRandomGemType());
            moveGemToTop(gem.getIndex());
        }

        return resultGems.toArray(Gem.class);
    }

    /**
     * Moving the gem in its column from original position(0, 1, ..., height)
     * to top(height). The gem is repeatedly swaped with every above
     * gem till it reaches the top. So every gem above the first should be
     * moved below its original position.
     */
    private void moveGemToTop(int gemIndex) {
        int aboveGemIndex = helper.getAboveNeighborIndex(gemIndex);
        if (helper.isValidIndex(gemIndex) && helper.isValidIndex(aboveGemIndex)) {
            helper.swapSynchronized(gemIndex, aboveGemIndex, boardGems);
            moveGemToTop(aboveGemIndex);
        }
        Gem gem = helper.findGem(gemIndex, boardGems); // TODO: refactor
        if (!resultGems.contains(gem, false)) { // maybe a Set would be better, to prevent duplicate items
            gem.block();
            resultGems.add(gem);
        }
    }
}
