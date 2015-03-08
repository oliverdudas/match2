package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.controller.event.EventManager;
import com.dudas.game.controller.event.TwoGemsBoardEvent;
import com.dudas.game.model.Gem;
import com.dudas.game.model.GemType;
import com.dudas.game.model.provider.PixmapGemsProvider;
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
    protected PixmapGemsProvider pixmapGemsProvider;

    protected Array<ExpectedSwapEvent> expectedSwapEvents;
    protected Array<ExpectedSwapEvent> expectedBackSwapEvents;
    protected Array<ExpectedFallEvent> expectedFallEvents;
    protected Array<ExpectedClearSuccessEvent> expectedClearSuccessEvents;
    protected String expectedFinalBoard;

    @Before
    public void setUp() throws Exception {
        pixmapGemsProvider = new PixmapGemsProvider(TESTBOARD_PNG);
        expectedSwapEvents = new Array<ExpectedSwapEvent>();
        expectedBackSwapEvents = new Array<ExpectedSwapEvent>();
        expectedFallEvents = new Array<ExpectedFallEvent>();
        expectedClearSuccessEvents = new Array<ExpectedClearSuccessEvent>();
        expectedFinalBoard = TESTBOARD_PNG;
    }

    protected void verifySwapEvent(float x1, float y1, float x2, float y2) {
        ArgumentCaptor<TwoGemsBoardEvent> swapEventCaptor = ArgumentCaptor.forClass(TwoGemsBoardEvent.class);
        verify(eventManager).fireSwap(swapEventCaptor.capture());
        TwoGemsBoardEvent swapEvent = swapEventCaptor.getValue();

        ExpectedSwapEvent expectedSwapEvent = expectedSwapEvents.get(0);

        assertNotNull(swapEvent);
        assertTrue(swapEvent.getFromGem().getX() == x1);
        assertTrue(swapEvent.getFromGem().getY() == y1);
        assertTrue(swapEvent.getFromGem().isBlocked());
        assertEquals(expectedSwapEvent.type2, swapEvent.getFromGem().getType());
        assertTrue(swapEvent.getToGem().getX() == x2);
        assertTrue(swapEvent.getToGem().getY() == y2);
        assertTrue(swapEvent.getToGem().isBlocked());
        assertEquals(expectedSwapEvent.type1, swapEvent.getToGem().getType());

        swapEvent.complete();
        expectedSwapEvent.discard();
    }

    protected void verifyBoard() {
        Array<Gem> expectedGems = new PixmapGemsProvider(expectedFinalBoard).getGems(board.getWidth(), board.getHeight());
        for (Gem gem : board.getGems()) {
            int boardIndex = coordinatesToIndex(gem.getX(), gem.getY());
            Gem expectedGem = expectedGems.get(boardIndex);
            assertTrue(expectedGem.getIndex(board.getHeight()) == gem.getIndex(board.getHeight()));
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

    protected class ExpectedSwapEvent {
        public GemType type1;
        public GemType type2;

        public ExpectedSwapEvent() {
            this.type1 = GemType.EMPTY;
            this.type2 = GemType.EMPTY;
        }

        public ExpectedSwapEvent withSwapTypes(GemType type1, GemType type2) {
            this.type1 = type1;
            this.type2 = type2;
            return this;
        }

        public void discard() {
            expectedSwapEvents.removeValue(this, false);
        }
    }

    protected class ExpectedFallEvent {
        public int gemsSize;

        public ExpectedFallEvent() {
            gemsSize = 0;
        }

        public ExpectedFallEvent withGemsSize(int gemsSize) {
            this.gemsSize = gemsSize;
            return this;
        }

        public void discard() {
            expectedFallEvents.removeValue(this, false);
        }
    }

    protected class ExpectedClearSuccessEvent {
        public List<LengthWithType> lengthWithTypeList;

        public ExpectedClearSuccessEvent() {
            lengthWithTypeList = new ArrayList<LengthWithType>();
        }

        public ExpectedClearSuccessEvent addLengthWithType(int length, GemType type) {
            lengthWithTypeList.add(new LengthWithType(length, type));
            return this;
        }

        protected class LengthWithType {
            public int expectedLength;
            public GemType expectedType;

            public LengthWithType(int length, GemType type) {
                this.expectedLength = length;
                this.expectedType = type;
            }
        }

        public void discard() {
            expectedClearSuccessEvents.removeValue(this, false);
        }
    }
}
