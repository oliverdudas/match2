package com.dudas.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import javax.naming.OperationNotSupportedException;

/**
 * Created by foxy on 03/02/2015.
 */
public class ExtendViewportWithRightCamera extends ExtendViewport {

    public ExtendViewportWithRightCamera(float minWorldWidth, float minWorldHeight) {
        super(minWorldWidth, minWorldHeight);
    }

    public ExtendViewportWithRightCamera(float minWorldWidth, float minWorldHeight, Camera camera) {
        super(minWorldWidth, minWorldHeight, camera);
    }

    public ExtendViewportWithRightCamera(float minWorldWidth, float minWorldHeight, float maxWorldWidth, float maxWorldHeight) {
        super(minWorldWidth, minWorldHeight, maxWorldWidth, maxWorldHeight);
    }

    public ExtendViewportWithRightCamera(float minWorldWidth, float minWorldHeight, float maxWorldWidth, float maxWorldHeight, Camera camera) {
        super(minWorldWidth, minWorldHeight, maxWorldWidth, maxWorldHeight, camera);
    }

    @Override
    public void apply() {
        apply(false);
    }

    @Override
    public void apply(boolean centerCamera) {
//        if (centerCamera) {
//            throw new RuntimeException("Centering camera not supported for " + getClass().getSimpleName());
//        }

        Camera camera = getCamera();
        Gdx.gl.glViewport(getScreenX(), getScreenY(), getScreenWidth(), getScreenHeight());
        camera.viewportWidth = getWorldWidth();
        camera.viewportHeight = getWorldHeight();

        if (centerCamera) {
            camera.position.set(getWorldWidth() / 2, getWorldHeight() / 2, 0);
        } else {
            applyRightCamera(camera);
        }
        camera.update();
    }

    private Vector3 applyRightCamera(Camera camera) {
        return camera.position.set(getMinWorldWidth() - (getWorldWidth() * 0.5f), getMinWorldHeight() - (getWorldHeight() * 0.5f), 0);
    }
}
