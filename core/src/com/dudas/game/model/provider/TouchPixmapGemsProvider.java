package com.dudas.game.model.provider;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

/**
 * Created by foxy on 07/02/2015.
 */
public class TouchPixmapGemsProvider extends AbstractPixmapGemsProvider {

    private Pixmap image;

    public TouchPixmapGemsProvider(String fileName) {
        super(fileName);
    }

    @Override
    protected void createImage() {
        image = new Pixmap(new FileHandle(fileName));
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
        Color.rgba8888ToColor(color, image.getPixel(x, y));
    }

}
