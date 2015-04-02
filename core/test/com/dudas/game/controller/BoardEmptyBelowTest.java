package com.dudas.game.controller;

import com.dudas.game.model.GemType;
import com.dudas.game.model.provider.DesktopPixmapGemsProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by OLO on 1. 3. 2015
 */
public class BoardEmptyBelowTest extends BaseFallBoardTest {

    public static final int BOARD_WIDTH = 3;
    public static final int BOARD_HEIGHT = 3;
    public static final String INIT_BOARD_BACKSWAP_WITH_EMPTYBELOW_3X3_PNG = "init_board_backswap_with_emptybelow_3x3.png";
    public static final String EXPECTED_BOARD_BACKSWAP_WITH_EMPTYBELOW_3X3_PNG = "expected_board_backswap_with_emptybelow_3x3.png";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        pixmapGemsProvider = new DesktopPixmapGemsProvider(INIT_BOARD_BACKSWAP_WITH_EMPTYBELOW_3X3_PNG);
        pixmapGemsProvider.mockRandomGemTypes(GemType.PURPLE, GemType.PURPLE); // TODO bellow empty finder is called twice => FIX IT
        board = new BoardController(
                BOARD_WIDTH,
                BOARD_HEIGHT,
                pixmapGemsProvider,
                eventManager
        );
    }

    @Test
    public void testBackSwapEmptyBelow() throws Exception {
        expectedSwapEvents.add(new ExpectedSwapEvent().withSwapTypes(GemType.RED, GemType.GREEN));
        expectedBackSwapEvents.add(new ExpectedSwapEvent().withSwapTypes(GemType.RED, GemType.GREEN));
        expectedFallEvents.add(new ExpectedFallEvent().withGemsSize(3));
        expectedFinalBoard = EXPECTED_BOARD_BACKSWAP_WITH_EMPTYBELOW_3X3_PNG;

        verifyBackSwapWithEmptyBellowFlow(1, 1, 2, 1);

    }
}
