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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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

    @Test
    public void testVerticalNeighborSwap() throws Exception {
        Gem fromGem = board.getGems().get(0); // [0, 0, BLUE]
        assertEquals(GemType.BLUE, fromGem.getType());
        Gem toGem = board.getGems().get(1); // [0, 1, RED]
        assertEquals(GemType.RED, toGem.getType());

        ArgumentCaptor<Gem> fromGemCaptor = ArgumentCaptor.forClass(Gem.class);
        ArgumentCaptor<Gem> toGemCaptor = ArgumentCaptor.forClass(Gem.class);

        board.swap(fromGem.getX(), fromGem.getY(), toGem.getX(), toGem.getY());

        verify(eventManager).fireSwap(toGemCaptor.capture(), fromGemCaptor.capture());

        Gem capturedFromGem = fromGemCaptor.getValue();
        Gem capturedToGem = toGemCaptor.getValue();

        assertEquals(GemType.RED, capturedFromGem.getType());
        assertTrue(0 == capturedFromGem.getX());
        assertTrue(0 == capturedFromGem.getY());

        assertEquals(GemType.BLUE, capturedToGem.getType());
        assertTrue(0 == capturedToGem.getX());
        assertTrue(1 == capturedToGem.getY());
    }

    @Test
    public void testHorizontalNeighborSwap() throws Exception {
        Gem fromGem = board.getGems().get(2); // [0, 2, GREEN]
        assertEquals(GemType.GREEN, fromGem.getType());
        Gem toGem = board.getGems().get(11); // [1, 2, BLUE]
        assertEquals(GemType.BLUE, toGem.getType());

        ArgumentCaptor<Gem> fromGemCaptor = ArgumentCaptor.forClass(Gem.class);
        ArgumentCaptor<Gem> toGemCaptor = ArgumentCaptor.forClass(Gem.class);

        board.swap(fromGem.getX(), fromGem.getY(), toGem.getX(), toGem.getY());

        verify(eventManager).fireSwap(toGemCaptor.capture(), fromGemCaptor.capture());

        Gem capturedFromGem = fromGemCaptor.getValue();
        Gem capturedToGem = toGemCaptor.getValue();

        assertEquals(GemType.BLUE, capturedFromGem.getType());
        assertTrue(0 == capturedFromGem.getX());
        assertTrue(2 == capturedFromGem.getY());

        assertEquals(GemType.GREEN, capturedToGem.getType());
        assertTrue(1 == capturedToGem.getX());
        assertTrue(2 == capturedToGem.getY());
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
    public void testSwapBackFlow() throws Exception {
        verifyBoardReady();

        verifyBeforeSwap(0, 0, GemType.BLUE, 0, 1, GemType.RED);
        board.swap(0, 0, 0, 1);
        verifySwap(0, 0, GemType.RED, 0, 1, GemType.BLUE);
        board.clear(0, 1, 0, 0);
        verifyClearFailed(0, 1, GemType.BLUE, 0, 0, GemType.RED);
        board.backSwap(0, 1, 0, 0);
        verifyBackSwap(0, 0, GemType.BLUE, 0, 1, GemType.RED);
        board.setGemReady(0, 0);
        board.setGemReady(0, 1);

        verifyBoardReady();

        verifyBoard(new PixmapGemsProvider(TESTBOARD_PNG));
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

    private void verifyBeforeSwap(float fromX, float fromY, GemType expectedFromGemType, float toX, float toY, GemType expectedToGemType) {
        verifyReadyGems(fromX, fromY, toX, toY);
        verifySwapGemTypes(fromX, fromY, expectedFromGemType, toX, toY, expectedToGemType);
    }

    private void verifySwapGemTypes(float fromX, float fromY, GemType expectedFromGemType, float toX, float toY, GemType expectedToGemType) {
        Gem fromGem = board.getGems().get(coordinatesToIndex(fromX, fromY));
        assertEquals(fromGem.getType(), expectedFromGemType);
        Gem toGem = board.getGems().get(coordinatesToIndex(toX, toY));
        assertEquals(toGem.getType(), expectedToGemType);
    }

    private void verifySwap(float fromX, float fromY, GemType expectedFromGemType, float toX, float toY, GemType expectedToGemType) {
        verifyBlockedGems(fromX, fromY, toX, toY);
        verifySwapGemTypes(fromX, fromY, expectedFromGemType, toX, toY, expectedToGemType);
        verify(eventManager).fireSwap(any(Gem.class), any(Gem.class));
    }

    private void verifyClearFailed(float fromX, float fromY, GemType expectedFromGemType, float toX, float toY, GemType expectedToGemType) {
        verifySwapGemTypes(fromX, fromY, expectedFromGemType, toX, toY, expectedToGemType);
        verify(eventManager).fireClearFail(fromX, fromY, toX, toY);
    }

    private void verifyBackSwap(float fromX, float fromY, GemType expectedFromGemType, float toX, float toY, GemType expectedToGemType) {
        verifySwapGemTypes(fromX, fromY, expectedFromGemType, toX, toY, expectedToGemType);
        Array<Gem> gems = board.getGems();
        verify(eventManager).fireBackSwap(gems.get(coordinatesToIndex(toX, toY)), gems.get(coordinatesToIndex(fromX, fromY)));
    }

    private int coordinatesToIndex(float x, float y) {
        return (int) (x * board.getWidth() + y);
    }

    private void verifyBlockedGems(float fromX, float fromY, float toX, float toY) {
        Gem fromGem = board.getGems().get(coordinatesToIndex(fromX, fromY));
        assertTrue(fromGem.isBlocked());
        Gem toGem = board.getGems().get(coordinatesToIndex(toX, toY));
        assertTrue(toGem.isBlocked());
    }

    private void verifyReadyGems(float fromX, float fromY, float toX, float toY) {
        Gem fromGem = board.getGems().get(coordinatesToIndex(fromX, fromY));
        assertTrue(fromGem.isReady());
        Gem toGem = board.getGems().get(coordinatesToIndex(toX, toY));
        assertTrue(toGem.isReady());
    }
}
