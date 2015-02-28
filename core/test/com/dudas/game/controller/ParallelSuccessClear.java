package com.dudas.game.controller;

import com.dudas.game.Constants;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by OLO on 22. 2. 2015
 */
public class ParallelSuccessClear extends BaseFallBoardTest {

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
    public void testParalell2x3HorizontalClearFlow() throws Exception {


    }

    @Test
    public void testUnclearedGemFall() throws Exception {
        // TODO
    }
}
