package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.dudas.game.Constants;
import com.dudas.game.controller.BoardController;
import com.dudas.game.model.GemType;
import com.dudas.game.provider.TestGemsProvider;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by foxy on 07/02/2015.
 */
public class BoardControllerInitTest {

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

    @Test
    public void testMaxBoardIndex() throws Exception {
        assertTrue(80 == boardController.maxBoardIndex);
    }

    @Test
    public void testMinBoardIndex() throws Exception {
        assertTrue(0 == boardController.minBoardIndex);
    }

    @Test
    public void testGemsSize() throws Exception {
        assertTrue(81 == boardController.getGems().size);
    }

    @Test
    public void testFindGemByIndex() throws Exception {
        assertNotNull(boardController.findGem(15));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testFindGemByIndexOutOfBound() throws Exception {
        boardController.findGem(85);
    }
}
