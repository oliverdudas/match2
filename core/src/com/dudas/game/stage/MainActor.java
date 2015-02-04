package com.dudas.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by OLO on 31. 1. 2015
 */
public class MainActor extends Actor {

    private Texture texture;

    public MainActor() {
        init();
    }

    private void init() {
        texture = new Texture(Gdx.files.internal("Aspect.jpg"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, 0, 0, 100, 100);
    }

//    private Pixmap createProceduralPixmap(int width, int height) {
//        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
//
//        // Fill square with red color at 50% opacity
//        pixmap.setColor(1, 0, 0, 0.5f);
//        pixmap.fill();
//
//        // Draw a yellow-colored X shape on square
//        pixmap.setColor(1, 1, 0, 1);
//        pixmap.drawLine(0, 0, width, height);
//        pixmap.drawLine(width, 0, 0, height);
//
//        // Draw a cyan-colored border around square
//        pixmap.setColor(0, 1, 1, 1);
//        pixmap.drawRectangle(0, 0, width, height);
//
//        return pixmap;
//    }
}
