package com.dudas.game.controller;

import com.dudas.game.model.Gem;
import com.dudas.game.controller.event.BoardEvent;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by OLO on 20. 2. 2015
 */
public abstract class BaseFallBoardTest extends BaseSwapBoardTest {

    /**
     * Flow: START|SWAP -> CLEAR(success) -> FALL|END
     *
     * @param x1 first gem x coordinate
     * @param y1 first gem y coordinate
     * @param x2 second gem x coordinate
     * @param y2 second gem y coordinate
     */
    protected void verify3ClearFlow(float x1, float y1, float x2, float y2) {
        verifyBoardReady();
        board.swap(x1, y1, x2, y2);
        verifySwapEvent(x1, y1, x2, y2);
        verifyClearSuccessEvent();
        verifyFallEvent();
        verifyBoardReady();
        verifyBoard();
    }

    protected void verify2x3ClearFlow(float x1, float y1, float x2, float y2) {
        verifyBoardReady();
        board.swap(x1, y1, x2, y2);
        verifySwapEvent(x1, y1, x2, y2);
        verifyClearSuccessEvent();
        verifyFallEvent();
        verifyClearSuccessEvent();
        verifyFallEvent();
        verifyBoardReady();
        verifyBoard();
    }

    protected void verifyBackSwapWithEmptyBellowFlow(float x1, float y1, float x2, float y2) {
//        verifyBoardReady();
        board.swap(x1, y1, x2, y2);
        verifySwapEvent(x1, y1, x2, y2);
        verifyClearFailEvent(x1, y1, x2, y2);
        verifyBackSwapEvent(x1, y1, x2, y2);
        verifyFallEvent();
        verifyBoardReady();
        verifyBoard();
    }

    protected void verifyParallel2x3ClearFlow(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        verifyBoardReady();
        board.swap(x1, y1, x2, y2);
        verifySwapEvent(x1, y1, x2, y2);
        verifyClearSuccessEvent();

        board.swap(x3, y3, x4, y4);
        verifySwapEvent(x3, y3, x4, y4);
        verifyClearSuccessEvent();

        verifyFallEvent();
        verifyFallEvent();
    }


    private void verifyFallEvent() {
        ArgumentCaptor<BoardEvent> fallEventCaptor = ArgumentCaptor.forClass(BoardEvent.class);
        verify(eventManager, atLeastOnce()).fireFall(fallEventCaptor.capture());
        BoardEvent fallEvent = fallEventCaptor.getValue();

        assertNotNull(fallEvent);

        Gem[] gems = fallEvent.getGems();

        ExpectedFallEvent expectedFallEvent = expectedFallEvents.get(0);
        assertTrue(expectedFallEvent.gemsSize == gems.length);

        verifyBlockedGems(gems);

        fallEvent.complete();
        expectedFallEvent.discard();
    }

    private void verifyClearSuccessEvent() {
        ArgumentCaptor<BoardEvent> clearSuccessEventCaptor = ArgumentCaptor.forClass(BoardEvent.class);
        verify(eventManager).fireClearSuccess(clearSuccessEventCaptor.capture());
        BoardEvent clearSuccessEvent = clearSuccessEventCaptor.getValue();
        assertNotNull(clearSuccessEvent);
        Gem[] gems = clearSuccessEvent.getGems();
        ExpectedClearSuccessEvent expectedClearSuccessEvent = expectedClearSuccessEvents.get(0);
        for (ExpectedClearSuccessEvent.LengthWithType lengthWithType : expectedClearSuccessEvent.lengthWithTypeList) {
            int length = 0;
            for (Gem gem : gems) {
                if (lengthWithType.expectedType.equals(gem.getType())) {
                    length++;
                }
            }
            assertTrue(lengthWithType.expectedLength == length);
        }
        reset(eventManager);
        clearSuccessEvent.complete();
        expectedClearSuccessEvent.discard();
    }

}
