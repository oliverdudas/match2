package com.dudas.game.model.provider;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.dudas.game.model.Gem;
import com.dudas.game.model.GemFactory;
import com.dudas.game.model.GemType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by foxy on 07/02/2015.
 */
public class PixmapGemsProvider implements GemsProvider {

    private Array<Gem> gems;
    private Array<GemType> gemTypeStack;

    public PixmapGemsProvider(String fileName) {
        this.gems = new Array<Gem>();
        init(fileName);
    }

    private void init(String filePath) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(ClassLoader.getSystemResource("pixmap/" + filePath).toURI()));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read ImageIO from file: " + filePath);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to read ImageIO from file: " + filePath);
        }
        int width = image.getWidth();
        int height = image.getHeight();

        Color color = new Color();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int invertedY = height - 1 - y;
                Color.argb8888ToColor(color, image.getRGB(x, invertedY));
                int R = (int) (color.r * 255f);
                int G = (int) (color.g * 255f);
                int B = (int) (color.b * 255f);
                int A = (int) (color.a * 255f);

                if (GEM_COLOR_TYPE.RED.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.RED, x, y));
                } else if (GEM_COLOR_TYPE.GREEN.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.GREEN, x, y));
                } else if (GEM_COLOR_TYPE.BLUE.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.BLUE, x, y));
                } else if (GEM_COLOR_TYPE.ORANGE.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.ORANGE, x, y));
                } else if (GEM_COLOR_TYPE.PURPLE.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.PURPLE, x, y));
                } else if (GEM_COLOR_TYPE.YELLOW.sameColor(R, G, B, A)) {
                    gems.add(GemFactory.getGem(GemType.YELLOW, x, y));
                } else {
                    throw new RuntimeException("Unknown pixel RGB.");
                }
            }
        }
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

    public void mockRandomGemTypes(GemType... gemTypes) {
        if (gemTypeStack == null) {
            gemTypeStack = new Array<GemType>();
            gemTypeStack.sort();
        }

        gemTypeStack.addAll(gemTypes);
    }

    public enum GEM_COLOR_TYPE {
        RED(245, 86, 103, 255),      // 245,  86, 103, 255
        GREEN(122, 167, 58, 255),    // 122, 167,  58, 255
        BLUE(74, 210, 242, 255),     //  74, 210, 242, 255
        ORANGE(217, 127, 55, 255),   // 217, 127,  55, 255
        PURPLE(165, 144, 236, 255),  // 165, 144, 236, 255
        YELLOW(211, 180, 16, 255);   // 211, 180,  16, 255

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
