package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Constants;
import com.dudas.game.EventManager;
import com.dudas.game.Gem;
import com.dudas.game.model.GemModel;
import com.dudas.game.model.GemType;
import com.dudas.game.provider.TestGemsProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by foxy on 07/02/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class BoardControllerSwapTest {

    @Mock
    private EventManager eventManager;

    private BoardController board;

    @Before
    public void setUp() throws Exception {
        board = new BoardController(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        board.setGemsProvider(new TestGemsProvider());
        board.setEventManager(eventManager);
    }

    @Test
    public void testVerticalNeighborSwap() throws Exception {
        Gem fromGem = board.getGems().get(0); // [0, 0, BLUE]
        assertEquals(GemType.BLUE, fromGem.getType());
        Gem toGem = board.getGems().get(1); // [0, 1, RED]
        assertEquals(GemType.RED, toGem.getType());

        ArgumentCaptor<Gem> fromGemCaptor = ArgumentCaptor.forClass(Gem.class);
        ArgumentCaptor<Gem> toGemCaptor = ArgumentCaptor.forClass(Gem.class);

        board.swap(fromGem.getX(), fromGem.getY(), toGem.getX(), toGem.getY());

        verify(eventManager).fireSwap(toGemCaptor.capture(), fromGemCaptor.capture());

        Gem capturedFromGem = fromGemCaptor.getValue();
        Gem capturedToGem = toGemCaptor.getValue();

        assertEquals(GemType.RED, capturedFromGem.getType());
        assertTrue(0 == capturedFromGem.getX());
        assertTrue(0 == capturedFromGem.getY());

        assertEquals(GemType.BLUE, capturedToGem.getType());
        assertTrue(0 == capturedToGem.getX());
        assertTrue(1 == capturedToGem.getY());
    }

    @Test
    public void testHorizontalNeighborSwap() throws Exception {
        Gem fromGem = board.getGems().get(2); // [0, 2, GREEN]
        assertEquals(GemType.GREEN, fromGem.getType());
        Gem toGem = board.getGems().get(11); // [1, 2, BLUE]
        assertEquals(GemType.BLUE, toGem.getType());

        ArgumentCaptor<Gem> fromGemCaptor = ArgumentCaptor.forClass(Gem.class);
        ArgumentCaptor<Gem> toGemCaptor = ArgumentCaptor.forClass(Gem.class);

        board.swap(fromGem.getX(), fromGem.getY(), toGem.getX(), toGem.getY());

        verify(eventManager).fireSwap(toGemCaptor.capture(), fromGemCaptor.capture());

        Gem capturedFromGem = fromGemCaptor.getValue();
        Gem capturedToGem = toGemCaptor.getValue();

        assertEquals(GemType.BLUE, capturedFromGem.getType());
        assertTrue(0 == capturedFromGem.getX());
        assertTrue(2 == capturedFromGem.getY());

        assertEquals(GemType.GREEN, capturedToGem.getType());
        assertTrue(1 == capturedToGem.getX());
        assertTrue(2 == capturedToGem.getY());
    }

    @Test(expected = RuntimeException.class)
    public void testDiagonalNeighborSwap() throws Exception {
        Gem fromGem = board.getGems().get(0); // [0, 0, BLUE]
        assertEquals(GemType.BLUE, fromGem.getType());
        Gem toGem = board.getGems().get(10); // [1, 1, RED]
        assertEquals(GemType.RED, toGem.getType());

        board.swap(fromGem.getX(), fromGem.getY(), toGem.getX(), toGem.getY());
    }

    //        long start = System.currentTimeMillis();
//
//        // Suppress ctor *before* createPartialMock and static mock, or ctor is run.
//        suppress(constructor(MatchGameEventManager.class));
//
//        // Could also do mockStaticPartial(LongRunningCtor.class, "instance").
//        mockStatic(MatchGameEventManager.class);
//
//        // Could also do createPartialMock(LongRunningCtor.class, "fetch").
//        MatchGameEventManager mock = createMock(MatchGameEventManager.class);
//
//        expect(MatchGameEventManager.get()).andReturn(mock);
////        expect(mock.fetch()).andReturn("Processed data");
////        verify(mock).fireSwap(4, 3, 4, 4);
//
//        replay(mock);
////
//        board.swap(4, 3, 4, 4);
////        UsesLongRunningCtor underTest = new UsesLongRunningCtor();
////        assertEquals("Processed data", underTest.transformData());
////        long end = System.currentTimeMillis();
////
//        verify(mock);

//        MatchGameEventManager mockHelper = Whitebox.invokeConstructor(MatchGameEventManager.class);
//        Whitebox.setInternalState(mockHelper, "minstance", MatchGameEventManager.get());
//        PowerMockito.mockStatic(MatchGameEventManager.class);
//        Mockito.when(MatchGameEventManager.get()).thenReturn(mockHelper);

        // mock all the static methods in a class called "Static"
//        PowerMockito.mockStatic(MatchGameEventManager.class);
//        PowerMockito.when(MatchGameEventManager.get()).thenReturn(MatchGameEventManager.get());
//        MatchGameEventManager mock = Mockito.mock(MatchGameEventManager.class);
//        Mockito.doNothing().when(mock).fireSwap(4, 3, 4, 4);
//        board.swap(4, 3, 4, 4);
//
//        PowerMockito.verifyStatic(Mockito.times(1));
//        Mockito.verify(mock).fireSwap(4, 3, 4, 4);

//        // use Mockito to set up your expectation
//        MatchGameEventManager.get().fireSwap(4, 3, 4, 4);
//        PowerMockito.verifyStatic(Mockito.times(1));
//
//        Mockito.when(Static.secondStaticMethod()).thenReturn(123);
//
//        // execute your test
//        classCallStaticMethodObj.execute();
//
//        // Different from Mockito, always use PowerMockito.verifyStatic() first
//        PowerMockito.verifyStatic(Mockito.times(2));
//        // Use EasyMock-like verification semantic per static method invocation
//        Static.firstStaticMethod(param);
//
//        // Remember to call verifyStatic() again
//        PowerMockito.verifyStatic(); // default times is once
//        Static.secondStaticMethod();
//
//        // Again, remember to call verifyStatic()
//        PowerMockito.verifyStatic(Mockito.never());
//        Static.thirdStaticMethod();
//
//
//        Mockito.verify(mock).

//    }

    //    @Test
//    public void testSwap() throws Exception {
//        Gem firstGem = board.getGems().get(0);
//        Gem secondGem = board.getGems().get(1);
//        Gem thirdGem = board.getGems().get(2);
//
//        assertEquals(GemType.BLUE, firstGem.getType());
//        assertTrue(0f == firstGem.getX());
//        assertTrue(0f == firstGem.getY());
//
//        assertEquals(GemType.RED, secondGem.getType());
//        assertTrue(0f == secondGem.getX());
//        assertTrue(1f == secondGem.getY());
//
//        assertEquals(GemType.GREEN, thirdGem.getType());
//        assertTrue(0f == thirdGem.getX());
//        assertTrue(2f == thirdGem.getY());
//
//
//        board.swap(0f, 0f, 0f, 1f);
//
//        Gem newFirstGem = board.getGems().get(0);
//        assertEquals(GemType.RED, newFirstGem.getType());
//        assertTrue(0f == newFirstGem.getX());
//        assertTrue(0f == newFirstGem.getY());
//
//        Gem newSecondGem = board.getGems().get(1);
//        assertEquals(GemType.BLUE, newSecondGem.getType());
//        assertTrue(0f == newSecondGem.getX());
//        assertTrue(1f == newSecondGem.getY());
//
//        board.swap(0f, 1f, 0f, 2f);
//
//        newSecondGem = board.getGems().get(1);
//        assertEquals(GemType.GREEN, newSecondGem.getType());
//        assertTrue(0f == newSecondGem.getX());
//        assertTrue(1f == newSecondGem.getY());
//
//        Gem newThirdGem = board.getGems().get(2);
//        assertEquals(GemType.BLUE, newThirdGem.getType());
//        assertTrue(0f == newThirdGem.getX());
//        assertTrue(2f == newThirdGem.getY());
//
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void testDiagonalSwap() throws Exception {
//        Gem firstGem = board.getGems().get(0);
//        Gem secondGem = board.getGems().get(10);
//
//        assertEquals(GemType.BLUE, firstGem.getType());
//        assertTrue(0f == firstGem.getX());
//        assertTrue(0f == firstGem.getY());
//
//        assertEquals(GemType.RED, secondGem.getType());
//        assertTrue(1f == secondGem.getX());
//        assertTrue(1f == secondGem.getY());
//
//        board.swap(0f, 0f, 1f, 1f);
//    }

}
