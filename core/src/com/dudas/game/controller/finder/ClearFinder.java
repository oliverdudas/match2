package com.dudas.game.controller.finder;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.dudas.game.controller.helper.BoardHelper;
import com.dudas.game.model.Gem;

/**
 * Created by OLO on 22. 2. 2015
 */
public class ClearFinder extends BaseGemFinder {

    private ObjectSet<Gem> resultGems;

    public ClearFinder(Array<Gem> gems, BoardHelper helper) {
        super(gems, helper);
        resultGems = new ObjectSet<Gem>();
    }

    @Override
    public Gem[] find(Gem... sourceGems) {
        for (Gem sourceGem : sourceGems) {
            populateClearGems(sourceGem);
        }

        return resultGems.iterator().toArray().toArray(Gem.class); //TODO: ?
    }

    private void populateClearGems(Gem baseGem) {
        Array<Gem> upClearArray = resolveClear(baseGem, 0f, 1f);  // up
        Array<Gem> downClearArray = resolveClear(baseGem, 0f, -1f); // down

        if (upClearArray.size + downClearArray.size >= 2) {
            resultGems.addAll(downClearArray);
            resultGems.add(baseGem);
            resultGems.addAll(upClearArray);
        }
//        gemArrayPool.free(upClearArray);
//        gemArrayPool.free(downClearArray);

        Array<Gem> rightClearArray = resolveClear(baseGem, 1f, 0f);  // right
        Array<Gem> leftClearArray = resolveClear(baseGem, -1f, 0f); // left
        if (rightClearArray.size + leftClearArray.size >= 2) {
            resultGems.addAll(leftClearArray);
            resultGems.add(baseGem);
            resultGems.addAll(rightClearArray);
        }
//        gemArrayPool.free(rightClearArray);
//        gemArrayPool.free(leftClearArray);

    }

    private Array<Gem> resolveClear(Gem actualGem, float directionX, float directionY) {
        int actualBoardIndex = actualGem.getIndex(helper.getHeight());
        float nextX = actualGem.getX() + directionX;
        float nextY = actualGem.getY() + directionY;
        int nextBoardIndex = helper.createGemBoardIndex(nextX, nextY);
        if (helper.areNeighborIndexes(actualBoardIndex, nextBoardIndex) && helper.areSameType(actualGem, helper.findGem(nextBoardIndex, boardGems))) {
            Array<Gem> gemArray = resolveClear(helper.findGem(nextBoardIndex, boardGems), directionX, directionY);
            gemArray.add(helper.findGem(nextBoardIndex, boardGems));
            return gemArray;
        } else {
//            return gemArrayPool.obtain();
            return new Array<Gem>();
        }
    }

}
