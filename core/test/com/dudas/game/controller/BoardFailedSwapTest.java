package com.dudas.game.controller;

import com.dudas.game.Constants;
import com.dudas.game.model.Gem;
import com.dudas.game.exception.NeighborException;
import com.dudas.game.model.GemType;
import com.dudas.game.model.provider.PixmapGemsProvider;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by foxy on 07/02/2015.
 */
public class BoardFailedSwapTest extends BaseSwapBoardTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        board = new BoardController(
                Constants.BOARD_WIDTH,
                Constants.BOARD_HEIGHT,
                new PixmapGemsProvider(TESTBOARD_PNG),
                eventManager
        );
    }

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
        expectedSwapEvents.add(new ExpectedSwapEvent().withSwapTypes(GemType.BLUE, GemType.RED));
        expectedBackSwapEvents.add(new ExpectedSwapEvent().withSwapTypes(GemType.BLUE, GemType.RED));
        expectedFinalBoard = TESTBOARD_PNG;

        verifyBackSwapFlow(0, 0, 0, 1);
    }

    @Test
    public void testRightNighborSwapFlow() throws Exception {
        expectedSwapEvents.add(new ExpectedSwapEvent().withSwapTypes(GemType.BLUE, GemType.YELLOW));
        expectedBackSwapEvents.add(new ExpectedSwapEvent().withSwapTypes(GemType.BLUE, GemType.YELLOW));
        expectedFinalBoard = TESTBOARD_PNG;

        verifyBackSwapFlow(1, 0, 2, 0);
    }

    @Test
    public void testDownNighborSwapFlow() throws Exception {
        expectedSwapEvents.add(new ExpectedSwapEvent().withSwapTypes(GemType.RED, GemType.BLUE));
        expectedBackSwapEvents.add(new ExpectedSwapEvent().withSwapTypes(GemType.RED, GemType.BLUE));
        expectedFinalBoard = TESTBOARD_PNG;

        verifyBackSwapFlow(1, 1, 1, 0);
    }

    @Test
    public void testLeftNighborSwapFlow() throws Exception {
        expectedSwapEvents.add(new ExpectedSwapEvent().withSwapTypes(GemType.BLUE, GemType.BLUE));
        expectedBackSwapEvents.add(new ExpectedSwapEvent().withSwapTypes(GemType.BLUE, GemType.BLUE));
        expectedFinalBoard = TESTBOARD_PNG;

        verifyBackSwapFlow(1, 0, 0, 0);
    }

}
