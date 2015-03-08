package com.dudas.game.performance;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.model.Gem;
import com.dudas.game.model.GemFactory;
import com.dudas.game.model.GemType;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by OLO on 1. 2. 2015
 */
public class ArrayTest {

    @Test
    public void arrayTest() {
        Array<Gem> gems = createGems();
        Gem gem1 = gems.get(5);
        Gem gem2 = gems.get(6);
        gems.swap(5, 6);
        Gem swappedGem1 = gems.get(6);
        Gem swappedGem2 = gems.get(5);
        assertTrue(gem1.equals(swappedGem1));
        assertTrue(gem2.equals(swappedGem2));
    }

    private Array<Gem> createGems() {
        Array<Gem> gemArray = new Array<Gem>();
        gemArray.addAll(createGem(),
                createGem(),
                createGem(),
                createGem(),
                createGem(),
                createGem(),
                createGem(),
                createGem(),
                createGem(),
                createGem(),
                createGem());
        return gemArray;
    }

    private Gem createGem() {
        return GemFactory.getGem(GemType.BLUE, 0, 0, 0);
    }

}
