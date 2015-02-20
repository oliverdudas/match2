package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pool;
import com.dudas.game.Board;
import com.dudas.game.EventManager;
import com.dudas.game.Gem;
import com.dudas.game.exception.NeighborException;
import com.dudas.game.model.GemType;
import com.dudas.game.provider.GemsProvider;

/**
 * Created by foxy on 04/02/2015.
 */
public class BoardController implements Board {

    public static final String TAG = BoardController.class.getName();

    public final float width;
    private final float height;

    private EventManager eventManager;
    private GemsProvider gemsProvider;

    private Pool<Array<Gem>> gemArrayPool;
    private IntArray topBorderIndexes;
    public float maxBoardIndex;
    public float minBoardIndex;

    public BoardController(float width, float height) {
        this.width = width;
        this.height = height;
        init();
    }

    public BoardController(float width, float height, GemsProvider provider, EventManager eventManager) {
        this(width, height);
        this.gemsProvider = provider;
        this.eventManager = eventManager;
    }

    private void init() {
        maxBoardIndex = width * height - 1;
        minBoardIndex = 0;
        initGemArrayPool();
        initTopBorderIndexes();
//        initRightBorderIndexes();
    }

    private void initGemArrayPool() {
        this.gemArrayPool = new Pool<Array<Gem>>() {
            protected Array<Gem> newObject() {
                return new Array<Gem>();
            }

            @Override
            public Array<Gem> obtain() {
                Array<Gem> gems = super.obtain();
                gems.clear();
                return gems;
            }
        };
    }

    private void initTopBorderIndexes() {
        topBorderIndexes = new IntArray((int) width);
        for (int i = 1; i <= width; i++) {
            topBorderIndexes.add((int) width * i - 1);
        }
    }

    @Override
    public void swap(float fromX, float fromY, float toX, float toY) {
        checkNeighborCoordinates(fromX, fromY, toX, toY);

        int fromIndex = createGemBoardIndex(fromX, fromY);
        int toIndex = createGemBoardIndex(toX, toY);
        swapSynchronized(fromIndex, toIndex);

        Gem toGem = findGem(toIndex);
        Gem fromGem = findGem(fromIndex);

        toGem.block();
        fromGem.block();

        eventManager.fireSwap(toGem, fromGem);
    }

    /**
     * Swaps indexes in the gems array and also swaps both gems positions
     *
     * @param fromIndex index of forst gem to swap
     * @param toIndex   index of second gem to swap
     */
    private void swapSynchronized(int fromIndex, int toIndex) {
        getGems().swap(fromIndex, toIndex);
        synchronizeGemPosition(fromIndex);
        synchronizeGemPosition(toIndex);
    }

    @Override
    public void clear(float fromX, float fromY, float toX, float toY) {
        Array<Gem> gems = gemArrayPool.obtain();

        Gem fromGem = findGem(createGemBoardIndex(fromX, fromY));
        GemType fromType = fromGem.getType();
        populateClearGems(fromX, fromY, gems, fromType);

        Gem toGem = findGem(createGemBoardIndex(toX, toY));
        GemType toType = toGem.getType();
        populateClearGems(toX, toY, gems, toType);

        if (gems.size >= 3) {
            Gem unclearedGem = resolveUnclearedGem(gems, fromGem, toGem);
            eventManager.fireClearSuccess(gems, unclearedGem);
        } else {
            eventManager.fireClearFail(fromX, fromY, toX, toY);
        }
    }

    private Gem resolveUnclearedGem(Array<Gem> gems, Gem fromGem, Gem toGem) {
        Gem unclearedGem; // during swap and clearfinding on from 2 gems must not be cleared
        if (!gems.contains(fromGem, false)) {
            unclearedGem = fromGem;
        } else if (!gems.contains(toGem, false)) {
            unclearedGem = toGem;
        } else {
            unclearedGem = null;
        }
        return unclearedGem;
    }

    private Array<Gem> populateClearGems(float x, float y, Array<Gem> gems, GemType gemType) {
        Array<Gem> upClearArray = resolveClear(x, y, 0f, 1f, gemType);  // up
        Array<Gem> downClearArray = resolveClear(x, y, 0f, -1f, gemType); // down

        Gem baseGem = findGem(createGemBoardIndex(x, y));

        if (upClearArray.size + downClearArray.size >= 2) {
            gems.add(baseGem);
            gems.addAll(upClearArray);
            gems.addAll(downClearArray);
        }
        gemArrayPool.free(upClearArray);
        gemArrayPool.free(downClearArray);

        Array<Gem> rightClearArray = resolveClear(x, y, 1f, 0f, gemType);  // right
        Array<Gem> leftClearArray = resolveClear(x, y, -1f, 0f, gemType); // left
        if (rightClearArray.size + leftClearArray.size >= 2) {
            if (!gems.contains(baseGem, false)) {
                gems.add(baseGem);
            }
            gems.addAll(rightClearArray);
            gems.addAll(leftClearArray);
        }
        gemArrayPool.free(rightClearArray);
        gemArrayPool.free(leftClearArray);

        return gems;
    }

    private Array<Gem> resolveClear(float x, float y, float directionX, float directionY, GemType gemType) {
        int actualBoardIndex = createGemBoardIndex(x, y);
        float nextX = x + directionX;
        float nextY = y + directionY;
        int nextBoardIndex = createGemBoardIndex(nextX, nextY);
        if (areNeighborIndexes(actualBoardIndex, nextBoardIndex) && areTheSameType(gemType, findGem(nextBoardIndex).getType())) {
            Array<Gem> gemArray = resolveClear(nextX, nextY, directionX, directionY, gemType);
            gemArray.add(findGem(nextBoardIndex));
            return gemArray;
        } else {
            return gemArrayPool.obtain();
        }
    }

    private boolean areTheSameType(GemType gemType, GemType type) {
        return gemType.equals(type);
    }

    @Override
    public void backSwap(float fromX, float fromY, float toX, float toY) {
        int fromIndex = createGemBoardIndex(fromX, fromY);
        int toIndex = createGemBoardIndex(toX, toY);
        swapSynchronized(fromIndex, toIndex);

        eventManager.fireBackSwap(findGem(fromIndex), findGem(toIndex));
    }

    @Override
    public void setGemReady(float x, float y) {
        findGem(createGemBoardIndex(x, y)).setReady();
    }

//    @Override
//    public void fall(Array<Gem> clearedGems) {
//        Array<Gem> fallGems = gemArrayPool.obtain();
//        for (Gem gem : clearedGems) {
//            moveGemToTop(gem.getIndex(), fallGems);
//        }
//        eventManager.fireFall(fallGems);
//        gemArrayPool.free(clearedGems); // free gems array from clear method TODO: create test for obtaining and freeing
//    }

    public void fall(Array<Gem> clearedGems) {
        Array<Gem> fallGems = gemArrayPool.obtain();
        for (Gem gem : clearedGems) {
            moveGemToTop(gem.getIndex(), fallGems);
        }
        eventManager.fireFall(fallGems);
        gemArrayPool.free(clearedGems); // free gems array from clear method TODO: create test for obtaining and freeing
    }

    @Override
    public void clearFallen(Array<Gem> gems) {
        Array<Gem> clearGems = gemArrayPool.obtain();

        for (Gem gem : gems) {
            gem.setReady(); // TODO: maybe this should be done in BoardRenderer somehow
            populateClearGems(gem.getX(), gem.getY(), clearGems, gem.getType());
        }

        if (clearGems.size > 2) {
            eventManager.fireClearSuccess(clearGems, null);
            // free celarGems in the pool
        } else {
//            END OF THE WHOLE SWAP, CLEAR, FALL CYCLE
            gemArrayPool.free(gems); // setting free fallGems from fall(...)
            gemArrayPool.free(clearGems);
        }

    }

    /**
     * Moving the gem in its column from original position(0, 1, ..., height)
     * to top(height). The gem is repeatedly swaped with every above
     * gem till it reaches the top. So every gem above the first should be
     * moved below its original position.
     */
//    public void moveGemToTop(int boardIndex, Array<Gem> fallGems) {
//        Gem gemToAdd;
//        if (isNotTopBoardIndex(boardIndex)) {
//            int aboveBoardIndex = boardIndex + 1;
//            gemToAdd = findGem(aboveBoardIndex);
//            swapSynchronized(boardIndex, aboveBoardIndex);
//            moveGemToTop(aboveBoardIndex, fallGems);
//        } else {
//            Gem synchronizedGem = synchronizeGemPosition(boardIndex);
//            gemToAdd = synchronizedGem;
//        }
//        if (!fallGems.contains(gemToAdd, false)) { // maybe a Set would be better, to prevent duplicate items
//            fallGems.add(gemToAdd);
//        }
//    }

    public void moveGemToTop(int gemIndex, Array<Gem> fallGems) {
        int aboveGemIndex = getAboveNeighborIndex(gemIndex);
        if (isValidIndex(gemIndex) && isValidIndex(aboveGemIndex)) {
            swapSynchronized(gemIndex, aboveGemIndex);
            moveGemToTop(aboveGemIndex, fallGems);
        }
        Gem gem = findGem(gemIndex); // TODO: refactor
        if (!fallGems.contains(gem, false)) { // maybe a Set would be better, to prevent duplicate items
            fallGems.add(gem);
        }
    }

    private Gem synchronizeGemPosition(int boardIndex) {
        Gem gem = findGem(boardIndex);
        gem.setIndex(boardIndex);
        return gem;
    }

    private int createGemBoardIndex(float x, float y) {
        return (int) (x * width + y);
    }

    public Gem findGem(int boardIndex) {
        Gem gem = getGems().get(boardIndex);
        if (gem == null) {
            throw new RuntimeException("Gem on boardIndex position doesn't exist!");
        }
        return gem;
    }

    @Override
    public Array<Gem> getGems() {
        return gemsProvider.getGems(width, height);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public IntArray getTopBorderIndexes() {
        return topBorderIndexes;
    }

    private float createGemBoardPositionX(int index) {
        return index / width;
    }

    private float createGemBoardPositionY(int index) {
        return index % height;
    }

    private Gem findGem(float boardIndex) {
        return findGem((int) boardIndex);
    }


    @Override
    public void setGemsProvider(GemsProvider provider) {
        this.gemsProvider = provider;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setEventProcessor(BoardEventListener matchGameListener) {
        eventManager.attach(matchGameListener);
    }
//    @Deprecated
//    private Gem findGem(float x, float y) {
//        for (Gem gem : getGems()) {
//            if (gem.getX() == x && gem.getY() == y) {
//                return gem;
//            }
//        }
//        return null;
//    }

//    public IntArray getRightBorderIndexes() {
//        return rightBorderIndexes;
//    }

//    private void initRightBorderIndexes() {
//        rightBorderIndexes = new IntArray((int) height);
//        float tilecount = width * height;
//        for (int i = 1; i <= height; i++) {
//            rightBorderIndexes.add((int) tilecount - i);
//        }
//    }

    private IntArray getNeighborIndexes(int gemIndex) {
        IntArray neighborIndexes = new IntArray(4);
        neighborIndexes.add(getRightNeighborIndex(gemIndex));
        neighborIndexes.add(getBelowNeighborIndex(gemIndex));
        neighborIndexes.add(getLeftNeighborIndex(gemIndex));
        neighborIndexes.add(getAboveNeighborIndex(gemIndex));
        return neighborIndexes;
    }

    private int getAboveNeighborIndex(int gemIndex) {
        int above = gemIndex + 1;
        return isValidIndex(above) && isNotTopBoardIndex(gemIndex) ? above : -1;
    }

    private int getLeftNeighborIndex(int gemIndex) {
        int left = gemIndex - (int) height;
        return isValidIndex(left) ? left : -1;
    }

    private int getBelowNeighborIndex(int gemIndex) {
        int below = gemIndex - 1;
        return isValidIndex(below) && isNotTopBoardIndex(below) ? below : -1;
    }

    private int getRightNeighborIndex(int gemIndex) {
        int right = gemIndex + (int) height;
        return isValidIndex(right) ? right : -1;
    }

    private boolean isNotTopBoardIndex(int below) {
        return !topBorderIndexes.contains(below);
    }

    private boolean isValidIndex(int index) {
        return index >= minBoardIndex && index <= maxBoardIndex;
    }

    private void checkNeighborCoordinates(float fromX, float fromY, float toX, float toY) {
        if (!areNeighborCoordinates(fromX, fromY, toX, toY)) {
            throw new NeighborException();
        }
    }

    private boolean areNeighborCoordinates(float fromX, float fromY, float toX, float toY) {
        int fromIndex = createGemBoardIndex(fromX, fromY);
        int toIndex = createGemBoardIndex(toX, toY);
        return areNeighborIndexes(fromIndex, toIndex);
    }

    private boolean areNeighborIndexes(int fromIndex, int toIndex) {
        return isValidIndex(fromIndex) && isValidIndex(toIndex) && getNeighborIndexes(fromIndex).contains(toIndex);
    }
}
