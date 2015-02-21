package com.dudas.game.controller;

import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by OLO on 20. 2. 2015
 */
public abstract class BaseSwapBoardTest extends BaseBoardTest {

    /**
     * Flow: START|SWAP -> CLEAR(fail) -> BACK SWAP|END
     *
     * @param x1 first gem x coordinate
     * @param y1 first gem y coordinate
     * @param x2 second gem x coordinate
     * @param y2 second gem y coordinate
     */
    protected void verifyBackSwapFlow(float x1, float y1, float x2, float y2) {
        verifyBoardReady();
        board.swap(x1, y1, x2, y2);
        verifySwapEvent(x1, y1, x2, y2);
        verifyClearFailEvent(x1, y1, x2, y2);
        verifyBackSwapEvent(x1, y1, x2, y2);
        verifyBoardReady();
        verifyBoard();
    }

    /**
     * Verification of backSwap event
     *
     * @param x1 first gem x coordinate
     * @param y1 first gem y coordinate
     * @param x2 second gem x coordinate
     * @param y2 second gem y coordinate
     */
    protected void verifyBackSwapEvent(float x1, float y1, float x2, float y2) {
        ArgumentCaptor<TwoGemsBoardEvent> backSwapEventCaptor = ArgumentCaptor.forClass(TwoGemsBoardEvent.class);
        verify(eventManager).fireBackSwap(backSwapEventCaptor.capture());
        TwoGemsBoardEvent backSwapEvent = backSwapEventCaptor.getValue();
        assertNotNull(backSwapEvent);
        assertTrue(backSwapEvent.getFromGem().getX() == x2);
        assertTrue(backSwapEvent.getFromGem().getY() == y2);
        assertTrue(backSwapEvent.getFromGem().isBlocked());
        assertEquals(expectedSwapGem2Type, backSwapEvent.getFromGem().getType());
        assertTrue(backSwapEvent.getToGem().getX() == x1);
        assertTrue(backSwapEvent.getToGem().getY() == y1);
        assertTrue(backSwapEvent.getToGem().isBlocked());
        assertEquals(expectedSwapGem1Type, backSwapEvent.getToGem().getType());
        backSwapEvent.complete();
    }

    /**
     * Verification of clearFail event
     *
     * @param x1 first gem x coordinate
     * @param y1 first gem y coordinate
     * @param x2 second gem x coordinate
     * @param y2 second gem y coordinate
     */
    protected void verifyClearFailEvent(float x1, float y1, float x2, float y2) {
        ArgumentCaptor<TwoGemsBoardEvent> clearFailEventCaptor = ArgumentCaptor.forClass(TwoGemsBoardEvent.class);
        verify(eventManager).fireClearFail(clearFailEventCaptor.capture());
        TwoGemsBoardEvent clearFailEvent = clearFailEventCaptor.getValue();
        assertNotNull(clearFailEvent);
        assertTrue(clearFailEvent.getFromGem().getX() == x1);
        assertTrue(clearFailEvent.getFromGem().getY() == y1);
        assertTrue(clearFailEvent.getFromGem().isBlocked());
        assertEquals(expectedSwapGem2Type, clearFailEvent.getFromGem().getType());
        assertTrue(clearFailEvent.getToGem().getX() == x2);
        assertTrue(clearFailEvent.getToGem().getY() == y2);
        assertTrue(clearFailEvent.getToGem().isBlocked());
        assertEquals(expectedSwapGem1Type, clearFailEvent.getToGem().getType());
        clearFailEvent.complete();
    }

}
