package com.dudas.game.stage.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.StringBuilder;
import com.dudas.game.Constants;
import com.dudas.game.controller.Board;
import com.dudas.game.controller.event.BoardEvent;
import com.dudas.game.controller.event.BoardEventListener;
import com.dudas.game.controller.event.TwoGemsBoardEvent;
import com.dudas.game.model.Gem;
import com.dudas.game.stage.GameStage;
import com.dudas.game.stage.actor.GemActor;
import com.dudas.game.stage.renderer.action.BoardCountDownEventAction;
import com.dudas.game.stage.renderer.action.FireEventAction;
import com.dudas.game.stage.renderer.event.ClearDoneEvent;
import com.dudas.game.stage.renderer.event.FallDoneEvent;
import com.dudas.game.stage.renderer.poolable.ClearCompleteCallback;
import com.dudas.game.stage.renderer.poolable.FallCompleteCallback;
import org.slf4j.*;
import org.slf4j.Logger;

import java.util.Comparator;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by OLO on 13. 2. 2015
 */
public class BoardEventRenderer implements BoardEventListener {

    Logger logger = LoggerFactory.getLogger(BoardEventRenderer.class);
    public static final float ACTION_DURATION = 0.1f;

    private Board board;
    private Group boardGroup;
    private ArrayMap<Gem, GemActor> gemActors;

    private Pool<MoveToAction> moveToActionPool;
    private Pool<ScaleToAction> scaleToActionPool;
    private Pool<ClearCompleteCallback> clearCompleteCallbackPool;
    private Pool<FallCompleteCallback> fallCompleteCallbackPool;
    private Pool<FireEventAction> fireEventPool;
    private Pool<BoardCountDownEventAction> clearDoneCountdownEventActionPool;
    private Pool<ClearDoneEvent> clearDoneEventPool;
    private Pool<FallDoneEvent> fallDoneEventPool;
    private Pool<IntArray> fallDistancesPool;

    public BoardEventRenderer(Board board, Group boardGroup) {
        this.board = board;
        this.boardGroup = boardGroup;
        init();
    }

    private void init() {
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
        this.fallDoneEventPool = new Pool<FallDoneEvent>() {
            @Override
            protected FallDoneEvent newObject() {
                return new FallDoneEvent();
            }

            @Override
            public FallDoneEvent obtain() {
                FallDoneEvent obtain = super.obtain();
                obtain.setPool(fallDoneEventPool);
                return obtain;
            }
        };
        this.fallDistancesPool = new Pool<IntArray>() {
            protected IntArray newObject() {
                IntArray intArray = new IntArray(true, (int) board.getWidth());
                for (int i = 0; i < board.getWidth(); i++) {
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

        initGemActors();
    }

    private String getTag(String methodName) {
        return methodName + ": " + " DELTA: " + Gdx.graphics.getDeltaTime() + " TIME: " + TimeUtils.millis();
    }

    @Override
    public void onSwap(TwoGemsBoardEvent event) {
        logger.debug(getTag("onSwap"));
        Gem fromGem = event.getFromGem();
        GemActor fromActor = gemActors.get(fromGem);
        Gem toGem = event.getToGem();
        GemActor toActor = gemActors.get(toGem);
        handleSwapAction(fromActor, fromGem);
        handleSwapAction(toActor, toGem, event); // TODO: refactor with use of CountDownEventAction like onClearSuccess
    }

    private void handleSwapAction(final GemActor gemActor, Gem gem) {
        handleSwapAction(gemActor, gem, null);
    }

    private void handleSwapAction(final GemActor gemActor, Gem gem, final BoardEvent event) {
        MoveToAction fromTo = moveToActionPool.obtain();
        fromTo.setPosition(gem.getX(), gem.getY());
        fromTo.setDuration(0.2f);

        if (event != null) {
            gemActor.addAction(sequence(fromTo, run(new Runnable() {
                @Override
                public void run() {
                    event.complete();
                }
            })));
        } else {
            gemActor.addAction(fromTo);
        }
    }

    @Override
    public void onBackSwap(TwoGemsBoardEvent event) {
        logger.debug(getTag("onBackSwap"));
        Gem fromGem = event.getFromGem();
        GemActor fromActor = gemActors.get(fromGem);
        Gem toGem = event.getToGem();
        GemActor toActor = gemActors.get(toGem);

        // the gem has ben changed in board, now the gemActor must be synchronized
        handleSwapAction(fromActor, fromGem);
        handleSwapAction(toActor, toGem, event);
    }

    @Override
    public void onClearSuccess(final BoardEvent event) {
        logger.debug(getTag("onClearSuccess"));
        final Gem[] gems = event.getGems();
        final BoardCountDownEventAction<ClearDoneEvent> clearDoneEventAction = clearDoneCountdownEventActionPool.obtain();
        clearDoneEventAction.setCount(gems.length);
        clearDoneEventAction.setTarget(boardGroup);
        boardGroup.addAction(sequence(Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        for (Gem gem : gems) {
                            final GemActor gemActor = gemActors.get(gem);

                            logger.debug("onClear iterated gem: " + gemsToString(gem));

                            if (gemActor.getActions() != null && gemActor.getActions().size > 0) {
                                throw new RuntimeException("CLEAR CAN'T OVERLAP EXISTING ACTIONS");
                            }

                            ScaleToAction scaleToAction1 = scaleToActionPool.obtain();
                            scaleToAction1.setScale(1.2f);
                            scaleToAction1.setDuration(0.1f);

                            ScaleToAction scaleToAction2 = scaleToActionPool.obtain();
                            scaleToAction2.setScale(0);
                            scaleToAction2.setDuration(0.4f);

                            ClearCompleteCallback clearCompleteCallback = clearCompleteCallbackPool.obtain();
                            clearCompleteCallback.addGemActor(gemActor);

                            ClearDoneEvent clearDoneEvent = clearDoneEventPool.obtain();
                            clearDoneEvent.setTarget(gemActor);
                            clearDoneEventAction.addEvent(clearDoneEvent);

                            gemActor.addAction(
                                    sequence(
                                            scaleToAction1,
                                            scaleToAction2,
                                            Actions.run(clearCompleteCallback),
                                            fireEventPool.obtain().addEvent(clearDoneEvent)
                                    ));
                        }
                    }
                }),
                clearDoneEventAction,
                run(new Runnable() {
                    @Override
                    public void run() {
                        event.complete();
                    }
                })));
    }

    @Override
    public void onClearFail(BoardEvent event) {
        event.complete();
    }

    @Override
    public void onFall(final BoardEvent event) {
        logger.debug(getTag("onFall"));
        final Array<Gem> gems = new Array<Gem>(event.getGems());
        gems.sort(new Comparator<Gem>() {
            @Override
            public int compare(Gem o1, Gem o2) {
                return (int) o1.getY() - (int) o2.getY();
            }
        });

        final IntArray fallDistances = createFallDistanceOfNewGems(gems);
        final IntArray fallDelay = fallDistancesPool.obtain();
        final BoardCountDownEventAction<FallDoneEvent> fallDoneEventAction = new BoardCountDownEventAction<FallDoneEvent>(FallDoneEvent.class);
        fallDoneEventAction.setCount(gems.size);
        fallDoneEventAction.setTarget(boardGroup);
        boardGroup.addAction(sequence(Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        for (Gem gem : gems) {
                            GemActor gemActor = gemActors.get(gem);

                            logger.debug("onFall iterated gem: " + gemsToString(gem));

                            Array<Action> gemActorActions = gemActor.getActions();
                            if (gemActorActions != null && gemActorActions.size > 0) {
                                logger.debug("TOO MANY ACTIONS: Actions size: " + gemActorActions.size + "\n" +
                                        "Gems size: " + gems.size + "\n" +
                                        "Actions: " + gemActorActions.toString(" --- "));

                                debugFallSequence((SequenceAction) gemActorActions.get(0));

                                gemActor.clearActions();
                            }

                            if (gem.isNew()) {
                                float yposAddition = gem.getY() + fallDistances.get((int) gemActor.getX());
                                gemActor.setY(yposAddition);
                                gemActor.setVisible(true);
                            }

                            int delay = fallDelay.get((int) gem.getX());
                            fallDelay.incr((int) gem.getX(), 1);
                            DelayAction delayAction = new DelayAction();  // TODO: create pool for delayAction
                            delayAction.setDuration(0.06f * delay);

                            MoveToAction moveToAction = moveToActionPool.obtain();
                            moveToAction.setPosition(gem.getX(), gem.getY() - 0.2f);
                            float distanceToFall = gemActor.getY() - gem.getY();
                            float moveToDuration = ACTION_DURATION * distanceToFall;
                            if (distanceToFall == 1) {
                                moveToDuration = moveToDuration * 1.5f;
                            }
                            moveToAction.setDuration(moveToDuration);

                            MoveToAction fallbackAction = moveToActionPool.obtain();
                            fallbackAction.setPosition(gem.getX(), gem.getY());
                            fallbackAction.setDuration(0.08f);

                            FallCompleteCallback fallCompleteCallback = fallCompleteCallbackPool.obtain();
                            fallCompleteCallback.addGemActor(gemActor);

                            FallDoneEvent fallDoneEvent = fallDoneEventPool.obtain();
                            fallDoneEvent.setTarget(gemActor);
                            fallDoneEventAction.addEvent(fallDoneEvent);

                            gemActor.addAction(sequence(
                                    delayAction,
                                    moveToAction,
                                    fallbackAction,
                                    Actions.run(fallCompleteCallback),
                                    fireEventPool.obtain().addEvent(fallDoneEvent)
                            ));
                        }
                    }
                }),
                fallDoneEventAction,
                run(new Runnable() {
                    @Override
                    public void run() {
                        event.complete();
                    }
                })));

        fallDistancesPool.free(fallDistances); // release fallDistances back to pool. This isn't necessery to be poolable, because every frame is used only once, but I prefer this solution.
        fallDistancesPool.free(fallDelay);
    }

    private void debugFallSequence(SequenceAction sequenceAction) {
        GemActor actor = (GemActor) sequenceAction.getTarget();
        Gem gem = (Gem) actor.getUserObject();

        com.badlogic.gdx.utils.StringBuilder builder = new StringBuilder("");

        builder.append("\n\nGemActor(" + actor.getX() + ", " + actor.getY() + ") --- Gem(" + gem.getX() + ", " + gem.getY() + ", " + gem.getType().name() + ")");

        Array<Action> actions = sequenceAction.getActions();
        for (Action action : actions) {
            if (action instanceof MoveToAction) {
                MoveToAction moveToAction = (MoveToAction) action;
                builder.append("" +
                        "\n\n" +
                        "Action name: " + moveToAction.toString() + "(" + moveToAction.getX() + ", " + moveToAction.getY() + ")");
            }
        }

        logger.debug("SEQUENCE DEBUG: " + builder.toString());
    }

    private IntArray createFallDistanceOfNewGems(Array<Gem> gems) {
        IntArray distances = fallDistancesPool.obtain();
        for (Gem gem : gems) {
            if (gem.isNew()) {
                distances.incr((int) gem.getX(), 1);
            }
        }
        return distances;
    }

    private void initGemActors() {
        Array<Gem> gems = board.getGems();

        for (Gem gem : gems) {
            GemActor gemActor = produceGemActor(gem.getX(), gem.getY());
            gemActor.setUserObject(gem);
            gemActors.put(gem, gemActor);
            boardGroup.addActor(gemActor);
        }
    }

    private GemActor produceGemActor(float x, float y) { // TODO maybe create a provider for grm actors
        return new GemActor(x, y, Constants.GEM_WIDTH, Constants.GEM_HEIGHT);
    }

    private String gemsToString(Gem... gems) {
        java.lang.StringBuilder builder = new java.lang.StringBuilder(" - [");
        for (Gem gem : gems) {
            builder.append(gem.getId()).append(",");
        }
        builder.append("]");
        return builder.toString();
    }
}
