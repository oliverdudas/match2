package com.dudas.game.controller;

import com.dudas.game.Gem;
import com.dudas.game.exception.NeighborException;
import com.dudas.game.model.GemType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by foxy on 07/02/2015.
 */
public class BoardFailedSwapTest extends BaseBoardTest {

    @Test(expected = NeighborException.class)
    public void testDiagonalNeighborSwap() throws Exception {
        Gem fromGem = board.getGems().get(0); // [0, 0, BLUE]
        assertEquals(GemType.BLUE, fromGem.getType());
        Gem toGem = board.getGems().get(10); // [1, 1, RED]
        assertEquals(GemType.RED, toGem.getType());

        board.swap(fromGem.getX(), fromGem.getY(), toGem.getX(), toGem.getY());
    }

    @Test(expected = NeighborException.class)
    public void testVerticalNotNeighborSwap() throws Exception {
        Gem fromGem = board.getGems().get(0); // [0, 0, BLUE]
        assertEquals(GemType.BLUE, fromGem.getType());
        Gem toGem = board.getGems().get(2); // [0, 2, GREEN]
        assertEquals(GemType.GREEN, toGem.getType());

        board.swap(fromGem.getX(), fromGem.getY(), toGem.getX(), toGem.getY());
    }

    @Test(expected = NeighborException.class)
    public void testHorizontalNotNeighborSwap() throws Exception {
        Gem fromGem = board.getGems().get(0); // [0, 0, BLUE]
        assertEquals(GemType.BLUE, fromGem.getType());
        Gem toGem = board.getGems().get(18); // [2, 0, YELLOW]
        assertEquals(GemType.YELLOW, toGem.getType());

        board.swap(fromGem.getX(), fromGem.getY(), toGem.getX(), toGem.getY());
    }

    @Test(expected = NeighborException.class)
    public void testNotHorizontalAndNotVerticalNeighborSwap() throws Exception {
        Gem fromGem = board.getGems().get(0); // [0, 0, BLUE]
        assertEquals(GemType.BLUE, fromGem.getType());
        Gem toGem = board.getGems().get(13); // [1, 4, ORANGE]
        assertEquals(GemType.ORANGE, toGem.getType());

        board.swap(fromGem.getX(), fromGem.getY(), toGem.getX(), toGem.getY());
    }

    @Test(expected = NeighborException.class)
    public void testInvalidNeighborSwap() throws Exception {
        board.swap(10, 5, 2, 13);
    }

    @Test
    public void testUpNeighborSwapFlow() throws Exception {
        verifyBackSwapFlow(0, 0, GemType.BLUE, 0, 1, GemType.RED);
    }

    @Test
    public void testRightNighborSwapFlow() throws Exception {
        verifyBackSwapFlow(1, 0, GemType.BLUE, 2, 0, GemType.YELLOW);
    }

    @Test
    public void testDownNighborSwapFlow() throws Exception {
        verifyBackSwapFlow(1, 1, GemType.RED, 1, 0, GemType.BLUE);
    }

    @Test
    public void testLeftNighborSwapFlow() throws Exception {
        verifyBackSwapFlow(1, 0, GemType.BLUE, 0, 0, GemType.BLUE);
    }

}
