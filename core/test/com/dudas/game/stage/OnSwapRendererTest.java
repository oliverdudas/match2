package com.dudas.game.stage;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.dudas.game.Gem;
import com.dudas.game.controller.BoardController;
import com.dudas.game.model.GemModel;
import com.dudas.game.model.GemType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import static org.mockito.Mockito.*;

/**
 * Created by OLO on 15. 2. 2015
 */
@RunWith(MockitoJUnitRunner.class)
//@PrepareForTest(BoardController.class)
public class OnSwapRendererTest {

    @Mock
    private BoardController board;

    private BoardEventRenderer renderer;

    @Before
    public void setUp() {
    }

    @Test
    public void testOnSwap() throws Exception {
//        Array<Gem> gems = new Array<Gem>();
//        GemModel blueGem = new GemModel(GemType.BLUE, 0, 0);
//        gems.add(blueGem);
//        GemModel redGem = new GemModel(GemType.RED, 0, 1);
//        gems.add(redGem);
//        when(board.getGems()).thenReturn(gems);
//
//        renderer = new BoardEventRenderer(board, new Group());
//        renderer.onSwap(blueGem, redGem);
//
//        Mockito.verify(board).clear(blueGem.getX(), blueGem.getY(), redGem.getX(), redGem.getY());

    }
}
