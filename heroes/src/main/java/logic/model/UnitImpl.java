package logic.model;

import java.util.List;
import java.util.Random;

/**
 * Implement Unit interface.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class UnitImpl implements Unit {

    /**
     * The unit race.
     */
    private final UnitRace race;

    /**
     * The unit type.
     */
    private final UnitType type;

    /**
     * The war side.
     */
    private final WarSide side;

    /**
     * Consist actions available unit.
     */
    private final List<Action> actions;

    /**
     * The player's health.
     */
    private int health = 100;

    /**
     * @param actions - init actions.
     */
    public UnitImpl(UnitRace race, UnitType type, WarSide side, List<Action> actions) {
        this.race = race;
        this.type = type;
        this.side = side;
        for (Action action : actions) {
            action.setOwner(this);
        }
        this.actions = actions;
    }

    /**
     * @return current unit health.
     */
    @Override
    public final int getHealth() {
        return health;
    }

    /**
     * @param health is new unit health.
     */
    @Override
    public final void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return random unit action.
     */
    @Override
    public final Action getRandomAction() {
        Random r = new Random();
        return actions.get(r.nextInt(actions.size()));
    }

    /**
     * @return the unit type.
     */
    @Override
    public final UnitType getType() {
        return type;
    }

    /**
     * @return the unit race.
     */
    @Override
    public final UnitRace getRace() {
        return race;
    }

    /**
     * @return the war race.
     */
    @Override
    public final WarSide getSide() {
        return side;
    }
}
