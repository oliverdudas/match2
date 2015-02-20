package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Board;
import com.dudas.game.Constants;
import com.dudas.game.EventManager;
import com.dudas.game.Gem;
import com.dudas.game.exception.NeighborException;
import com.dudas.game.model.GemType;
import com.dudas.game.provider.PixmapGemsProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by foxy on 07/02/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class BoardSwapTest {

    public static final String TESTBOARD_PNG = "testboard.png";
    @Mock
    private EventManager eventManager;

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new BoardController(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        board.setGemsProvider(new PixmapGemsProvider(TESTBOARD_PNG));
        board.setEventManager(eventManager);
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
    public void testUpBackSwapFlow() throws Exception {
        verifyBackSwapFlow(0, 0, GemType.BLUE, 0, 1, GemType.RED);
    }

    @Test
    public void testRightBackSwapFlow() throws Exception {
        verifyBackSwapFlow(1, 0, GemType.BLUE, 2, 0, GemType.YELLOW);
    }

    @Test
    public void testDownBackSwapFlow() throws Exception {
        verifyBackSwapFlow(1, 1, GemType.RED, 1, 0, GemType.BLUE);
    }

    @Test
    public void testLeftSameColorBackSwapFlow() throws Exception {
        verifyBackSwapFlow(1, 0, GemType.BLUE, 0, 0, GemType.BLUE);
    }

    private void verifyBackSwapFlow(float x1, float y1, GemType expectedGemType1, float x2, float y2, GemType expectedGemType2) {
        verifyBoardReady();
        board.swap(x1, y1, x2, y2);
        verifySwapEvent(x1, y1, expectedGemType1, x2, y2, expectedGemType2);
        verifyClearFailEvent(x1, y1, expectedGemType1, x2, y2, expectedGemType2);
        verifyBackSwapEvent(x1, y1, expectedGemType1, x2, y2, expectedGemType2);
        verifyBoardReady();
        verifyBoard(new PixmapGemsProvider(TESTBOARD_PNG));
    }

    private void verifyBackSwapEvent(float x1, float y1, GemType expectedGemType1, float x2, float y2, GemType expectedGemType2) {
        ArgumentCaptor<TwoGemsBoardEvent> backSwapEventCaptor = ArgumentCaptor.forClass(TwoGemsBoardEvent.class);
        verify(eventManager).fireBackSwap(backSwapEventCaptor.capture());
        TwoGemsBoardEvent backSwapEvent = backSwapEventCaptor.getValue();
        assertNotNull(backSwapEvent);
        assertTrue(backSwapEvent.getFromGem().getX() == x2);
        assertTrue(backSwapEvent.getFromGem().getY() == y2);
        assertTrue(backSwapEvent.getFromGem().isBlocked());
        assertEquals(expectedGemType2, backSwapEvent.getFromGem().getType());
        assertTrue(backSwapEvent.getToGem().getX() == x1);
        assertTrue(backSwapEvent.getToGem().getY() == y1);
        assertTrue(backSwapEvent.getToGem().isBlocked());
        assertEquals(expectedGemType1, backSwapEvent.getToGem().getType());
        backSwapEvent.complete();
    }

    private void verifyClearFailEvent(float x1, float y1, GemType expectedGemType1, float x2, float y2, GemType expectedGemType2) {
        ArgumentCaptor<TwoGemsBoardEvent> clearFailEventCaptor = ArgumentCaptor.forClass(TwoGemsBoardEvent.class);
        verify(eventManager).fireClearFail(clearFailEventCaptor.capture());
        TwoGemsBoardEvent clearFailEvent = clearFailEventCaptor.getValue();
        assertNotNull(clearFailEvent);
        assertTrue(clearFailEvent.getFromGem().getX() == x1);
        assertTrue(clearFailEvent.getFromGem().getY() == y1);
        assertTrue(clearFailEvent.getFromGem().isBlocked());
        assertEquals(expectedGemType2, clearFailEvent.getFromGem().getType());
        assertTrue(clearFailEvent.getToGem().getX() == x2);
        assertTrue(clearFailEvent.getToGem().getY() == y2);
        assertTrue(clearFailEvent.getToGem().isBlocked());
        assertEquals(expectedGemType1, clearFailEvent.getToGem().getType());
        clearFailEvent.complete();
    }

    private void verifySwapEvent(float x1, float y1, GemType expectedGemType1, float x2, float y2, GemType expectedGemType2) {
        ArgumentCaptor<TwoGemsBoardEvent> swapEventCaptor = ArgumentCaptor.forClass(TwoGemsBoardEvent.class);
        verify(eventManager).fireSwap(swapEventCaptor.capture());
        TwoGemsBoardEvent swapEvent = swapEventCaptor.getValue();

        assertNotNull(swapEvent);
        assertTrue(swapEvent.getFromGem().getX() == x1);
        assertTrue(swapEvent.getFromGem().getY() == y1);
        assertTrue(swapEvent.getFromGem().isBlocked());
        assertEquals(expectedGemType2, swapEvent.getFromGem().getType());
        assertTrue(swapEvent.getToGem().getX() == x2);
        assertTrue(swapEvent.getToGem().getY() == y2);
        assertTrue(swapEvent.getToGem().isBlocked());
        assertEquals(expectedGemType1, swapEvent.getToGem().getType());
        swapEvent.complete();
    }

    private void verifyBoard(PixmapGemsProvider pixmapGemsProvider) {
        Array<Gem> expectedGems = pixmapGemsProvider.getGems(board.getWidth(), board.getHeight());
        for (Gem gem : board.getGems()) {
            int boardIndex = coordinatesToIndex(gem.getX(), gem.getY());
            Gem expectedGem = expectedGems.get(boardIndex);
            assertTrue(expectedGem.getIndex() == gem.getIndex());
            assertEquals(expectedGem.getType(), gem.getType());
            assertEquals(expectedGem.getState(), gem.getState());
        }
    }

    private void verifyBoardReady() {
        for (Gem gem : board.getGems()) {
            assertTrue(gem.isReady());
        }
    }

    private int coordinatesToIndex(float x, float y) {
        return (int) (x * board.getWidth() + y);
    }

}
