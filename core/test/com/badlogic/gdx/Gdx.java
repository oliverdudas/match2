package com.badlogic.gdx;

/**
 * Fake Gdx class because of junit testing issue. During executing junit test this class is empty and tests fails on nullpointers.
 * We need to fake some implementation
 */
public class Gdx {

    public static Graphics graphics = new GraphicsImpl();

    public static Application app = new ApplicationImpl();
}
