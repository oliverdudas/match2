package com.dudas.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pool;
import com.dudas.game.Board;
import com.dudas.game.Constants;
import com.dudas.game.Gem;
import com.dudas.game.event.action.ClearDoneEvent;
import com.dudas.game.event.action.FireEventAction;
import com.dudas.game.event.matchgame.MatchGameEventManager;
import com.dudas.game.event.matchgame.MatchGameListener;
import com.dudas.game.model.GemType;
import com.dudas.game.stage.poolables.ClearCompleteCallback;
import com.dudas.game.stage.poolables.BoardCountDownEventAction;
import com.dudas.game.stage.poolables.FallCompleteCallback;
import com.dudas.game.stage.poolables.SwapCompleteCallback;
import com.dudas.game.util.ExtendViewportWithRightCamera;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by foxy on 04/02/2015.
 */
public class GameStage extends Stage implements MatchGameListener {

    public static final String TAG = GameStage.class.getName();
    public static final float ACTION_DURATION = 0.2f;

    private Group group;
    private Board board;
    private Vector2 touchPosition;
    private GemActor selectedActor;
    private GemActor hitActor;
    private boolean swapEnabled;
    private ArrayMap<Gem, GemActor> gemActors;
    private Pool<MoveToAction> moveToActionPool;
    private Pool<ScaleToAction> scaleToActionPool;
    private Pool<SwapCompleteCallback> swapCompleteCallbackPool;
    private Pool<ClearCompleteCallback> clearCompleteCallbackPool;
    private Pool<FallCompleteCallback> fallCompleteCallbackPool;
    private Pool<FireEventAction> fireEventPool;
    private Pool<BoardCountDownEventAction> clearDoneCountdownEventActionPool;
    private Pool<ClearDoneEvent> clearDoneEventPool;
    private Pool<IntArray> fallDistancesPool;

    public GameStage(Batch batch, Board board) {
        super(new ExtendViewportWithRightCamera(board.getWidth(), board.getHeight()), batch);
        this.board = board;
        this.touchPosition = new Vector2();
        this.swapEnabled = true;
        this.gemActors = new ArrayMap<Gem, GemActor>();
        this.moveToActionPool = new Pool<MoveToAction>() {
            @Override
            protected MoveToAction newObject() {
                return new MoveToAction();
            }

            @Override
            public MoveToAction obtain() {
                MoveToAction obtain = super.obtain();
                obtain.setPool(moveToActionPool);
                return obtain;
            }
        };
        this.scaleToActionPool = new Pool<ScaleToAction>() {
            @Override
            protected ScaleToAction newObject() {
                return new ScaleToAction();
            }

            @Override
            public ScaleToAction obtain() {
                ScaleToAction obtain = super.obtain();
                obtain.setPool(scaleToActionPool);
                return obtain;
            }
        };
        this.swapCompleteCallbackPool = new Pool<SwapCompleteCallback>() {
            @Override
            protected SwapCompleteCallback newObject() {
                return new SwapCompleteCallback();
            }

            @Override
            public SwapCompleteCallback obtain() {
                SwapCompleteCallback obtain = super.obtain();
                obtain.setPool(swapCompleteCallbackPool);
                return obtain;
            }
        };
        this.clearCompleteCallbackPool = new Pool<ClearCompleteCallback>() {
            @Override
            protected ClearCompleteCallback newObject() {
                return new ClearCompleteCallback();
            }

            @Override
            public ClearCompleteCallback obtain() {
                ClearCompleteCallback obtain = super.obtain();
                obtain.setPool(clearCompleteCallbackPool);
                return obtain;
            }
        };
        this.fallCompleteCallbackPool = new Pool<FallCompleteCallback>() {
            @Override
            protected FallCompleteCallback newObject() {
                return new FallCompleteCallback();
            }

            @Override
            public FallCompleteCallback obtain() {
                FallCompleteCallback obtain = super.obtain();
                obtain.setPool(fallCompleteCallbackPool);
                return obtain;
            }
        };  // TODO: after implementing check if pooling works fine
        this.fireEventPool = new Pool<FireEventAction>() {
            @Override
            protected FireEventAction newObject() {
                return new FireEventAction();
            }

            @Override
            public FireEventAction obtain() {
                FireEventAction obtain = super.obtain();
                obtain.setPool(fireEventPool);
                return obtain;
            }
        };
        this.clearDoneCountdownEventActionPool = new Pool<BoardCountDownEventAction>() {
            @Override
            protected BoardCountDownEventAction<ClearDoneEvent> newObject() {
                return new BoardCountDownEventAction<ClearDoneEvent>(ClearDoneEvent.class);
            }

            @Override
            public BoardCountDownEventAction<ClearDoneEvent> obtain() {
                BoardCountDownEventAction<ClearDoneEvent> obtain = super.obtain();
                obtain.setPool(clearDoneCountdownEventActionPool);
                return obtain;
            }
        };
        this.clearDoneEventPool = new Pool<ClearDoneEvent>() {
            @Override
            protected ClearDoneEvent newObject() {
                return new ClearDoneEvent();
            }

            @Override
            public ClearDoneEvent obtain() {
                ClearDoneEvent obtain = super.obtain();
                obtain.setPool(clearDoneEventPool);
                return obtain;
            }
        };
        this.fallDistancesPool = new Pool<IntArray>() {
            protected IntArray newObject() {
                IntArray intArray = new IntArray(true, (int) getWidth());
                for (int i = 0; i < getWidth(); i++) {
                    intArray.add(0);
                }
                return intArray;
            }

            @Override
            public IntArray obtain() {
                IntArray distances = super.obtain();
                for (int i = 0; i < distances.size; i++) {
                    distances.set(i, 0);
                }
                return distances;
            }
        };
        init();
    }

    private void init() {
        initGroup();
        initBoardActor();
        initGemActors();
        MatchGameEventManager.setMatchGameProcessor(this);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean touchDragged = super.touchDragged(screenX, screenY, pointer);

        if (isSwapEnabled()) {
            screenToStageCoordinates(touchPosition.set(screenX, screenY));
            hitActor = (GemActor) hit(touchPosition.x, touchPosition.y, true);
            if (isSwapPossible()) {
                disableGemActorSwap();
                selectedActor.block();
                hitActor.block();
                board.swap(selectedActor.getX(), selectedActor.getY(), hitActor.getX(), hitActor.getY());
                return true;
            } else {
                selectedActor = hitActor;
            }
            return touchDragged;
        }
        return false;
    }

    private boolean isSwapPossible() {
        return selectedActor != null
                && hitActor != null
                && hitActor != selectedActor
                && hitActor.isReady()
                && selectedActor.isReady()
                && isVerticalOrHorizontalSwap();
    }

    private boolean isVerticalOrHorizontalSwap() {
        return selectedActor.getGem().getX() == hitActor.getGem().getX()
                || selectedActor.getGem().getY() == hitActor.getGem().getY();
    }

    @Override
    public void onSwap(Gem to, Gem from) {
        SwapCompleteCallback swapCompleteCallback = swapCompleteCallbackPool.obtain();
        GemActor fromActor = gemActors.get(from);
        GemActor toActor = gemActors.get(to);
        swapCompleteCallback.addSwapPair(fromActor, toActor);
        swapCompleteCallback.addBoard(board);
        handleSwapAction(fromActor, from.getX(), from.getY());
        handleSwapAction(toActor, to.getX(), to.getY(), swapCompleteCallback);

        removeSelection();
    }

    private void handleSwapAction(final GemActor gemActor, float x, float y) {
        handleSwapAction(gemActor, x, y, null);
    }

    private void handleSwapAction(final GemActor gemActor, float x, float y, SwapCompleteCallback swapCompleteCallback) {
        MoveToAction fromTo = moveToActionPool.obtain();
        fromTo.setPosition(x, y);
        fromTo.setDuration(ACTION_DURATION);

        if (swapCompleteCallback != null) {
            gemActor.addAction(sequence(fromTo, run(swapCompleteCallback)));
        } else {
            gemActor.addAction(fromTo);
        }
    }

    @Override
    public void onBackSwap(Gem fromGem, Gem toGem) {
        GemActor fromActor = gemActors.get(fromGem);
        GemActor toActor = gemActors.get(toGem);

        // the gem has ben changed in board, now the gemActor must be synchronized
        handleSwapAction(fromActor, fromGem.getX(), fromGem.getY());
        SwapCompleteCallback swapCompleteCallback = swapCompleteCallbackPool.obtain();
        swapCompleteCallback.addSwapPair(fromActor, toActor);
        swapCompleteCallback.setBackSwap(true);
        handleSwapAction(toActor, toGem.getX(), toGem.getY(), swapCompleteCallback);
    }

    @Override
    public void onClearSuccess(final Array<Gem> gems, Gem unclearedGem) {
        Gdx.app.debug(TAG, "ClearSuccess(size): " + gems.size);

        if (unclearedGem != null) {
            gemActors.get(unclearedGem).setReady();
        }

        BoardCountDownEventAction<ClearDoneEvent> clearDoneEventAction = clearDoneCountdownEventActionPool.obtain();
        clearDoneEventAction.setCount(gems.size);
        clearDoneEventAction.setTarget(group);
        group.addAction(sequence(Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        for (Gem gem : gems) {
                            final GemActor gemActor = gemActors.get(gem);
                            gemActor.block();

                            ScaleToAction scaleToAction = scaleToActionPool.obtain();
                            scaleToAction.setScale(0);
                            scaleToAction.setDuration(ACTION_DURATION);

                            ClearCompleteCallback clearCompleteCallback = clearCompleteCallbackPool.obtain();
                            clearCompleteCallback.addGemActor(gemActor);
                            gemActor.addAction(sequence(
                                    scaleToAction,
                                    Actions.run(clearCompleteCallback),
                                    fireEventPool.obtain().addEvent(clearDoneEventPool.obtain())
                            ));
                        }
                    }
                }),
                clearDoneEventAction,
                run(new Runnable() {
                    @Override
                    public void run() {
                        board.fall(gems);
                    }
                })));
    }

    @Override
    public void onClearFail(float fromX, float fromY, float toX, float toY) {
        Gdx.app.debug(TAG, "ClearFail: (" + fromX + ", " + fromY + ", " + toX + ", " + toY + ")");
        board.backSwap(fromX, fromY, toX, toY);
    }

    @Override
    public void onFall(final Array<Gem> gems) {
        Gdx.app.debug(TAG, "Fall: count: " + gems.size);

        final IntArray fallDistances = createFallDistanceOfNewGems(gems);
//        ClearDoneCountdownEventAction<FallDoneEvent> countdownEventAction = new ClearDoneCountdownEventAction<FallDoneEvent>(FallDoneEvent.class, gems.size);
//        countdownEventAction.setTarget(group);
        group.addAction(sequence(Actions.run(new Runnable() {
            @Override
            public void run() {
                for (Gem gem : gems) {
                    GemActor gemActor = gemActors.get(gem);

                    if (GemType.EMPTY.equals(gemActor.getType())) {
                        float yposAddition = gem.getY() + fallDistances.get((int) gemActor.getX());
                        gemActor.setY(yposAddition);
                        gemActor.setType(GemType.getRandom());
                        gemActor.setVisible(true);
                    }

                    MoveToAction moveToAction = moveToActionPool.obtain();
                    moveToAction.setPosition(gem.getX(), gem.getY());
                    float distanceToFall = gemActor.getY() - gem.getY();
                    moveToAction.setDuration(ACTION_DURATION * distanceToFall);

                    FallCompleteCallback fallCompleteCallback = fallCompleteCallbackPool.obtain();
                    fallCompleteCallback.addGemActor(gemActor);
                    gemActor.addAction(sequence(
                            moveToAction,
                            Actions.run(fallCompleteCallback)
//                            ,
//                            new FireEventAction(new FallDoneEvent(gemActor))
                    ));
                }
            }
        })));

        fallDistancesPool.free(fallDistances); // release fallDistances back to pool. This isn't necessery to be poolable, because every frame is used only once, but I prefer this solution.
    }

    private IntArray createFallDistanceOfNewGems(Array<Gem> gems) {
        IntArray distances = fallDistancesPool.obtain();
        for (Gem gem : gems) {
            if (GemType.EMPTY.equals(gem.getType())) {
                distances.incr((int) gem.getX(), 1);
            }
        }
        return distances;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        removeSelection();
        enableSwap();
        return true;
    }

    private void removeSelection() {
        selectedActor = null;
    }

    private void initGroup() {
        group = new Group();
        addActor(group);
        group.setTouchable(Touchable.childrenOnly);
        group.setBounds(Constants.INITIAL_X, Constants.INITIAL_Y, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
    }

    private void initBoardActor() {
        BoardActor boardActor = new BoardActor(Constants.INITIAL_X, Constants.INITIAL_Y, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        group.addActor(boardActor);
    }

    private void initGemActors() {
        Array<Gem> gems = board.getGems();

        for (Gem gem : gems) {
            GemActor gemActor = produceGemActor(gem.getX(), gem.getY());
            gemActor.setUserObject(gem);
            gemActors.put(gem, gemActor);
            group.addActor(gemActor);
        }
    }

    private GemActor produceGemActor(float x, float y) { // TODO maybe create a provider for grm actors
        return new GemActor(x, y, Constants.GEM_WIDTH, Constants.GEM_HEIGHT);
    }

    public void resize(int width, int height) {
        getViewport().update(width, height);
    }

    /**
     * Prevent continuus swaping with one swipe
     */
    private void disableGemActorSwap() {
        swapEnabled = false;
    }

    /**
     * Enable only one swap with one finger swipe.
     */
    private void enableSwap() {
        swapEnabled = true;
    }

    private boolean isSwapEnabled() {
        return swapEnabled;
    }

}
