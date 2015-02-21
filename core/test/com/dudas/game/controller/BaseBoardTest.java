package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Board;
import com.dudas.game.EventManager;
import com.dudas.game.Gem;
import com.dudas.game.model.GemType;
import com.dudas.game.provider.PixmapGemsProvider;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by OLO on 20. 2. 2015
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class BaseBoardTest {

    protected static final String TESTBOARD_PNG = "testboard.png";

    @Mock
    protected EventManager eventManager;
    protected Board board;

    protected void verifySwapEvent(float x1, float y1, GemType expectedGemType1, float x2, float y2, GemType expectedGemType2) {
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

    protected void verifyBoard(PixmapGemsProvider pixmapGemsProvider) {
        Array<Gem> expectedGems = pixmapGemsProvider.getGems(board.getWidth(), board.getHeight());
        for (Gem gem : board.getGems()) {
            int boardIndex = coordinatesToIndex(gem.getX(), gem.getY());
            Gem expectedGem = expectedGems.get(boardIndex);
            assertTrue(expectedGem.getIndex() == gem.getIndex());
            assertEquals(expectedGem.getType(), gem.getType());
            assertEquals(expectedGem.getState(), gem.getState());
        }
    }

    protected void verifyBoardReady() {
        for (Gem gem : board.getGems()) {
            assertTrue(gem.isReady());
        }
    }

    protected void verifyBlockedGems(Gem... gems) {
        for (Gem gem : gems) {
            assertTrue(gem.isBlocked());
        }
    }

    protected int coordinatesToIndex(float x, float y) {
        return (int) (x * board.getWidth() + y);
    }
}
