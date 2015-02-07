package com.dudas.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.dudas.game.Board;
import com.dudas.game.Gem;
import com.dudas.game.event.MatchGameEventManager;
import com.dudas.game.provider.GemsProvider;

/**
 * Created by foxy on 04/02/2015.
 */
public class BoardController implements Board {

    public static final String TAG = BoardController.class.getName();
    public final float width;
    private final float height;

    private Array<Gem> gemsToClear;
    private IntArray topBorderIndexes;
    private GemsProvider gemsProvider;
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
        gemsToClear = new Array<Gem>();
        initTopBorderIndexes();
//        initRightBorderIndexes();

    }

    private void initTopBorderIndexes() {
        topBorderIndexes = new IntArray((int) width);
        for (int i = 1; i <= width; i++) {
            topBorderIndexes.add((int) width * i - 1);
        }
    }

//    private void initRightBorderIndexes() {
//        rightBorderIndexes = new IntArray((int) height);
//        float tilecount = width * height;
//        for (int i = 1; i <= height; i++) {
//            rightBorderIndexes.add((int) tilecount - i);
//        }
//    }

    @Override
    public FloatArray swap(float fromX, float fromY, float toX, float toY) {
//        Gdx.app.debug(TAG, "(" + fromX + ", " + fromY + ") -> (" + toX + ", " + toY + ")");

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

    private float createGemBoardPositionX(int index) {
        return index / width;
    }

    private float createGemBoardPositionY(int index) {
        return index % height;
    }

    private Gem findGem(float boardIndex) {
        return findGem((int) boardIndex);
    }

    private Gem findGem(int boardIndex) {
        Gem gem = getGems().get(boardIndex);
        if (gem == null) {
            throw new RuntimeException("Gem on boardIndex position doesn't exist!");
        }
        return gem;
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

    @Override
    public Array<Gem> getGems() {
        return gemsProvider.getGems(width, height);
    }

    @Override
    public void clear(float fromX, float fromY, float toX, float toY) {
        gemsToClear.clear();
//        populateGemsToClear();
        Gdx.app.debug(TAG, "clear");
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

//    public IntArray getRightBorderIndexes() {
//        return rightBorderIndexes;
//    }
}
