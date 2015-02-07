package com.dudas.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.dudas.game.Board;
import com.dudas.game.Gem;
import com.dudas.game.event.MatchGameEventManager;
import com.dudas.game.model.GemModel;

/**
 * Created by foxy on 04/02/2015.
 */
public class BoardController implements Board {

    public static final String TAG = BoardController.class.getName();
    public final float width;
    private final float height;

    private Array<Gem> gems;
    private Array<Gem> gemsToClear;
    private IntArray topBorderIndexes;
    private IntArray rightBorderIndexes;

    public BoardController(float width, float height) {
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        gems = new Array<Gem>();
        gemsToClear = new Array<Gem>();
        initTopBorderIndexes();
        initRightBorderIndexes();
        gems.add(new GemModel(GemModel.GemType.BLUE, 0, 0));
        gems.add(new GemModel(GemModel.GemType.RED, 0, 1));
        gems.add(new GemModel(GemModel.GemType.GREEN, 0, 2));
        gems.add(new GemModel(GemModel.GemType.PURPLE, 0, 3));
        gems.add(new GemModel(GemModel.GemType.YELLOW, 0, 4));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 0, 5));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 0, 6));
        gems.add(new GemModel(GemModel.GemType.RED, 0, 7));
        gems.add(new GemModel(GemModel.GemType.BLUE, 0, 8));

        gems.add(new GemModel(GemModel.GemType.BLUE, 1, 0));
        gems.add(new GemModel(GemModel.GemType.RED, 1, 1));
        gems.add(new GemModel(GemModel.GemType.BLUE, 1, 2));
        gems.add(new GemModel(GemModel.GemType.PURPLE, 1, 3));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 1, 4));
        gems.add(new GemModel(GemModel.GemType.BLUE, 1, 5));
        gems.add(new GemModel(GemModel.GemType.GREEN, 1, 6));
        gems.add(new GemModel(GemModel.GemType.YELLOW, 1, 7));
        gems.add(new GemModel(GemModel.GemType.BLUE, 1, 8));

        gems.add(new GemModel(GemModel.GemType.YELLOW, 2, 0));
        gems.add(new GemModel(GemModel.GemType.BLUE, 2, 1));
        gems.add(new GemModel(GemModel.GemType.RED, 2, 2));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 2, 3));
        gems.add(new GemModel(GemModel.GemType.GREEN, 2, 4));
        gems.add(new GemModel(GemModel.GemType.BLUE, 2, 5));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 2, 6));
        gems.add(new GemModel(GemModel.GemType.RED, 2, 7));
        gems.add(new GemModel(GemModel.GemType.PURPLE, 2, 8));

        gems.add(new GemModel(GemModel.GemType.BLUE, 3, 0));
        gems.add(new GemModel(GemModel.GemType.RED, 3, 1));
        gems.add(new GemModel(GemModel.GemType.GREEN, 3, 2));
        gems.add(new GemModel(GemModel.GemType.BLUE, 3, 3));
        gems.add(new GemModel(GemModel.GemType.YELLOW, 3, 4));
        gems.add(new GemModel(GemModel.GemType.RED, 3, 5));
        gems.add(new GemModel(GemModel.GemType.BLUE, 3, 6));
        gems.add(new GemModel(GemModel.GemType.PURPLE, 3, 7));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 3, 8));

        gems.add(new GemModel(GemModel.GemType.PURPLE, 4, 0));
        gems.add(new GemModel(GemModel.GemType.BLUE, 4, 1));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 4, 2));
        gems.add(new GemModel(GemModel.GemType.GREEN, 4, 3));
        gems.add(new GemModel(GemModel.GemType.RED, 4, 4));
        gems.add(new GemModel(GemModel.GemType.BLUE, 4, 5));
        gems.add(new GemModel(GemModel.GemType.YELLOW, 4, 6));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 4, 7));
        gems.add(new GemModel(GemModel.GemType.BLUE, 4, 8));

        gems.add(new GemModel(GemModel.GemType.PURPLE, 5, 0));
        gems.add(new GemModel(GemModel.GemType.YELLOW, 5, 1));
        gems.add(new GemModel(GemModel.GemType.RED, 5, 2));
        gems.add(new GemModel(GemModel.GemType.GREEN, 5, 3));
        gems.add(new GemModel(GemModel.GemType.BLUE, 5, 4));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 5, 5));
        gems.add(new GemModel(GemModel.GemType.RED, 5, 6));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 5, 7));
        gems.add(new GemModel(GemModel.GemType.BLUE, 5, 8));

        gems.add(new GemModel(GemModel.GemType.ORANGE, 6, 0));
        gems.add(new GemModel(GemModel.GemType.GREEN, 6, 1));
        gems.add(new GemModel(GemModel.GemType.BLUE, 6, 2));
        gems.add(new GemModel(GemModel.GemType.PURPLE, 6, 3));
        gems.add(new GemModel(GemModel.GemType.RED, 6, 4));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 6, 5));
        gems.add(new GemModel(GemModel.GemType.BLUE, 6, 6));
        gems.add(new GemModel(GemModel.GemType.YELLOW, 6, 7));
        gems.add(new GemModel(GemModel.GemType.YELLOW, 6, 8));

        gems.add(new GemModel(GemModel.GemType.RED, 7, 0));
        gems.add(new GemModel(GemModel.GemType.BLUE, 7, 1));
        gems.add(new GemModel(GemModel.GemType.PURPLE, 7, 2));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 7, 3));
        gems.add(new GemModel(GemModel.GemType.BLUE, 7, 4));
        gems.add(new GemModel(GemModel.GemType.YELLOW, 7, 5));
        gems.add(new GemModel(GemModel.GemType.RED, 7, 6));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 7, 7));
        gems.add(new GemModel(GemModel.GemType.GREEN, 7, 8));

        gems.add(new GemModel(GemModel.GemType.BLUE, 8, 0));
        gems.add(new GemModel(GemModel.GemType.PURPLE, 8, 1));
        gems.add(new GemModel(GemModel.GemType.YELLOW, 8, 2));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 8, 3));
        gems.add(new GemModel(GemModel.GemType.BLUE, 8, 4));
        gems.add(new GemModel(GemModel.GemType.RED, 8, 5));
        gems.add(new GemModel(GemModel.GemType.GREEN, 8, 6));
        gems.add(new GemModel(GemModel.GemType.YELLOW, 8, 7));
        gems.add(new GemModel(GemModel.GemType.ORANGE, 8, 8));
    }

    private void initTopBorderIndexes() {
        topBorderIndexes = new IntArray((int) width);
        for (int i = 1; i <= width; i++) {
            topBorderIndexes.add((int) width * i - 1);
        }
    }

    private void initRightBorderIndexes() {
        rightBorderIndexes = new IntArray((int) height);
        int tilecount = (int) width * (int) height;
        for (int i = 1; i <= height; i++) {
            rightBorderIndexes.add(tilecount - i);
        }
    }

    @Override
    public FloatArray swap(float fromX, float fromY, float toX, float toY) {
//        Gdx.app.debug(TAG, "(" + fromX + ", " + fromY + ") -> (" + toX + ", " + toY + ")");


        Gem fromGem = findGem(fromX, fromY);
        fromGem.block();
        Gem toGem = findGem(toX, toY);
        toGem.block();

        fromGem.setX(toX);
        fromGem.setY(toY);
        toGem.setX(fromX);
        toGem.setY(fromY);
        MatchGameEventManager.get().fireSwap(fromGem.getX(), fromGem.getY(), toGem.getX(), toGem.getY());
        return FloatArray.with(fromGem.getX(), fromGem.getY(), toGem.getX(), toGem.getY());
    }

    /**
     * For now there are only vertical and horizontal moves allowed.
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    private void moveGemTo(float fromX, float fromY, float toX, float toY) {
        float boardIndexFrom = createGemBoardIndex(fromX, fromY);
        float boardIndexTo = createGemBoardIndex(toX, toY);

        float step;
        float direction = (boardIndexFrom - boardIndexTo) / Math.abs(boardIndexFrom - boardIndexTo);
        if ((boardIndexFrom - boardIndexTo) % height == 0) {
            step = direction * height;
        } else {
            step = direction;
        }

//        float xDirection = (fromX - toX) / Math.abs(fromX - toX);
//        float yDirection = (fromY - toY) / Math.abs(fromY - toY);

        moveToStep(boardIndexFrom, boardIndexFrom + step);
    }

    private void moveToStep(float fromIndex, float toIndex) {

    }

    private float createGemBoardIndex(float x, float y) {
        return x * width + y;
    }

    private Gem findGem(float boardIndex) {
        return findGem((int) boardIndex);
    }

    private Gem findGem(int boardIndex) {
        Gem gem = gems.get(boardIndex);
        if (gem == null) {
            throw new RuntimeException("Gem on boardIndex position doesn't exist!");
        }
        return gem;
    }

    @Deprecated
    private Gem findGem(float x, float y) {
        for (Gem gem : gems) {
            if (gem.getX() == x && gem.getY() == y) {
                return gem;
            }
        }
        return null;
    }

    @Override
    public Array<Gem> getGems() {
        return gems;
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

    public IntArray getTopBorderIndexes() {
        return topBorderIndexes;
    }

    public IntArray getRightBorderIndexes() {
        return rightBorderIndexes;
    }
}
