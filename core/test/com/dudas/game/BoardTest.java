package com.dudas.game;

import com.badlogic.gdx.utils.IntArray;
import com.dudas.game.controller.BoardController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by foxy on 07/02/2015.
 */
public class BoardTest {

    private BoardController boardController;

    @Before
    public void setUp() throws Exception {
        boardController = new BoardController(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
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
    public void testRightBorderIndexes() throws Exception {
        IntArray rightBorderIndexes = boardController.getRightBorderIndexes();
        assertEquals(9, rightBorderIndexes.size);

        assertTrue(rightBorderIndexes.contains(80));
        assertTrue(rightBorderIndexes.contains(79));
        assertTrue(rightBorderIndexes.contains(78));
        assertTrue(rightBorderIndexes.contains(77));
        assertTrue(rightBorderIndexes.contains(76));
        assertTrue(rightBorderIndexes.contains(75));
        assertTrue(rightBorderIndexes.contains(74));
        assertTrue(rightBorderIndexes.contains(73));
        assertTrue(rightBorderIndexes.contains(72));
    }
}
