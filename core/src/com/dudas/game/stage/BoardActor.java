package com.dudas.game.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.dudas.game.assets.Assets;

/**
 * Created by OLO on 31. 1. 2015
 */
public class BoardActor extends Actor {

    public BoardActor(float x, float y, float width, float height) {
        setBounds(x, y, width, height);
        init();
    }

    private void init() {
        setTouchable(Touchable.enabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                Assets.instance.assetBoard.board,
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight());
    }

}
