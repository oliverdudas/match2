package com.dudas.game.stage;

import com.dudas.game.Board;

/**
 * Created by foxy on 06/02/2015.
 */
public class SwapCompleteCallback implements Runnable {

    private Board board;
    private GemActor selectedActor;
    private GemActor swapedActor;
    private boolean backSwap;

    public SwapCompleteCallback() {
        backSwap = false;
    }

    @Override
    public void run() {
        if (!backSwap) {
            board.clear(selectedActor.getX(), selectedActor.getY(), swapedActor.getX(), swapedActor.getY());
        } else {
            selectedActor.setReady();
            swapedActor.setReady();
        }
    }

    public void addBoard(Board board) {
        this.board = board;
    }

    public void addSwapPair(GemActor actor1, GemActor actor2) {
        this.selectedActor = actor1;
        this.swapedActor = actor2;
    }

    public void setBackSwap(boolean backSwap) {
        this.backSwap = backSwap;
    }
}
