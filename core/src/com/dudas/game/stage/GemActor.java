package com.dudas.game.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.dudas.game.Gem;
import com.dudas.game.assets.Assets;
import com.dudas.game.model.GemModel;

/**
 * Created by OLO on 31. 1. 2015
 */
public class GemActor extends Actor {

    public GemActor(float x, float y, float width, float height) {
        init(x, y, width, height);
    }

    private void init(float x, float y, float width, float height) {
        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                getRegionByGemType(((Gem) getUserObject()).getType()),
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

    private TextureAtlas.AtlasRegion getRegionByGemType(GemModel.GemType type) {
        switch (type) {
            case RED:
                return Assets.instance.assetGems.red;
            case GREEN:
                return Assets.instance.assetGems.green;
            case BLUE:
                return Assets.instance.assetGems.blue;
            case PURPLE:
                return Assets.instance.assetGems.purple;
            case YELLOW:
                return Assets.instance.assetGems.yellow;
            case ORANGE:
                return Assets.instance.assetGems.orange;
        }
        throw new RuntimeException("There is no region for gemType: " + type.name());
    }

    public Gem getGem() {
        return (Gem) getUserObject();
    }

    public boolean isReady() {
        return getGem().isReady();
    }

    public void setReady() {
        getGem().setReady();
    }
}
