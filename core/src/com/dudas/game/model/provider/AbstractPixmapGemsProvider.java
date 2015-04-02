package com.dudas.game.model.provider;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.dudas.game.model.Gem;
import com.dudas.game.model.GemFactory;
import com.dudas.game.model.GemType;

/**
 * Created by foxy on 07/02/2015.
 */
public abstract class AbstractPixmapGemsProvider implements GemsProvider {

    protected Array<Gem> gems;
    private Array<GemType> gemTypeStack;
    protected String fileName;

    public AbstractPixmapGemsProvider(String fileName) {
        this.fileName = fileName;
        this.gems = new Array<Gem>();
        init();
    }

    protected abstract void createImage();

    protected abstract int getImageWith();

    protected abstract int getImageHeight();

    protected abstract void setColor(Color color, int x, int y);

    private void readPixmapFile() {
        createImage();
        int width = getImageWith();
        int height = getImageHeight();

        Color color = new Color();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int invertedY = height - 1 - y;
                setColor(color, x, invertedY);
                int R = (int) (color.r * 255f);
                int G = (int) (color.g * 255f);
                int B = (int) (color.b * 255f);
                int A = (int) (color.a * 255f);

                if (GEM_COLOR_TYPE.RED.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.RED, x, y, createId(height, x, y)));
                } else if (GEM_COLOR_TYPE.GREEN.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.GREEN, x, y, createId(height, x, y)));
                } else if (GEM_COLOR_TYPE.BLUE.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.BLUE, x, y, createId(height, x, y)));
                } else if (GEM_COLOR_TYPE.ORANGE.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.ORANGE, x, y, createId(height, x, y)));
                } else if (GEM_COLOR_TYPE.PURPLE.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.PURPLE, x, y, createId(height, x, y)));
                } else if (GEM_COLOR_TYPE.YELLOW.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.YELLOW, x, y, createId(height, x, y)));
                } else if (GEM_COLOR_TYPE.EMPTY.sameColor(R, G, B, A)) {
                    Gem emptyGem = GemFactory.getGem(GemType.EMPTY, x, y, createId(height, x, y));
                    emptyGem.block();
                    gems.add(emptyGem);
                } else {
                    throw new RuntimeException("Unknown pixel RGB.");
                }
            }
        }
    }

    private void init() {
        readPixmapFile();
    }

    @Override
    public Array<Gem> getGems(float width, float height) {
        return gems;
    }

    @Override
    public GemType getRandomGemType() {
        if (gemTypeStack != null && gemTypeStack.size > 0) {
            return gemTypeStack.removeIndex(0);
        }
        return GemType.getRandom();
    }

    @Override
    public void reset() {
        init();
    }

    @Override
    public Gem createGem(GemType gemType, float x, float y, int id) {
        return null;
    }

    public void mockRandomGemTypes(GemType... gemTypes) {
        if (gemTypeStack == null) {
            gemTypeStack = new Array<GemType>();
            gemTypeStack.sort();
        }

        gemTypeStack.addAll(gemTypes);
    }

    protected int createId(int height, int x, int y) {
        return x * height + y + 1;
    }

    public enum GEM_COLOR_TYPE {
        RED(245, 86, 103, 255),      // 245,  86, 103, 255
        GREEN(122, 167, 58, 255),    // 122, 167,  58, 255
        BLUE(74, 210, 242, 255),     //  74, 210, 242, 255
        ORANGE(217, 127, 55, 255),   // 217, 127,  55, 255
        PURPLE(165, 144, 236, 255),  // 165, 144, 236, 255
        YELLOW(211, 180, 16, 255),   // 211, 180,  16, 255
        EMPTY(255, 255, 255, 1);     // 255, 255, 255,   1

        private int r;
        private int g;
        private int b;
        private int a;

        private GEM_COLOR_TYPE(int r, int g, int b, int a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }

        public boolean sameColor(int r, int g, int b, int a) {
            return this.r == r && this.g == g && this.b == b && this.a == a;
        }

    }

}
