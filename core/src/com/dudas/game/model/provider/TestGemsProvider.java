package com.dudas.game.model.provider;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.model.Gem;
import com.dudas.game.model.GemModel;
import com.dudas.game.model.GemType;

/**
 * Created by foxy on 07/02/2015.
 */
public class TestGemsProvider implements GemsProvider {

    private Array<Gem> gems;

    public TestGemsProvider() {
    }

    @Override
    public Array<Gem> getGems(float width, float height) {
        if (gems == null) {
            gems = new Array<Gem>();
            gems.add(new GemModel(GemType.BLUE, 0, 0));
            gems.add(new GemModel(GemType.RED, 0, 1));
            gems.add(new GemModel(GemType.GREEN, 0, 2));
            gems.add(new GemModel(GemType.PURPLE, 0, 3));
            gems.add(new GemModel(GemType.YELLOW, 0, 4));
            gems.add(new GemModel(GemType.ORANGE, 0, 5));
            gems.add(new GemModel(GemType.RED, 0, 6));
            gems.add(new GemModel(GemType.BLUE, 0, 7));
            gems.add(new GemModel(GemType.ORANGE, 0, 8));

            gems.add(new GemModel(GemType.BLUE, 1, 0));
            gems.add(new GemModel(GemType.RED, 1, 1));
            gems.add(new GemModel(GemType.BLUE, 1, 2));
            gems.add(new GemModel(GemType.PURPLE, 1, 3));
            gems.add(new GemModel(GemType.ORANGE, 1, 4));
            gems.add(new GemModel(GemType.BLUE, 1, 5));
            gems.add(new GemModel(GemType.GREEN, 1, 6));
            gems.add(new GemModel(GemType.YELLOW, 1, 7));
            gems.add(new GemModel(GemType.BLUE, 1, 8));

            gems.add(new GemModel(GemType.YELLOW, 2, 0));
            gems.add(new GemModel(GemType.BLUE, 2, 1));
            gems.add(new GemModel(GemType.RED, 2, 2));
            gems.add(new GemModel(GemType.ORANGE, 2, 3));
            gems.add(new GemModel(GemType.GREEN, 2, 4));
            gems.add(new GemModel(GemType.BLUE, 2, 5));
            gems.add(new GemModel(GemType.ORANGE, 2, 6));
            gems.add(new GemModel(GemType.RED, 2, 7));
            gems.add(new GemModel(GemType.PURPLE, 2, 8));

            gems.add(new GemModel(GemType.BLUE, 3, 0));
            gems.add(new GemModel(GemType.RED, 3, 1));
            gems.add(new GemModel(GemType.GREEN, 3, 2));
            gems.add(new GemModel(GemType.BLUE, 3, 3));
            gems.add(new GemModel(GemType.YELLOW, 3, 4));
            gems.add(new GemModel(GemType.RED, 3, 5));
            gems.add(new GemModel(GemType.BLUE, 3, 6));
            gems.add(new GemModel(GemType.PURPLE, 3, 7));
            gems.add(new GemModel(GemType.ORANGE, 3, 8));

            gems.add(new GemModel(GemType.PURPLE, 4, 0));
            gems.add(new GemModel(GemType.BLUE, 4, 1));
            gems.add(new GemModel(GemType.ORANGE, 4, 2));
            gems.add(new GemModel(GemType.GREEN, 4, 3));
            gems.add(new GemModel(GemType.RED, 4, 4));
            gems.add(new GemModel(GemType.BLUE, 4, 5));
            gems.add(new GemModel(GemType.YELLOW, 4, 6));
            gems.add(new GemModel(GemType.ORANGE, 4, 7));
            gems.add(new GemModel(GemType.BLUE, 4, 8));

            gems.add(new GemModel(GemType.PURPLE, 5, 0));
            gems.add(new GemModel(GemType.YELLOW, 5, 1));
            gems.add(new GemModel(GemType.RED, 5, 2));
            gems.add(new GemModel(GemType.GREEN, 5, 3));
            gems.add(new GemModel(GemType.BLUE, 5, 4));
            gems.add(new GemModel(GemType.ORANGE, 5, 5));
            gems.add(new GemModel(GemType.RED, 5, 6));
            gems.add(new GemModel(GemType.ORANGE, 5, 7));
            gems.add(new GemModel(GemType.BLUE, 5, 8));

            gems.add(new GemModel(GemType.ORANGE, 6, 0));
            gems.add(new GemModel(GemType.GREEN, 6, 1));
            gems.add(new GemModel(GemType.BLUE, 6, 2));
            gems.add(new GemModel(GemType.PURPLE, 6, 3));
            gems.add(new GemModel(GemType.RED, 6, 4));
            gems.add(new GemModel(GemType.ORANGE, 6, 5));
            gems.add(new GemModel(GemType.BLUE, 6, 6));
            gems.add(new GemModel(GemType.YELLOW, 6, 7));
            gems.add(new GemModel(GemType.YELLOW, 6, 8));

            gems.add(new GemModel(GemType.RED, 7, 0));
            gems.add(new GemModel(GemType.BLUE, 7, 1));
            gems.add(new GemModel(GemType.PURPLE, 7, 2));
            gems.add(new GemModel(GemType.ORANGE, 7, 3));
            gems.add(new GemModel(GemType.BLUE, 7, 4));
            gems.add(new GemModel(GemType.YELLOW, 7, 5));
            gems.add(new GemModel(GemType.RED, 7, 6));
            gems.add(new GemModel(GemType.ORANGE, 7, 7));
            gems.add(new GemModel(GemType.GREEN, 7, 8));

            gems.add(new GemModel(GemType.BLUE, 8, 0));
            gems.add(new GemModel(GemType.PURPLE, 8, 1));
            gems.add(new GemModel(GemType.YELLOW, 8, 2));
            gems.add(new GemModel(GemType.ORANGE, 8, 3));
            gems.add(new GemModel(GemType.BLUE, 8, 4));
            gems.add(new GemModel(GemType.RED, 8, 5));
            gems.add(new GemModel(GemType.GREEN, 8, 6));
            gems.add(new GemModel(GemType.RED, 8, 7));
            gems.add(new GemModel(GemType.RED, 8, 8));
        }
        return gems;
    }

    @Override
    public GemType getRandomGemType() {
        return GemType.getRandom();
    }

    @Override
    public void reset() {
        gems = null;
    }

}
