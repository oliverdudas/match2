package com.dudas.game.finder;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.controller.finder.FallFinder;
import com.dudas.game.controller.finder.GemFinder;
import com.dudas.game.controller.helper.BoardHelper;
import com.dudas.game.model.Gem;
import com.dudas.game.model.GemFactory;
import com.dudas.game.model.GemState;
import com.dudas.game.model.GemType;
import com.dudas.game.model.provider.GemsProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by OLO on 1. 4. 2015
 */
@RunWith(MockitoJUnitRunner.class)
public class FallFinderTest {

    private GemFinder finder;
    @Mock
    private Array<Gem> gems;
    @Mock
    private GemsProvider gemsProvider;
    @Mock
    private BoardHelper helper;

    @Before
    public void setUp() {
        finder = new FallFinder(gems, helper, gemsProvider);
    }

    @Test
    public void testFindInSequence() {

        Gem[] clearedGems = new Gem[4];
        clearedGems[0] = GemFactory.getGem(GemType.GREEN, GemState.BLOCKED, 0, 3, 69, true);
        clearedGems[1] = GemFactory.getGem(GemType.GREEN, GemState.BLOCKED, 0, 2, 76, true);
        clearedGems[2] = GemFactory.getGem(GemType.GREEN, GemState.BLOCKED, 0, 1, 68, true);
        clearedGems[3] = GemFactory.getGem(GemType.GREEN, GemState.BLOCKED, 0, 0, 66, true);

        Gem restrictedGem = GemFactory.getGem(GemType.RED, GemState.BLOCKED, 0, 4, 79, true);

        when(helper.isValidIndex(0)).thenReturn(true);
        when(helper.isValidIndex(1)).thenReturn(true);
        when(helper.isValidIndex(2)).thenReturn(true);
        when(helper.isValidIndex(3)).thenReturn(true);
        when(helper.isValidIndex(4)).thenReturn(true);

        when(helper.getAboveNeighborIndex(0)).thenReturn(1);
        when(helper.getAboveNeighborIndex(1)).thenReturn(2);
        when(helper.getAboveNeighborIndex(2)).thenReturn(3);
        when(helper.getAboveNeighborIndex(3)).thenReturn(4);

        when(helper.findGem(0, gems)).thenReturn(clearedGems[0]);
        when(helper.findGem(1, gems)).thenReturn(clearedGems[1]);
        when(helper.findGem(2, gems)).thenReturn(clearedGems[2]);
        when(helper.findGem(3, gems)).thenReturn(clearedGems[3]);
        when(helper.findGem(4, gems)).thenReturn(restrictedGem);

        Gem[] resultGems = finder.find(clearedGems, createBellowEmptyGems());

        assertEquals("Length of gems which should fall", 4, resultGems.length);
    }

//    []
    private Gem[] createBellowEmptyGems() {
        return new Gem[0];
    }

    //    [69,76,68,66,]
}
