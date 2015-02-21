package com.dudas.game.controller;

import com.dudas.game.Constants;
import com.dudas.game.model.GemType;
import com.dudas.game.provider.PixmapGemsProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by OLO on 20. 2. 2015
 */
public class BoardSuccessSwapTest extends BaseFallBoardTest {

    public static final String EXPECTED_BOARD_AFTER_HORIZONTAL_SIMPLE_FALL_FLOW_PNG = "expected_board_after_horizontal_simple_fall_flow.png";
    public static final String EXPECTED_BOARD_AFTER_VERTICAL_SIMPLE_FALL_FLOW_PNG = "expected_board_after_vertical_simple_fall_flow.png";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        board = new BoardController(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        PixmapGemsProvider gemsProvider = new PixmapGemsProvider(TESTBOARD_PNG);
        gemsProvider.addGemTypeToStack(GemType.RED, GemType.YELLOW, GemType.GREEN);
        board.setGemsProvider(gemsProvider);
        board.setEventManager(eventManager);
    }

    @Test
    public void testHorizontal3ClearFlow() throws Exception {
        expectedSwapGem1Type = GemType.ORANGE;
        expectedSwapGem2Type = GemType.PURPLE;
        expectedFallEvent.gemsSize = 6;
        expectedClearSuccessEvent.addLengthWithType(3, GemType.ORANGE);
        expectedFinalBoard = EXPECTED_BOARD_AFTER_HORIZONTAL_SIMPLE_FALL_FLOW_PNG;

        verify3ClearFlow(3, 8, 3, 7);
    }

    @Test
    public void testVertical3ClearFlow() throws Exception {
        expectedSwapGem1Type = GemType.PURPLE;
        expectedSwapGem2Type = GemType.BLUE;
        expectedFallEvent.gemsSize = 9;
        expectedClearSuccessEvent.addLengthWithType(3, GemType.PURPLE);
        expectedFinalBoard = EXPECTED_BOARD_AFTER_VERTICAL_SIMPLE_FALL_FLOW_PNG;

        verify3ClearFlow(8, 1, 7, 1);
    }
}
