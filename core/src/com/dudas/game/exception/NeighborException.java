package com.dudas.game.exception;

/**
 * Created by OLO on 16. 2. 2015
 */
public class NeighborException extends BoardException {

    public NeighborException() {
        super("Not a neighbor.");
    }
}
