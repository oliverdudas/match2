package com.dudas.game.controller;

import com.dudas.game.Constants;
import com.dudas.game.model.GemType;
import com.dudas.game.model.provider.PixmapGemsProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by OLO on 20. 2. 2015
 */
public class BoardSuccessSwapTest extends BaseFallBoardTest {

    public static final String EXPECTED_BOARD_AFTER_HORIZONTAL_SIMPLE_FALL_FLOW_PNG = "expected_board_after_horizontal_simple_fall_flow.png";
    public static final String EXPECTED_BOARD_AFTER_VERTICAL_SIMPLE_FALL_FLOW_PNG = "expected_board_after_vertical_simple_fall_flow.png";
    public static final String EXPECTED_BOARD_AFTER_2X3CLEAR_IN_SEQUENZE_FLOW_PNG = "expected_board_after_2x3clear_in_sequenze_flow.png";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        board = new BoardController(
                Constants.BOARD_WIDTH,
                Constants.BOARD_HEIGHT,
                pixmapGemsProvider,
                eventManager
        );
    }

    @Test
    public void testHorizontal3ClearFlow() throws Exception {
        pixmapGemsProvider.mockRandomGemTypes(GemType.RED, GemType.GREEN, GemType.YELLOW);
        expectedSwapGem1Type = GemType.ORANGE;
        expectedSwapGem2Type = GemType.PURPLE;
        expectedClearSuccessEvents.add(new ExpectedClearSuccessEvent().addLengthWithType(3, GemType.ORANGE));
        expectedFallEvents.add(new ExpectedFallEvent().withGemsSize(6));
        expectedFinalBoard = EXPECTED_BOARD_AFTER_HORIZONTAL_SIMPLE_FALL_FLOW_PNG;

        verify3ClearFlow(3, 8, 3, 7);
    }

    @Test
    public void testVertical3ClearFlow() throws Exception {
        pixmapGemsProvider.mockRandomGemTypes(GemType.RED, GemType.YELLOW, GemType.GREEN);
        expectedSwapGem1Type = GemType.PURPLE;
        expectedSwapGem2Type = GemType.BLUE;
        expectedClearSuccessEvents.add(new ExpectedClearSuccessEvent().addLengthWithType(3, GemType.PURPLE));
        expectedFallEvents.add(new ExpectedFallEvent().withGemsSize(9));
        expectedFinalBoard = EXPECTED_BOARD_AFTER_VERTICAL_SIMPLE_FALL_FLOW_PNG;

        verify3ClearFlow(8, 1, 7, 1);
    }

    @Test
    public void test2x3ClearInSequenzeFlow() throws Exception {
        pixmapGemsProvider.mockRandomGemTypes(
                GemType.RED, GemType.RED, GemType.PURPLE,
                GemType.YELLOW, GemType.BLUE, GemType.GREEN
        );
        expectedSwapGem1Type = GemType.ORANGE;
        expectedSwapGem2Type = GemType.GREEN;
        expectedClearSuccessEvents.add(new ExpectedClearSuccessEvent().addLengthWithType(3, GemType.ORANGE));
        expectedFallEvents.add(new ExpectedFallEvent().withGemsSize(3));
        expectedClearSuccessEvents.add(new ExpectedClearSuccessEvent().addLengthWithType(3, GemType.RED));
        expectedFallEvents.add(new ExpectedFallEvent().withGemsSize(4));
        expectedFinalBoard = EXPECTED_BOARD_AFTER_2X3CLEAR_IN_SEQUENZE_FLOW_PNG;

        verify2x3ClearFlow(7, 7, 8, 7);
    }

}
