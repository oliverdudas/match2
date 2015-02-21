package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Board;
import com.dudas.game.EventManager;
import com.dudas.game.Gem;
import com.dudas.game.model.GemType;
import com.dudas.game.provider.GemsProvider;
import com.dudas.game.provider.PixmapGemsProvider;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by OLO on 20. 2. 2015
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class BaseBoardTest {

    protected static final String TESTBOARD_PNG = "init_board.png";

    @Mock
    protected EventManager eventManager;
    protected Board board;

    protected ExpectedFallEvent expectedFallEvent;
    protected ExpectedClearSuccessEvent expectedClearSuccessEvent;
    protected String expectedFinalBoard;
    protected GemType expectedSwapGem1Type;
    protected GemType expectedSwapGem2Type;

    @Before
    public void setUp() throws Exception {
        expectedFallEvent = new ExpectedFallEvent();
        expectedClearSuccessEvent = new ExpectedClearSuccessEvent();
        expectedFinalBoard = TESTBOARD_PNG;
        expectedSwapGem1Type = GemType.EMPTY;
        expectedSwapGem2Type = GemType.EMPTY;
    }

    protected void verifySwapEvent(float x1, float y1, float x2, float y2) {
        ArgumentCaptor<TwoGemsBoardEvent> swapEventCaptor = ArgumentCaptor.forClass(TwoGemsBoardEvent.class);
        verify(eventManager).fireSwap(swapEventCaptor.capture());
        TwoGemsBoardEvent swapEvent = swapEventCaptor.getValue();

        assertNotNull(swapEvent);
        assertTrue(swapEvent.getFromGem().getX() == x1);
        assertTrue(swapEvent.getFromGem().getY() == y1);
        assertTrue(swapEvent.getFromGem().isBlocked());
        assertEquals(expectedSwapGem2Type, swapEvent.getFromGem().getType());
        assertTrue(swapEvent.getToGem().getX() == x2);
        assertTrue(swapEvent.getToGem().getY() == y2);
        assertTrue(swapEvent.getToGem().isBlocked());
        assertEquals(expectedSwapGem1Type, swapEvent.getToGem().getType());
        swapEvent.complete();
    }

    protected void verifyBoard() {
        Array<Gem> expectedGems = new PixmapGemsProvider(expectedFinalBoard).getGems(board.getWidth(), board.getHeight());
        for (Gem gem : board.getGems()) {
            int boardIndex = coordinatesToIndex(gem.getX(), gem.getY());
            Gem expectedGem = expectedGems.get(boardIndex);
            assertTrue(expectedGem.getIndex() == gem.getIndex());
            assertEquals(expectedGem.getType(), gem.getType());
            assertEquals(expectedGem.getState(), gem.getState());
        }
    }

    protected void verifyBoardReady() {
        for (Gem gem : board.getGems()) {
            assertTrue(gem.isReady());
        }
    }

    protected void verifyBlockedGems(Gem... gems) {
        for (Gem gem : gems) {
            assertTrue(gem.isBlocked());
        }
    }

    protected int coordinatesToIndex(float x, float y) {
        return (int) (x * board.getWidth() + y);
    }

    protected class ExpectedFallEvent {
        public int gemsSize;

        public ExpectedFallEvent() {
            gemsSize = 0;
        }
    }

    protected class ExpectedClearSuccessEvent {
        public List<LengthWithType> lengthWithTypeList;

        public ExpectedClearSuccessEvent() {
            lengthWithTypeList = new ArrayList<LengthWithType>();
        }

        public void addLengthWithType(int length, GemType type) {
            lengthWithTypeList.add(new LengthWithType(length, type));
        }

        protected class LengthWithType {
            public int expectedLength;
            public GemType expectedType;

            public LengthWithType(int length, GemType type) {
                this.expectedLength = length;
                this.expectedType = type;
            }
        }
    }
}
