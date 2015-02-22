package com.dudas.game.controller.helper;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.dudas.game.model.Gem;

/**
 * Created by OLO on 22. 2. 2015
 */
public interface BoardHelper {
    Gem findGem(int boardIndex, Array<Gem> boardGems);

    void swapSynchronized(int fromIndex, int toIndex, Array<Gem> boardGems);

    Gem synchronizeGemPosition(int boardIndex, Array<Gem> boardGems);

    boolean isNotTopBoardIndex(int below);

    boolean areSameType(Gem gemType1, Gem gemType2);

    IntArray getNeighborIndexes(int gemIndex);

    int getAboveNeighborIndex(int gemIndex);

    int getLeftNeighborIndex(int gemIndex);

    int getBelowNeighborIndex(int gemIndex);

    int getRightNeighborIndex(int gemIndex);

    boolean isValidIndex(int index);

    void checkNeighborCoordinates(float fromX, float fromY, float toX, float toY);

    boolean areNeighborCoordinates(float fromX, float fromY, float toX, float toY);

    boolean areNeighborIndexes(int fromIndex, int toIndex);

    int createGemBoardIndex(float x, float y);
}
