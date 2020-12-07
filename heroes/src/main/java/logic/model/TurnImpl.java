package logic.model;

/**
 * The Turn implementation.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class TurnImpl implements Turn {

    /**
     * The action type.
     */
    private final ActionType actionType;

    /**
     * The action unit type.
     */
    private final UnitType action;

    /**
     * The target unit type.
     */
    private final UnitType target;

    /**
     * The active unit race.
     */
    private final UnitRace activeRace;

    /**
     * The target unit race.
     */
    private final UnitRace targetRace;

    /**
     * The total damage.
     */
    private final Integer totalDamage;

    /**
     * The target health.
     */
    private final Integer targetHealth;

    /**
     * The action info.
     */
    private final String actionInfo;

    /**
     * @param actionType   init
     * @param action       init
     * @param target       init
     * @param activeRace   init
     * @param targetRace   init
     * @param totalDamage  init
     * @param targetHealth init
     * @param actionInfo   init
     */
    //CHECKSTYLE:OFF
    public TurnImpl(ActionType actionType,
                    UnitType action, UnitType target,
                    UnitRace activeRace, UnitRace targetRace,
                    Integer totalDamage, Integer targetHealth,
                    String actionInfo) {
        this.actionType = actionType;
        this.action = action;
        this.target = target;
        this.activeRace = activeRace;
        this.targetRace = targetRace;
        this.totalDamage = totalDamage;
        this.targetHealth = targetHealth;
        this.actionInfo = actionInfo;
    }
    //CHECKSTYLE:ON

    /**
     * @return action type
     */
    @Override
    public final ActionType getActionType() {
        return actionType;
    }

    /**
     * @return active unit type
     */
    @Override
    public final UnitType getActiveUnit() {
        return action;
    }

    /**
     * @return target unit type
     */
    @Override
    public final UnitType getTargetUnit() {
        return target;
    }

    /**
     * @return active unit race
     */
    @Override
    public final UnitRace getActiveUnitRace() {
        return activeRace;
    }

    /**
     * @return target unit race
     */
    @Override
    public final UnitRace getTargetUnitRace() {
        return targetRace;
    }

    /**
     * @return total damage
     */
    @Override
    public final Integer getTotalDamage() {
        return totalDamage;
    }

    /**
     * @return target health
     */
    @Override
    public Integer getTargetHealth() {
        return targetHealth;
    }

    /**
     * @return info about action
     */
    @Override
    public final String actionInfo() {
        return actionInfo;
    }
}
