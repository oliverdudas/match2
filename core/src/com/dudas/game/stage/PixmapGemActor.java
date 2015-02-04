package com.dudas.game.stage;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by OLO on 31. 1. 2015
 */
public class PixmapGemActor extends Actor {

    private Texture texture;
    private Vector2 position;

    public PixmapGemActor(float x, float y) {
        init(x, y);
    }

    private void init(float x, float y) {
        position = new Vector2(x, y);
        texture = new Texture(createProceduralPixmap(1, 1));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, position.x, position.y);
    }

    private Pixmap createProceduralPixmap(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        // Fill square with red color at 50% opacity
//        pixmap.setColor(1, 1, 0, 0.5f);
        pixmap.setColor(MathUtils.random(1f), MathUtils.random(1f), MathUtils.random(1f), 1f);
        pixmap.fill();

        // Draw a yellow-colored X shape on square
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);

        // Draw a cyan-colored border around square
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);

        return pixmap;
    }
}
