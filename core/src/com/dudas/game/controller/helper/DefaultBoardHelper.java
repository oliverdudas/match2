package com.dudas.game.controller.helper;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.dudas.game.model.Gem;
import com.dudas.game.exception.NeighborException;

/**
 * Created by OLO on 22. 2. 2015
 */
public class DefaultBoardHelper implements BoardHelper {

    private float width;
    private float height;

    private IntArray topBorderIndexes;
    private float maxBoardIndex;
    private float minBoardIndex;

    public DefaultBoardHelper(float width, float height) {
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        maxBoardIndex = width * height - 1;
        minBoardIndex = 0;
        initTopBorderIndexes();
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    private void initTopBorderIndexes() {
        topBorderIndexes = new IntArray((int) getWidth());
        for (int i = 1; i <= getWidth(); i++) {
            topBorderIndexes.add((int) getHeight() * i - 1);
        }
    }

    @Override
    public Gem findGem(int boardIndex, Array<Gem> boardGems) {
        Gem gem = boardGems.get(boardIndex);
        if (gem == null) {
            throw new RuntimeException("Gem on boardIndex position doesn't exist!");
        }
        return gem;
    }

    /**
     * Swaps indexes in the gems array and also swaps both gems positions
     *
     * @param fromIndex index of forst gem to swap
     * @param toIndex   index of second gem to swap
     */
    @Override
    public void swapSynchronized(int fromIndex, int toIndex, Array<Gem> boardGems) {
        boardGems.swap(fromIndex, toIndex);
        synchronizeGemPosition(fromIndex, boardGems);
        synchronizeGemPosition(toIndex, boardGems);
    }


    @Override
    public Gem synchronizeGemPosition(int boardIndex, Array<Gem> boardGems) {
        Gem gem = findGem(boardIndex, boardGems);
        gem.setIndex(boardIndex, (int) getHeight());
        return gem;
    }

    @Override
    public boolean isNotTopBoardIndex(int below) {
        return !isTopBoardIndex(below);
    }

    @Override
    public boolean isTopBoardIndex(int below) {
        return topBorderIndexes.contains(below);
    }

    @Override
    public boolean areSameType(Gem gemType1, Gem gemType2) {
        return gemType1.getType().equals(gemType2.getType());
    }

    @Override
    public IntArray getNeighborIndexes(int gemIndex) {
        IntArray neighborIndexes = new IntArray(4);
        neighborIndexes.add(getRightNeighborIndex(gemIndex));
        neighborIndexes.add(getBelowNeighborIndex(gemIndex));
        neighborIndexes.add(getLeftNeighborIndex(gemIndex));
        neighborIndexes.add(getAboveNeighborIndex(gemIndex));
        return neighborIndexes;
    }

    @Override
    public int getAboveNeighborIndex(int gemIndex) {
        int above = gemIndex + 1;
        return isValidIndex(above) && isNotTopBoardIndex(gemIndex) ? above : -1;
    }

    @Override
    public int getLeftNeighborIndex(int gemIndex) {
        int left = gemIndex - (int) height;
        return isValidIndex(left) ? left : -1;
    }

    @Override
    public int getBelowNeighborIndex(int gemIndex) {
        int below = gemIndex - 1;
        return isValidIndex(below) && isNotTopBoardIndex(below) ? below : -1;
    }

    @Override
    public int getRightNeighborIndex(int gemIndex) {
        int right = gemIndex + (int) height;
        return isValidIndex(right) ? right : -1;
    }

    @Override
    public boolean isValidIndex(int index) {
        return index >= minBoardIndex && index <= maxBoardIndex;
    }

    @Override
    public void checkNeighborCoordinates(float fromX, float fromY, float toX, float toY) {
        if (!areNeighborCoordinates(fromX, fromY, toX, toY)) {
            throw new NeighborException();
        }
    }

    @Override
    public boolean areNeighborCoordinates(float fromX, float fromY, float toX, float toY) {
        int fromIndex = createGemBoardIndex(fromX, fromY);
        int toIndex = createGemBoardIndex(toX, toY);
        return areNeighborIndexes(fromIndex, toIndex);
    }

    @Override
    public boolean areNeighborIndexes(int fromIndex, int toIndex) {
        return isValidIndex(fromIndex) && isValidIndex(toIndex) && getNeighborIndexes(fromIndex).contains(toIndex);
    }

    @Override
    public int createGemBoardIndex(float x, float y) {
        return (int) (x * height + y);
    }

    @Override
    public boolean areGemsReady(Gem... gems) {
        for (Gem gem : gems) {
            if (!gem.isReady()) {
                return false;
            }
        }
        return true;
    }
}
