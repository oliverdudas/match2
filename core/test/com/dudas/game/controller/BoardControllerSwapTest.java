package com.dudas.game.controller;

import com.dudas.game.Constants;
import com.dudas.game.controller.BoardController;
import com.dudas.game.event.matchgame.MatchGameEventManager;
import com.dudas.game.provider.TestGemsProvider;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.powermock.api.mockito.PowerMockito.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import static org.easymock.EasyMock.*;
import static org.powermock.api.support.membermodification.MemberMatcher.constructor;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;


/**
 * Created by foxy on 07/02/2015.
 */
@RunWith(MockitoJUnitRunner.class)
//@RunWith(PowerMockRunner.class)
@PrepareForTest(MatchGameEventManager.class)
public class BoardControllerSwapTest {

    private BoardController boardController;

    @Before
    public void setUp() throws Exception {
        boardController = new BoardController(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        boardController.setGemsProvider(new TestGemsProvider());
    }

    @Test
    public void testVerticalNeighborSwap() throws Exception {

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
//        boardController.swap(4, 3, 4, 4);
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
//        boardController.swap(4, 3, 4, 4);
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

    }

    //    @Test
//    public void testSwap() throws Exception {
//        Gem firstGem = boardController.getGems().get(0);
//        Gem secondGem = boardController.getGems().get(1);
//        Gem thirdGem = boardController.getGems().get(2);
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
//        boardController.swap(0f, 0f, 0f, 1f);
//
//        Gem newFirstGem = boardController.getGems().get(0);
//        assertEquals(GemType.RED, newFirstGem.getType());
//        assertTrue(0f == newFirstGem.getX());
//        assertTrue(0f == newFirstGem.getY());
//
//        Gem newSecondGem = boardController.getGems().get(1);
//        assertEquals(GemType.BLUE, newSecondGem.getType());
//        assertTrue(0f == newSecondGem.getX());
//        assertTrue(1f == newSecondGem.getY());
//
//        boardController.swap(0f, 1f, 0f, 2f);
//
//        newSecondGem = boardController.getGems().get(1);
//        assertEquals(GemType.GREEN, newSecondGem.getType());
//        assertTrue(0f == newSecondGem.getX());
//        assertTrue(1f == newSecondGem.getY());
//
//        Gem newThirdGem = boardController.getGems().get(2);
//        assertEquals(GemType.BLUE, newThirdGem.getType());
//        assertTrue(0f == newThirdGem.getX());
//        assertTrue(2f == newThirdGem.getY());
//
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void testDiagonalSwap() throws Exception {
//        Gem firstGem = boardController.getGems().get(0);
//        Gem secondGem = boardController.getGems().get(10);
//
//        assertEquals(GemType.BLUE, firstGem.getType());
//        assertTrue(0f == firstGem.getX());
//        assertTrue(0f == firstGem.getY());
//
//        assertEquals(GemType.RED, secondGem.getType());
//        assertTrue(1f == secondGem.getX());
//        assertTrue(1f == secondGem.getY());
//
//        boardController.swap(0f, 0f, 1f, 1f);
//    }

}
