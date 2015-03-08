package com.dudas.game.model.provider;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.model.Gem;
import com.dudas.game.model.GemFactory;
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
            gems.add(createGem(GemType.BLUE, 0, 0, 1));
            gems.add(createGem(GemType.RED, 0, 1, 2));
            gems.add(createGem(GemType.GREEN, 0, 2, 3));
            gems.add(createGem(GemType.PURPLE, 0, 3, 4));
            gems.add(createGem(GemType.YELLOW, 0, 4, 5));
            gems.add(createGem(GemType.ORANGE, 0, 5, 6));
            gems.add(createGem(GemType.RED, 0, 6, 7));
            gems.add(createGem(GemType.BLUE, 0, 7, 8));
            gems.add(createGem(GemType.ORANGE, 0, 8, 9));

            gems.add(createGem(GemType.BLUE, 1, 0, 10));
            gems.add(createGem(GemType.RED, 1, 1, 11));
            gems.add(createGem(GemType.BLUE, 1, 2, 12));
            gems.add(createGem(GemType.PURPLE, 1, 3, 13));
            gems.add(createGem(GemType.ORANGE, 1, 4, 14));
            gems.add(createGem(GemType.BLUE, 1, 5, 15));
            gems.add(createGem(GemType.GREEN, 1, 6, 16));
            gems.add(createGem(GemType.YELLOW, 1, 7, 17));
            gems.add(createGem(GemType.BLUE, 1, 8, 18));

            gems.add(createGem(GemType.YELLOW, 2, 0, 19));
            gems.add(createGem(GemType.BLUE, 2, 1, 20));
            gems.add(createGem(GemType.RED, 2, 2, 21));
            gems.add(createGem(GemType.ORANGE, 2, 3, 22));
            gems.add(createGem(GemType.GREEN, 2, 4, 23));
            gems.add(createGem(GemType.BLUE, 2, 5, 24));
            gems.add(createGem(GemType.ORANGE, 2, 6, 25));
            gems.add(createGem(GemType.RED, 2, 7, 26));
            gems.add(createGem(GemType.PURPLE, 2, 8, 27));

            gems.add(createGem(GemType.BLUE, 3, 0, 28));
            gems.add(createGem(GemType.RED, 3, 1, 29));
            gems.add(createGem(GemType.GREEN, 3, 2, 30));
            gems.add(createGem(GemType.BLUE, 3, 3, 31));
            gems.add(createGem(GemType.YELLOW, 3, 4, 32));
            gems.add(createGem(GemType.RED, 3, 5, 33));
            gems.add(createGem(GemType.BLUE, 3, 6, 34));
            gems.add(createGem(GemType.PURPLE, 3, 7, 35));
            gems.add(createGem(GemType.ORANGE, 3, 8, 36));

            gems.add(createGem(GemType.PURPLE, 4, 0, 37));
            gems.add(createGem(GemType.BLUE, 4, 1, 38));
            gems.add(createGem(GemType.ORANGE, 4, 2, 39));
            gems.add(createGem(GemType.GREEN, 4, 3, 40));
            gems.add(createGem(GemType.RED, 4, 4, 41));
            gems.add(createGem(GemType.BLUE, 4, 5, 42));
            gems.add(createGem(GemType.YELLOW, 4, 6, 43));
            gems.add(createGem(GemType.ORANGE, 4, 7, 44));
            gems.add(createGem(GemType.BLUE, 4, 8, 45));

            gems.add(createGem(GemType.PURPLE, 5, 0, 46));
            gems.add(createGem(GemType.YELLOW, 5, 1, 47));
            gems.add(createGem(GemType.RED, 5, 2, 48));
            gems.add(createGem(GemType.GREEN, 5, 3, 49));
            gems.add(createGem(GemType.BLUE, 5, 4, 50));
            gems.add(createGem(GemType.ORANGE, 5, 5, 51));
            gems.add(createGem(GemType.RED, 5, 6, 52));
            gems.add(createGem(GemType.ORANGE, 5, 7, 53));
            gems.add(createGem(GemType.BLUE, 5, 8, 54));

            gems.add(createGem(GemType.ORANGE, 6, 0, 55));
            gems.add(createGem(GemType.GREEN, 6, 1, 56));
            gems.add(createGem(GemType.BLUE, 6, 2, 57));
            gems.add(createGem(GemType.PURPLE, 6, 3, 58));
            gems.add(createGem(GemType.RED, 6, 4, 59));
            gems.add(createGem(GemType.ORANGE, 6, 5, 60));
            gems.add(createGem(GemType.BLUE, 6, 6, 61));
            gems.add(createGem(GemType.YELLOW, 6, 7, 62));
            gems.add(createGem(GemType.YELLOW, 6, 8, 63));

            gems.add(createGem(GemType.RED, 7, 0, 64));
            gems.add(createGem(GemType.BLUE, 7, 1, 65));
            gems.add(createGem(GemType.PURPLE, 7, 2, 66));
            gems.add(createGem(GemType.ORANGE, 7, 3, 67));
            gems.add(createGem(GemType.BLUE, 7, 4, 68));
            gems.add(createGem(GemType.YELLOW, 7, 5, 69));
            gems.add(createGem(GemType.RED, 7, 6, 70));
            gems.add(createGem(GemType.ORANGE, 7, 7, 71));
            gems.add(createGem(GemType.GREEN, 7, 8, 72));

            gems.add(createGem(GemType.BLUE, 8, 0, 73));
            gems.add(createGem(GemType.PURPLE, 8, 1, 74));
            gems.add(createGem(GemType.YELLOW, 8, 2, 75));
            gems.add(createGem(GemType.ORANGE, 8, 3, 76));
            gems.add(createGem(GemType.BLUE, 8, 4, 77));
            gems.add(createGem(GemType.RED, 8, 5, 78));
            gems.add(createGem(GemType.GREEN, 8, 6, 79));
            gems.add(createGem(GemType.RED, 8, 7, 80));
            gems.add(createGem(GemType.RED, 8, 8, 81));
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

    @Override
    public Gem createGem(GemType gemType, float x, float y, int id) {
        return GemFactory.getGem(gemType, x, y, id);
    }

}
