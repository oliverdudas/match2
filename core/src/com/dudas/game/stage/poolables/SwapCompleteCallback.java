package com.dudas.game.stage.poolables;

import com.dudas.game.Board;
import com.dudas.game.stage.GemActor;

/**
 * Created by foxy on 06/02/2015.
 */
public class SwapCompleteCallback extends BasePool<SwapCompleteCallback> implements Runnable {

    private Board board;
    private GemActor selectedActor;
    private GemActor swapedActor;
    private boolean backSwap;

    @Override
    public void run() {
        if (!backSwap) {
            board.clear(selectedActor.getX(), selectedActor.getY(), swapedActor.getX(), swapedActor.getY());
        } else {
            board.setGemReady(selectedActor.getX(), selectedActor.getY());
            board.setGemReady(swapedActor.getX(), swapedActor.getY());
        }

        returnToPool(this);
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

    @Override
    public void reset() {
        this.board = null;
        this.selectedActor = null;
        this.swapedActor = null;
        this.backSwap = false;
        super.reset();
    }
}
