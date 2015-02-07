package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pool;
import com.dudas.game.Board;
import com.dudas.game.Gem;
import com.dudas.game.event.MatchGameEventManager;
import com.dudas.game.model.GemType;
import com.dudas.game.provider.GemsProvider;

/**
 * Created by foxy on 04/02/2015.
 */
public class BoardController implements Board {

    public static final String TAG = BoardController.class.getName();
    public final float width;
    private final float height;

    private Pool<Array<Gem>> gemArrayPool;
    private IntArray topBorderIndexes;
    private GemsProvider gemsProvider;
    private float maxBoardIndex;
    private float minBoardIndex;
//    private IntArray rightBorderIndexes;

    public BoardController(float width, float height) {
        this.width = width;
        this.height = height;
        init();
    }

    public BoardController(float width, float height, GemsProvider provider) {
        this(width, height);
        this.gemsProvider = provider;
    }

    private void init() {
        maxBoardIndex = width * height - 1;
        minBoardIndex = 0;
        initGemArrayPool();
        initTopBorderIndexes();
//        initRightBorderIndexes();
    }

    private void initGemArrayPool() {
        this.gemArrayPool = new Pool<Array<Gem>>(){
            protected Array<Gem> newObject(){
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
    public FloatArray swap(float fromX, float fromY, float toX, float toY) {
//        Gdx.app.debug(TAG, "(" + fromX + ", " + fromY + ") -> (" + toX + ", " + toY + ")");
        if (fromX != toX && fromY != toY) {
            throw new RuntimeException("Only vertical and horizontal swaps are allowed!");
        }

        int fromIndex = createGemBoardIndex(fromX, fromY);
        int toIndex = createGemBoardIndex(toX, toY);
        swapSynchronized(fromIndex, toIndex);

        MatchGameEventManager.get().fireSwap(toX, toY, fromX, fromY);
        return FloatArray.with(toX, toY, fromX, fromY);
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

        GemType fromType = findGem(createGemBoardIndex(fromX, fromY)).getType();
        populateClearGems(fromX, fromY, gems, fromType);

        GemType toType = findGem(createGemBoardIndex(toX, toY)).getType();
        populateClearGems(toX, toY, gems, toType);

        if (gems.size >= 3) {
            MatchGameEventManager.get().fireClearSuccess(gems);
        } else {
            MatchGameEventManager.get().fireClearFail(fromX, fromY, toX, toY);
        }
        gemArrayPool.free(gems);
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
        float nextX = x + directionX;
        float nextY = y + directionY;
        int nextBoardIndex = createGemBoardIndex(nextX, nextY);
        if (minBoardIndex <= nextBoardIndex && nextBoardIndex <= maxBoardIndex && gemType.equals(findGem(nextBoardIndex).getType())) {
            Array<Gem> gemArray = resolveClear(nextX, nextY, directionX, directionY, gemType);
            gemArray.add(findGem(nextBoardIndex));
            return gemArray;
        } else {
            return gemArrayPool.obtain();
        }
    }

    public void moveGemToTop(float fromX, float fromY) {
        int boardIndexFrom = createGemBoardIndex(fromX, fromY);
        moveGemToTop(boardIndexFrom);
    }

    public void moveGemToTop(int boardIndex) {
        if (!topBorderIndexes.contains(boardIndex)) {
            int aboveBoardIndex = boardIndex + 1;
            swapSynchronized(boardIndex, aboveBoardIndex);
            moveGemToTop(aboveBoardIndex);
        }
    }

    private void synchronizeGemPosition(int boardIndex) {
        findGem(boardIndex).setIndex(boardIndex);
    }

    private int createGemBoardIndex(float x, float y) {
        return (int) (x * width + y);
    }

    private Gem findGem(int boardIndex) {
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

    @Override
    public void setGemsProvider(GemsProvider provider) {
        this.gemsProvider = provider;
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
}
