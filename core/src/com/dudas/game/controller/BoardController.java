package com.dudas.game.controller;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.dudas.game.controller.event.*;
import com.dudas.game.controller.finder.ClearFinder;
import com.dudas.game.controller.finder.FallFinder;
import com.dudas.game.controller.helper.BoardHelper;
import com.dudas.game.controller.helper.DefaultBoardHelper;
import com.dudas.game.model.Gem;
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
        helper.swapSynchronized(fromIndex, toIndex, getGems());

        final Gem toGem = helper.findGem(toIndex, getGems());
        final Gem fromGem = helper.findGem(fromIndex, getGems());

        blockGems(fromGem, toGem);

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
        helper.swapSynchronized(fromIndex, toIndex, getGems());

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
        ClearFinder clearFinder = new ClearFinder(getGems(), helper);
        final Gem[] clearGems = clearFinder.find(fromGem, toGem);

        if (clearGems.length >= 3) {
            blockGems(clearGems);
            final Gem unclearedGem = resolveUnclearedGem(fromGem, toGem, clearGems);
            eventManager.fireClearSuccess(new BoardEvent() {
                @Override
                public Gem[] getGems() {
                    return clearGems;
                }

                @Override
                public void complete() {
                    if (unclearedGem != null) {
                        unclearedGem.setReady();
                    }
                    fall(clearGems);
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

    private void fall(final Gem... clearedGems) {
        FallFinder fallFinder = new FallFinder(getGems(), helper, gemsProvider);
        final Gem[] fallGems = fallFinder.find(clearedGems);

        eventManager.fireFall(new BoardEvent() {
            @Override
            public Gem[] getGems() {
                return fallGems;
            }

            @Override
            public void complete() {
                resetNewGems(clearedGems);
                clearFallen(fallGems);
            }

            private void resetNewGems(Gem... clearedGems) {
                for (Gem clearedGem : clearedGems) {
                    clearedGem.setNew(false);
                }
            }
        });
    }

    private void clearFallen(Gem... fallGems) {
        ClearFinder clearFinder = new ClearFinder(getGems(), helper);
        final Gem[] clearGems = clearFinder.find(fallGems);

        if (clearGems.length >= 3) {
            blockGems(clearGems);
            eventManager.fireClearSuccess(new BoardEvent() {
                @Override
                public Gem[] getGems() {
                    return clearGems;
                }

                @Override
                public void complete() {
                    fall(clearGems);
                }
            });
            // free celarGems in the pool
        } else {
            setReadyGems(fallGems);
//            END OF THE WHOLE SWAP, CLEAR, FALL CYCLE
//            gemArrayPool.free(gems); // setting free fallGems from fall(...)
//            gemArrayPool.free(clearGems);
        }

    }

    /**
     * Helper methods
     */

    private void blockGems(Gem... gems) {
        for (Gem gem : gems) {
            gem.block();
        }
    }

    private void setReadyGems(Gem... gems) {
        for (Gem gem : gems) {
            gem.setReady();
        }
    }

    private Gem resolveUnclearedGem(Gem fromGem, Gem toGem, Gem... clearGems) {
        Array<Gem> gems = new Array<Gem>(clearGems); // TODO
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


}
