package com.dudas.game.controller;

import com.dudas.game.model.GemType;
import com.dudas.game.provider.PixmapGemsProvider;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by OLO on 20. 2. 2015
 */
public abstract class BaseSwapBoardTest extends BaseBoardTest {

    protected void verifyBackSwapFlow(float x1, float y1, GemType expectedGemType1, float x2, float y2, GemType expectedGemType2) {
        verifyBoardReady();
        board.swap(x1, y1, x2, y2);
        verifySwapEvent(x1, y1, expectedGemType1, x2, y2, expectedGemType2);
        verifyClearFailEvent(x1, y1, expectedGemType1, x2, y2, expectedGemType2);
        verifyBackSwapEvent(x1, y1, expectedGemType1, x2, y2, expectedGemType2);
        verifyBoardReady();
        verifyBoard(new PixmapGemsProvider(TESTBOARD_PNG));
    }

    protected void verifyBackSwapEvent(float x1, float y1, GemType expectedGemType1, float x2, float y2, GemType expectedGemType2) {
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

    protected void verifyClearFailEvent(float x1, float y1, GemType expectedGemType1, float x2, float y2, GemType expectedGemType2) {
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

}
