package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.dudas.game.controller.event.EventManager;
import com.dudas.game.model.Gem;
import com.dudas.game.controller.event.BoardEvent;
import com.dudas.game.controller.event.BoardEventListener;
import com.dudas.game.controller.event.BoardEventManager;
import com.dudas.game.controller.event.TwoGemsBoardEvent;
import com.dudas.game.controller.helper.BoardHelper;
import com.dudas.game.controller.helper.DefaultBoardHelper;
import com.dudas.game.model.GemType;
import com.dudas.game.model.provider.GemsProvider;
import com.dudas.game.model.provider.TestGemsProvider;

/**
 * Created by foxy on 04/02/2015.
 */
public class BoardController implements Board {

    private static final String TAG = BoardController.class.getName();

    private final float width;
    private final float height;

    private GemsProvider gemsProvider;
    private EventManager eventManager;
    private BoardHelper helper;

    private Pool<Array<Gem>> gemArrayPool;

    public BoardController(float width, float height) {
        this(width, height, new TestGemsProvider());
    }

    public BoardController(float width, float height, GemsProvider provider) {
        this(width, height, provider, new BoardEventManager(), new DefaultBoardHelper(width, height));
    }

    public BoardController(float width, float height, GemsProvider gemsProvider, EventManager eventManager) {
        this(width, height, gemsProvider, eventManager, new DefaultBoardHelper(width, height));
    }

    public BoardController(float width, float height, GemsProvider provider, EventManager eventManager, BoardHelper helper) {
        this.width = width;
        this.height = height;
        this.gemsProvider = provider;
        this.eventManager = eventManager;
        this.helper = helper;
        init();
    }

    private void init() {
        initGemArrayPool();
//        initRightBorderIndexes();
    }

    private void initGemArrayPool() {
        this.gemArrayPool = new Pool<Array<Gem>>() {
            protected Array<Gem> newObject() {
                return new Array<Gem>();
            }

            @Override
            public Array<Gem> obtain() {
                Array<Gem> gems = super.obtain();
                gems.clear();
                return gems;
            }
        };
    }

    /**
     * API methods
     */

    /**
     * Returns all board gems
     *
     * @return all board gems
     */
    @Override
    public Array<Gem> getGems() {
        return gemsProvider.getGems(width, height);
    }

    /**
     * Sets gems provider which provides all board gems
     *
     * @param provider provides all board gems
     */
    @Override
    public void setGemsProvider(GemsProvider provider) {
        this.gemsProvider = provider;
    }

    /**
     * Sets the event listener
     *
     * @param matchGameListener listens for board events
     */
    @Override
    public void setEventProcessor(BoardEventListener matchGameListener) {
        eventManager.attach(matchGameListener);
    }

    /**
     * The board width
     *
     * @return board width
     */
    @Override
    public float getWidth() {
        return width;
    }

    /**
     * The board height
     *
     * @return board height
     */
    @Override
    public float getHeight() {
        return height;
    }

    /**
     * Sets event manager which handles all events produces by flow methods
     *
     * @param eventManager handles all events produces by flow methods
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     *  Flow methods
     */

    /**
     * Swaps two gems.
     * This is the entry point for game.
     *
     * @param fromX is the x coordinate of first gem
     * @param fromY is the y coordinate of first gem
     * @param toX   is the x coordinate of second gem
     * @param toY   is the y coordinate of second gem
     */
    @Override
    public void swap(float fromX, float fromY, float toX, float toY) {
        helper.checkNeighborCoordinates(fromX, fromY, toX, toY);

        int fromIndex = helper.createGemBoardIndex(fromX, fromY);
        int toIndex = helper.createGemBoardIndex(toX, toY);
        swapSynchronized(fromIndex, toIndex);

        final Gem toGem = findGem(toIndex);
        final Gem fromGem = findGem(fromIndex);

        toGem.block();
        fromGem.block();

        eventManager.fireSwap(new TwoGemsBoardEvent() {

            @Override
            public Gem getFromGem() {
                return fromGem;
            }

            @Override
            public Gem getToGem() {
                return toGem;
            }

            @Override
            public Gem[] getGems() {
                return new Gem[]{getToGem(), getFromGem()};
            }

            @Override
            public void complete() {
                clear(getFromGem(), getToGem());
            }
        });

    }

    private void backSwap(final Gem fromGem, final Gem toGem) {
        int fromIndex = fromGem.getIndex();
        int toIndex = toGem.getIndex();
        swapSynchronized(fromIndex, toIndex);

        eventManager.fireBackSwap(new TwoGemsBoardEvent() {
            @Override
            public Gem getFromGem() {
                return fromGem;
            }

            @Override
            public Gem getToGem() {
                return toGem;
            }

            @Override
            public Gem[] getGems() {
                return new Gem[]{getFromGem(), getToGem()};
            }

            @Override
            public void complete() {
                getFromGem().setReady();
                getToGem().setReady();
            }
        });
    }

    private void clear(final Gem fromGem, final Gem toGem) {
        final Array<Gem> gems = gemArrayPool.obtain();

        GemType fromType = fromGem.getType();
        populateClearGems(fromGem, gems, fromType);

        GemType toType = toGem.getType();
        populateClearGems(toGem, gems, toType);

        if (gems.size >= 3) {
            final Gem unclearedGem = resolveUnclearedGem(gems, fromGem, toGem);
            blockGems(gems);
            eventManager.fireClearSuccess(new BoardEvent() {
                @Override
                public Gem[] getGems() {
                    return gems.toArray(Gem.class);
                }

                @Override
                public void complete() {
                    if (unclearedGem != null) {
                        unclearedGem.setReady();
                    }
                    fall(gems);
                }
            });
        } else {
            eventManager.fireClearFail(new TwoGemsBoardEvent() {

                @Override
                public Gem getFromGem() {
                    return fromGem;
                }

                @Override
                public Gem getToGem() {
                    return toGem;
                }

                @Override
                public Gem[] getGems() {
                    return new Gem[]{getFromGem(), getToGem()};
                }

                @Override
                public void complete() {
                    backSwap(getFromGem(), getToGem());
                }
            });
        }
    }

    private void fall(final Array<Gem> clearedGems) {
        final Array<Gem> fallGems = gemArrayPool.obtain();
        for (Gem gem : clearedGems) {
            gem.setNew(true);
            gem.setType(gemsProvider.getRandomGemType());
            moveGemToTop(gem.getIndex(), fallGems);
        }
        eventManager.fireFall(new BoardEvent() {
            @Override
            public Gem[] getGems() {
                return fallGems.toArray(Gem.class);
            }

            @Override
            public void complete() {
                resetNewGems(clearedGems);
                clearFallen(fallGems);
            }

            private void resetNewGems(Array<Gem> clearedGems) {
                for (Gem clearedGem : clearedGems) {
                    clearedGem.setNew(false);
                }
            }
        });
    }

    private void clearFallen(Array<Gem> gems) {
        final Array<Gem> clearGems = gemArrayPool.obtain();

        for (Gem gem : gems) {
            gem.setReady();
            populateClearGems(gem, clearGems, gem.getType());
        }

        if (clearGems.size > 2) {
            blockGems(clearGems);
            eventManager.fireClearSuccess(new BoardEvent() {
                @Override
                public Gem[] getGems() {
                    return clearGems.toArray(Gem.class);
                }

                @Override
                public void complete() {
                    fall(clearGems);
                }
            });
            // free celarGems in the pool
        } else {
//            END OF THE WHOLE SWAP, CLEAR, FALL CYCLE
            gemArrayPool.free(gems); // setting free fallGems from fall(...)
            gemArrayPool.free(clearGems);
        }

    }

    /**
     *  Helper methods
     */

    /**
     * Swaps indexes in the gems array and also swaps both gems positions
     *
     * @param fromIndex index of forst gem to swap
     * @param toIndex   index of second gem to swap
     */
    private void swapSynchronized(int fromIndex, int toIndex) {
        getGems().swap(fromIndex, toIndex);
        synchronizeGemPosition(fromIndex);
        synchronizeGemPosition(toIndex);
    }

    private void blockGems(Array<Gem> gems) {
        for (Gem gem : gems) {
            gem.block();
        }
    }

    private Gem resolveUnclearedGem(Array<Gem> gems, Gem fromGem, Gem toGem) {
        Gem unclearedGem; // during swap and clearfinding on from 2 gems must not be cleared
        if (!gems.contains(fromGem, false)) {
            unclearedGem = fromGem;
        } else if (!gems.contains(toGem, false)) {
            unclearedGem = toGem;
        } else {
            unclearedGem = null;
        }
        return unclearedGem;
    }

    private Array<Gem> populateClearGems(Gem baseGem, Array<Gem> gems, GemType gemType) {
        Array<Gem> upClearArray = resolveClear(baseGem, 0f, 1f);  // up
        Array<Gem> downClearArray = resolveClear(baseGem, 0f, -1f); // down

        if (upClearArray.size + downClearArray.size >= 2) {
            gems.add(baseGem);
            gems.addAll(upClearArray);
            gems.addAll(downClearArray);
        }
        gemArrayPool.free(upClearArray);
        gemArrayPool.free(downClearArray);

        Array<Gem> rightClearArray = resolveClear(baseGem, 1f, 0f);  // right
        Array<Gem> leftClearArray = resolveClear(baseGem, -1f, 0f); // left
        if (rightClearArray.size + leftClearArray.size >= 2) {
            if (!gems.contains(baseGem, false)) {
                gems.add(baseGem);
            }
            gems.addAll(rightClearArray);
            gems.addAll(leftClearArray);
        }
        gemArrayPool.free(rightClearArray);
        gemArrayPool.free(leftClearArray);

        return gems;
    }

    private Array<Gem> resolveClear(Gem actualGem, float directionX, float directionY) {
        int actualBoardIndex = actualGem.getIndex();
        float nextX = actualGem.getX() + directionX;
        float nextY = actualGem.getY() + directionY;
        int nextBoardIndex = helper.createGemBoardIndex(nextX, nextY);
        if (helper.areNeighborIndexes(actualBoardIndex, nextBoardIndex) && helper.areSameType(actualGem, findGem(nextBoardIndex))) {
            Array<Gem> gemArray = resolveClear(findGem(nextBoardIndex), directionX, directionY);
            gemArray.add(findGem(nextBoardIndex));
            return gemArray;
        } else {
            return gemArrayPool.obtain();
        }
    }

    /**
     * Moving the gem in its column from original position(0, 1, ..., height)
     * to top(height). The gem is repeatedly swaped with every above
     * gem till it reaches the top. So every gem above the first should be
     * moved below its original position.
     */
    private void moveGemToTop(int gemIndex, Array<Gem> fallGems) {
        int aboveGemIndex = helper.getAboveNeighborIndex(gemIndex);
        if (helper.isValidIndex(gemIndex) && helper.isValidIndex(aboveGemIndex)) {
            swapSynchronized(gemIndex, aboveGemIndex);
            moveGemToTop(aboveGemIndex, fallGems);
        }
        Gem gem = findGem(gemIndex); // TODO: refactor
        if (!fallGems.contains(gem, false)) { // maybe a Set would be better, to prevent duplicate items
            gem.block();
            fallGems.add(gem);
        }
    }

    private Gem synchronizeGemPosition(int boardIndex) {
        Gem gem = findGem(boardIndex);
        gem.setIndex(boardIndex);
        return gem;
    }

    private Gem findGem(int boardIndex) {
        Gem gem = getGems().get(boardIndex);
        if (gem == null) {
            throw new RuntimeException("Gem on boardIndex position doesn't exist!");
        }
        return gem;
    }


}
