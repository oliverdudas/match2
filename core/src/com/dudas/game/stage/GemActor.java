package com.dudas.game.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.dudas.game.assets.Assets;

/**
 * Created by OLO on 31. 1. 2015
 */
public class GemActor extends Actor {

    public GemActor(float x, float y, float width, float height) {
        init(x, y, width, height);
    }

    private void init(float x, float y, float width, float height) {
        setBounds(x, y, width, height);
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                Assets.instance.assetGems.red,
                this.getX(),
                this.getY(),
                this.getOriginX(),
                this.getOriginY(),
                this.getWidth(),
                this.getHeight(),
                this.getScaleX(),
                this.getScaleY(),
                this.getRotation(),
                true
        );
    }

}
