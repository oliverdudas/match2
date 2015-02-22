package com.dudas.game.controller.helper;

import com.badlogic.gdx.utils.IntArray;
import com.dudas.game.model.Gem;

/**
 * Created by OLO on 22. 2. 2015
 */
public interface BoardHelper {
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
