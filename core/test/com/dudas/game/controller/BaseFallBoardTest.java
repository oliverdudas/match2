package com.dudas.game.controller;

import com.dudas.game.Gem;
import com.dudas.game.model.GemType;
import com.dudas.game.provider.PixmapGemsProvider;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * Created by OLO on 20. 2. 2015
 */
public abstract class BaseFallBoardTest extends BaseSwapBoardTest {

    /**
     * Flow: START|SWAP -> CLEAR(success) -> FALL|END
     *
     * @param x1               first gem x coordinate
     * @param y1               first gem y coordinate
     * @param x2               second gem x coordinate
     * @param y2               second gem y coordinate
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

    private void verifyFallEvent() {
        ArgumentCaptor<BoardEvent> fallEventCaptor = ArgumentCaptor.forClass(BoardEvent.class);
        verify(eventManager).fireFall(fallEventCaptor.capture());
        BoardEvent fallEvent = fallEventCaptor.getValue();

        assertNotNull(fallEvent);

        Gem[] gems = fallEvent.getGems();
        assertTrue(expectedFallEvent.gemsSize == gems.length);
        verifyBlockedGems(gems);

        fallEvent.complete();
    }

    private void verifyClearSuccessEvent() {
        ArgumentCaptor<BoardEvent> clearSuccessEventCaptor = ArgumentCaptor.forClass(BoardEvent.class);
        verify(eventManager).fireClearSuccess(clearSuccessEventCaptor.capture());
        BoardEvent clearSuccessEvent = clearSuccessEventCaptor.getValue();
        assertNotNull(clearSuccessEvent);
        Gem[] gems = clearSuccessEvent.getGems();
        for (ExpectedClearSuccessEvent.LengthWithType lengthWithType : expectedClearSuccessEvent.lengthWithTypeList) {
            int length = 0;
            for (Gem gem : gems) {
                if (lengthWithType.expectedType.equals(gem.getType())) {
                    length++;
                }
            }
            assertTrue(lengthWithType.expectedLength == length);
        }
        clearSuccessEvent.complete();
    }

}
