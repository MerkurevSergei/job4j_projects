package logic.usecase;

import data.DataMapper;
import logic.model.*;
import services.Logger;
import services.LoggerFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The Game is usecase logic, intermediate layer between business logic and presentation.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class GameImpl implements Game {

    /**
     * The groups participating in the game.
     */
    private final List<Troop> troops;


    /**
     * Unit queue.
     */
    private final LinkedList<Unit> unitQueue;

    /**
     * The game log file.
     */
    private final String logFile;

    /**
     *
     */
    public GameImpl(String logFile) {
        this.troops = new ArrayList<>();
        this.logFile = logFile;
        this.unitQueue = new LinkedList<>();
        TroopFactory troopFactory = new DataMapper();
        troops.add(troopFactory.createTroop(WarSide.GOOD));
        troops.add(troopFactory.createTroop(WarSide.EVIL));
    }

    /**
     * Takes the next turn.
     *
     * @throws NoSuchElementException if the iteration has no more elements.
     */
    @Override
    public final Turn turn() throws Exception {
        if (!hasTurn()) {
            throw new NoSuchElementException("The game is over");
        }
        if (unitQueue.isEmpty()) {
            for (Troop troop : troops) {
                unitQueue.addAll(troop.getAllUnit());
            }
            Collections.shuffle(unitQueue);
        }
        Action action = Objects.requireNonNull(unitQueue.poll(), "if null, then logic has error").getRandomAction();
        Unit targetUnit = getTargetUnit(action);
        Turn turn = action.perform(targetUnit);
        checkUnit(targetUnit);

        try (Logger logger = new LoggerFile(logFile)) {
            logger.write(turn.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return turn;
    }

    /**
     * Check next turn in the game.
     * Returns {@code true} if the game can take the next step.
     *
     * @return {@code true} if the game can take the next step.
     */
    @Override
    public final boolean hasTurn() {
        boolean res = false;
        int liveTroop = 0;
        for (Troop troop : troops) {
            if (!troop.isEmpty()) {
                liveTroop++;
            }
            if (liveTroop > 1) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * @param action action
     * @return target unit
     */
    private Unit getTargetUnit(Action action) {
        Unit target = null; //
        for (Troop troop : troops) {
            Unit tmpTarget = troop.getRandomUnit();
            if (action.canApply(tmpTarget)) {
                target = tmpTarget;
                break;
            }
        }
        return target;
    }

    /**
     * Checked health at target unit.
     *
     * @param targetUnit target unit
     */
    private void checkUnit(Unit targetUnit) {
        if (targetUnit.getHealth() <= 0) {
            for (Troop troop : troops) {
                boolean res = troop.removeUnit(targetUnit);
                if (res) {
                    break;
                }
            }
        }
    }
}
