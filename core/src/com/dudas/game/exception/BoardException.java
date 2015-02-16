package com.dudas.game.exception;

/**
 * Created by OLO on 16. 2. 2015
 */
public abstract class BoardException extends RuntimeException {
    public BoardException(String message) {
        super(message);
    }
}
