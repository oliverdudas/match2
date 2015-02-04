package com.dudas.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by foxy on 04/02/2015.
 */
public class MainStage extends Stage {

    public MainStage(Batch batch) {
        super(new FitViewport(100, 100), batch);
        init();
    }

    private void init() {
        MainActor actor = new MainActor();
        actor.setTouchable(Touchable.enabled);
        addActor(actor);
        Gdx.input.setInputProcessor(this);
    }

    public void resize(int width, int height) {
        getViewport().update(width, height);
    }
}
