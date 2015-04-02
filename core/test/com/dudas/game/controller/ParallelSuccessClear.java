package com.dudas.game.controller;

import com.dudas.game.model.provider.DesktopPixmapGemsProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by OLO on 22. 2. 2015
 */
public class ParallelSuccessClear extends BaseFallBoardTest {

    protected static final String TESTBOARD_2X6_PNG = "init_board_2x6.png";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        board = new BoardController(
                2,
                6,
                new DesktopPixmapGemsProvider(TESTBOARD_2X6_PNG),
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
