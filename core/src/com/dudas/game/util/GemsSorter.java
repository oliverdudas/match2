package com.dudas.game.util;

import com.dudas.game.model.Gem;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by OLO on 28. 2. 2015
 */
public class GemsSorter {

    /**
     * Sort gems by X and Y coordinates
     *
     * @param gems gems to sort
     */
    public static void sortGems(Gem... gems) {
        Arrays.sort(gems, new Comparator<Gem>() {
            @Override
            public int compare(Gem o1, Gem o2) {
                int byX = (int) o1.getX() - (int) o2.getX();
                if (byX != 0) {
                    return byX;
                } else {
                    return (int) o1.getY() - (int) o2.getY();
                }
            }
        });
    }
}
