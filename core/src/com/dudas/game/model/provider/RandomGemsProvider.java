package com.dudas.game.model.provider;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.model.Gem;
import com.dudas.game.model.GemFactory;
import com.dudas.game.model.GemType;

/**
 * Created by foxy on 07/02/2015.
 */
public class RandomGemsProvider implements GemsProvider {

    private Array<Gem> gems;

    @Override
    public Array<Gem> getGems(float width, float height) {
        if (gems == null) {
            gems = new Array<Gem>();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    gems.add(createRandomGem(x, y, createId((int) height, x, y)));
                }
            }
        }
        return gems;
    }

    private int createId(int height, int x, int y) {
        return x * height + y + 1;
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
        return null;
    }

    private Gem createRandomGem(float x, float y, int id) {
        return GemFactory.getGem(GemType.getRandom(), x, y, id);
    }
}
