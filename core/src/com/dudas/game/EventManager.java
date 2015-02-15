package com.dudas.game;

import com.badlogic.gdx.utils.Array;
import com.dudas.game.controller.BoardEventListener;

/**
 * Created by OLO on 15. 2. 2015
 */
public interface EventManager {

    void fireSwap(Gem to, Gem from);

    void fireBackSwap(Gem fromGem, Gem toGem);


    void fireClearSuccess(Array<Gem> gems, Gem unclearedGem);


    void fireClearFail(float fromX, float fromY, float toX, float toY);


    void fireFall(Array<Gem> gems);

    void attach(BoardEventListener eventListener);
}
