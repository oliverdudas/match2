package com.dudas.game.stage;

import com.dudas.game.Board;
import com.dudas.game.model.GemType;

/**
 * Created by foxy on 06/02/2015.
 */
public class ClearCompleteCallback implements Runnable {

    private GemActor gemActor;
    private Board board;

    public ClearCompleteCallback() {
    }

    @Override
    public void run() {
        gemActor.setVisible(false);
        gemActor.setScale(1);
        gemActor.getGem().setType(GemType.EMPTY);

    }

    public void addGemActor(GemActor actor) {
        this.gemActor = actor;
    }

    public void addBoard(Board board) {
        this.board = board;
    }
}
