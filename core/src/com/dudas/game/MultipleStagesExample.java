package com.dudas.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MultipleStagesExample extends Game {

    @Override
    public void create() {
        setScreen(new ExampleScreen(this));
    }

}
