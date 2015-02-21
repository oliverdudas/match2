package com.dudas.game.controller;

import com.dudas.game.Gem;
import com.dudas.game.model.GemType;
import com.dudas.game.provider.PixmapGemsProvider;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * Created by OLO on 20. 2. 2015
 */
public abstract class BaseFallBoardTest extends BaseSwapBoardTest {

    public static final String EXPECTED_BOARD_AFTER_FALL_FLOW_PNG = "expected_board_after_fall_flow.png";

    protected void verifyFallFlow(float x1, float y1, GemType expectedGemType1, float x2, float y2, GemType expectedGemType2) {
        verifyBoardReady();
        board.swap(x1, y1, x2, y2);
        verifySwapEvent(x1, y1, expectedGemType1, x2, y2, expectedGemType2);

        verifyClearSuccessEvent();

        verifyFallEvent();

        verifyBoardReady();

        verifyBoard(new PixmapGemsProvider(EXPECTED_BOARD_AFTER_FALL_FLOW_PNG));
    }

    private void verifyFallEvent() {
        ArgumentCaptor<BoardEvent> fallEventCaptor = ArgumentCaptor.forClass(BoardEvent.class);
        verify(eventManager).fireFall(fallEventCaptor.capture());
        BoardEvent fallEvent = fallEventCaptor.getValue();

        assertNotNull(fallEvent);

        Gem[] gems = fallEvent.getGems();
        assertTrue(6 == gems.length);
        verifyBlockedGems(gems);

        fallEvent.complete();
    }

    private void verifyClearSuccessEvent() {
        ArgumentCaptor<BoardEvent> clearSuccessEventCaptor = ArgumentCaptor.forClass(BoardEvent.class);
        verify(eventManager).fireClearSuccess(clearSuccessEventCaptor.capture());
        BoardEvent clearSuccessEvent = clearSuccessEventCaptor.getValue();

        assertNotNull(clearSuccessEvent);

        Gem[] gems = clearSuccessEvent.getGems();
        Gem gem1 = gems[0];
        Gem gem2 = gems[1];
        Gem gem3 = gems[2];

        assertTrue(gem1.getX() == 3);
        assertTrue(gem1.getY() == 7);
        assertTrue(gem1.isBlocked());
        assertEquals(GemType.ORANGE, gem1.getType());

        assertTrue(gem2.getX() == 5);
        assertTrue(gem2.getY() == 7);
        assertTrue(gem2.isBlocked());
        assertEquals(GemType.ORANGE, gem2.getType());

        assertTrue(gem3.getX() == 4);
        assertTrue(gem3.getY() == 7);
        assertTrue(gem3.isBlocked());
        assertEquals(GemType.ORANGE, gem3.getType());

        clearSuccessEvent.complete();
    }

}
