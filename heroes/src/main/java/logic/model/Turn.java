package logic.model;

/**
 * The Turn interface.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface Turn {

    /**
     * The action type.
     */
    ActionType getActionType();

    /**
     * The action unit type.
     */
    UnitType getActiveUnit();

    /**
     * The target unit type.
     */
    UnitType getTargetUnit();

    /**
     * The active unit race.
     */
    UnitRace getActiveUnitRace();

    /**
     * The target unit race.
     */
    UnitRace getTargetUnitRace();

    /**
     * The total damage.
     */
    Integer getTotalDamage();

    /**
     * The target health.
     */
    Integer getTargetHealth();

    /**
     * The action info.
     */
    String actionInfo();
}
