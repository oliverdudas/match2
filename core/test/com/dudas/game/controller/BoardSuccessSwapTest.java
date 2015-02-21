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

    @Before
    public void setUp() throws Exception {
        board = new BoardController(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        PixmapGemsProvider gemsProvider = new PixmapGemsProvider(TESTBOARD_PNG);
        gemsProvider.addGemTypeToStack(GemType.RED, GemType.YELLOW, GemType.GREEN);
        board.setGemsProvider(gemsProvider);
        board.setEventManager(eventManager);
    }

    @Test
    public void testSimpleFallFlow() throws Exception {
        verifyFallFlow(3, 8, GemType.ORANGE, 3, 7, GemType.PURPLE);
    }
}
