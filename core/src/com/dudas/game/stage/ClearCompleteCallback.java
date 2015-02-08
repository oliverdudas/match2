package com.dudas.game.stage;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.Board;
import com.dudas.game.Gem;
import com.dudas.game.model.GemType;

/**
 * Created by foxy on 06/02/2015.
 */
public class ClearCompleteCallback implements Runnable {

    private GemActor gemActor;
    private Board board;
    private Array<Gem> gems;

    public ClearCompleteCallback() {
    }

    @Override
    public void run() {
        gemActor.setVisible(false);
        gemActor.setScale(1);
        gemActor.getGem().setType(GemType.EMPTY);

        if (board != null && gems != null) {
            board.fall(gems);
        }
    }

    public void addGemActor(GemActor actor) {
        this.gemActor = actor;
    }

    public void addBoard(Board board) {
        this.board = board;
    }

    public void addGems(Array<Gem> gems) {
        this.gems = gems;
    }
}
