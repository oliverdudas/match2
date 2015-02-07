package com.dudas.game.stage;

import com.dudas.game.Board;

/**
 * Created by foxy on 06/02/2015.
 */
public class SwapCompleteCallback implements Runnable {

    private Board board;
    private GemActor selectedActor;
    private GemActor swapedActor;

    public SwapCompleteCallback() {
    }

    @Override
    public void run() {
//        selectedActor.setReady();
//        swapedActor.setReady();
        board.clear(selectedActor.getX(), selectedActor.getY(), swapedActor.getX(), swapedActor.getY());
    }

    public void addBoard(Board board) {
        this.board = board;
    }

    public void addSwapPair(GemActor actor1, GemActor actor2) {
        this.selectedActor = actor1;
        this.swapedActor = actor2;
    }
}
