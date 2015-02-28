package com.dudas.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.dudas.game.Constants;

/**
 * Created by OLO on 16. 1. 2015.
 */
public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    public AssetBoard assetBoard;
    public AssetGems assetGems;
    private AssetManager assetManager;

    // singleton: prevent instantiation from other classes
    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;

        // set asset manager error handler
        assetManager.setErrorListener(this);

        // load texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);

        // start loading assets and wait until finished
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);

        for (String a : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + a);
        }

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

        // enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        // create game resource objects
        assetBoard = new AssetBoard(atlas);
        assetGems = new AssetGems(atlas);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '", throwable);
    }

    public class AssetBoard {
        public final TextureAtlas.AtlasRegion board;

        public AssetBoard(TextureAtlas atlas) {
            board = atlas.findRegion("board");
        }
    }

    public class AssetGems {
        public final TextureAtlas.AtlasRegion red;
        public final TextureAtlas.AtlasRegion green;
        public final TextureAtlas.AtlasRegion blue;
        public final TextureAtlas.AtlasRegion yellow;
        public final TextureAtlas.AtlasRegion orange;
        public final TextureAtlas.AtlasRegion purple;
        public final TextureAtlas.AtlasRegion empty;

        public AssetGems(TextureAtlas atlas) {
            red = atlas.findRegion("red_square");
            green = atlas.findRegion("green_square");
            blue = atlas.findRegion("blue_square");
            yellow = atlas.findRegion("yellow_square");
            orange = atlas.findRegion("orange_square");
            purple = atlas.findRegion("purple_square");
            empty = atlas.findRegion("empty_square");
        }
    }

}
