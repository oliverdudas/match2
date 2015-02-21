package com.dudas.game.provider;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Gem;
import com.dudas.game.model.GemModel;
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
                    gems.add(createRandomGem(x, y));
                }
            }
        }
        return gems;
    }

    @Override
    public GemType getRandomGemType() {
        return GemType.getRandom();
    }

    private Gem createRandomGem(float x, float y) {
        return new GemModel(GemType.getRandom(), x, y);
    }
}
