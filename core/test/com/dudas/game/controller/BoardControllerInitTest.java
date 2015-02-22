package com.dudas.game.controller;

import com.dudas.game.Constants;
import com.dudas.game.model.provider.TestGemsProvider;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by foxy on 07/02/2015.
 */
public class BoardControllerInitTest {

    private BoardController boardController;

    @Before
    public void setUp() throws Exception {
        boardController = new BoardController(
                Constants.BOARD_WIDTH,
                Constants.BOARD_HEIGHT,
                new TestGemsProvider()
        );
    }

    @Test
    public void testGemsSize() throws Exception {
        assertTrue(81 == boardController.getGems().size);
    }

    @Test
    public void testFindGemByIndex() throws Exception {
        assertNotNull(boardController.getGems().get(15));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testFindGemByIndexOutOfBound() throws Exception {
        boardController.getGems().get(85);
    }
}
