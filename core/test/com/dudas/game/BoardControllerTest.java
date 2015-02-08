package com.dudas.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.dudas.game.controller.BoardController;
import com.dudas.game.model.GemType;
import com.dudas.game.provider.TestGemsProvider;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by foxy on 07/02/2015.
 */
public class BoardControllerTest {

    private BoardController boardController;

    @Before
    public void setUp() throws Exception {
        boardController = new BoardController(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        boardController.setGemsProvider(new TestGemsProvider());
    }

    @Test
    public void testTopBorderIndexes() throws Exception {
        IntArray topBorderIndexes = boardController.getTopBorderIndexes();
        assertEquals(topBorderIndexes.size, 9);

        assertTrue(topBorderIndexes.contains(8));
        assertTrue(topBorderIndexes.contains(17));
        assertTrue(topBorderIndexes.contains(26));
        assertTrue(topBorderIndexes.contains(35));
        assertTrue(topBorderIndexes.contains(44));
        assertTrue(topBorderIndexes.contains(53));
        assertTrue(topBorderIndexes.contains(62));
        assertTrue(topBorderIndexes.contains(71));
        assertTrue(topBorderIndexes.contains(80));
    }

//    @Test
//    public void testRightBorderIndexes() throws Exception {
//        IntArray rightBorderIndexes = boardController.getRightBorderIndexes();
//        assertEquals(9, rightBorderIndexes.size);
//
//        assertTrue(rightBorderIndexes.contains(80));
//        assertTrue(rightBorderIndexes.contains(79));
//        assertTrue(rightBorderIndexes.contains(78));
//        assertTrue(rightBorderIndexes.contains(77));
//        assertTrue(rightBorderIndexes.contains(76));
//        assertTrue(rightBorderIndexes.contains(75));
//        assertTrue(rightBorderIndexes.contains(74));
//        assertTrue(rightBorderIndexes.contains(73));
//        assertTrue(rightBorderIndexes.contains(72));
//    }


    @Test
    public void testSwap() throws Exception {
        Gem firstGem = boardController.getGems().get(0);
        Gem secondGem = boardController.getGems().get(1);
        Gem thirdGem = boardController.getGems().get(2);

        assertEquals(GemType.BLUE, firstGem.getType());
        assertTrue(0f == firstGem.getX());
        assertTrue(0f == firstGem.getY());

        assertEquals(GemType.RED, secondGem.getType());
        assertTrue(0f == secondGem.getX());
        assertTrue(1f == secondGem.getY());

        assertEquals(GemType.GREEN, thirdGem.getType());
        assertTrue(0f == thirdGem.getX());
        assertTrue(2f == thirdGem.getY());


        boardController.swap(0f, 0f, 0f, 1f);

        Gem newFirstGem = boardController.getGems().get(0);
        assertEquals(GemType.RED, newFirstGem.getType());
        assertTrue(0f == newFirstGem.getX());
        assertTrue(0f == newFirstGem.getY());

        Gem newSecondGem = boardController.getGems().get(1);
        assertEquals(GemType.BLUE, newSecondGem.getType());
        assertTrue(0f == newSecondGem.getX());
        assertTrue(1f == newSecondGem.getY());

        boardController.swap(0f, 1f, 0f, 2f);

        newSecondGem = boardController.getGems().get(1);
        assertEquals(GemType.GREEN, newSecondGem.getType());
        assertTrue(0f == newSecondGem.getX());
        assertTrue(1f == newSecondGem.getY());

        Gem newThirdGem = boardController.getGems().get(2);
        assertEquals(GemType.BLUE, newThirdGem.getType());
        assertTrue(0f == newThirdGem.getX());
        assertTrue(2f == newThirdGem.getY());

    }

    @Test(expected = RuntimeException.class)
    public void testDiagonalSwap() throws Exception {
        Gem firstGem = boardController.getGems().get(0);
        Gem secondGem = boardController.getGems().get(1);

        assertEquals(GemType.BLUE, firstGem.getType());
        assertTrue(0f == firstGem.getX());
        assertTrue(0f == firstGem.getY());

        assertEquals(GemType.RED, secondGem.getType());
        assertTrue(1f == secondGem.getX());
        assertTrue(1f == secondGem.getY());

        boardController.swap(0f, 0f, 1f, 1f);
    }

    /**
     * Moving the first gem in the first column from bottom(0)
     * to top(8). The first gem is repeatedly swaped with every above
     * gem till it reaches the top. So every gem above the first should be
     * moved below its original position.
     * @throws Exception
     */
    @Test
    public void testMoveFirstGemToTop() throws Exception {
        Array<Gem> gems = boardController.getGems();

        Gem firstGem = gems.get(0);
        assertEquals(GemType.BLUE, firstGem.getType());
        assertTrue(0f == firstGem.getX());
        assertTrue(0f == firstGem.getY());

        Gem ninethGem = gems.get(8);
        assertEquals(GemType.ORANGE, ninethGem.getType());
        assertTrue(0f == ninethGem.getX());
        assertTrue(8f == ninethGem.getY());

        Array<Gem> fallGems = new Array<Gem>();
        boardController.moveGemToTop(firstGem.getIndex(), fallGems);

        assertTrue(boardController.getHeight() == fallGems.size);

        Gem expectedFirstGem = gems.get(0);
        assertEquals(GemType.RED, expectedFirstGem.getType());
        assertTrue(0f == expectedFirstGem.getX());
        assertTrue(0f == expectedFirstGem.getY());
        assertEquals(fallGems.get(0), expectedFirstGem);

        Gem expectedSecondGem = gems.get(1);
        assertEquals(GemType.GREEN, expectedSecondGem.getType());
        assertTrue(0f == expectedSecondGem.getX());
        assertTrue(1f == expectedSecondGem.getY());
        assertEquals(fallGems.get(1), expectedSecondGem);

        // ... 2, 3, 4, 5, 6,

        Gem expectedEightGem = gems.get(7);
        assertEquals(GemType.ORANGE, expectedEightGem.getType());
        assertTrue(0f == expectedEightGem.getX());
        assertTrue(7f == expectedEightGem.getY());
        assertEquals(fallGems.get(7), expectedEightGem);


        Gem expectedNinethGem = gems.get(8);
        assertEquals(GemType.BLUE, expectedNinethGem.getType());
        assertTrue(0f == expectedNinethGem.getX());
        assertTrue(8f == expectedNinethGem.getY());
        assertEquals(fallGems.get(8), expectedNinethGem);

    }
}
