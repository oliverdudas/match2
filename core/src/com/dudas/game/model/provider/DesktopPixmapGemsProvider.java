package com.dudas.game.model.provider;

import com.badlogic.gdx.graphics.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by foxy on 07/02/2015.
 */
public class DesktopPixmapGemsProvider extends AbstractPixmapGemsProvider {

    private BufferedImage image;

    public DesktopPixmapGemsProvider(String fileName) {
        super(fileName);
    }

    protected void createImage() {
        try {
            image = ImageIO.read(new File(ClassLoader.getSystemResource("pixmap/" + fileName).toURI()));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read ImageIO from file: " + fileName);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to read ImageIO from file: " + fileName);
        }
    }

    @Override
    protected int getImageWith() {
        return image.getWidth();
    }

    @Override
    protected int getImageHeight() {
        return image.getHeight();
    }

    @Override
    protected void setColor(Color color, int x, int y) {
        Color.argb8888ToColor(color, image.getRGB(x, y));
    }

}
