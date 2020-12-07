package logic.model;

import java.util.Set;

/**
 * The Action degrade apply to target unit and change it.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class ActionDegrade implements Action {

    /**
     * The action description.
     */
    private final String description;

    /**
     * Improved game group.
     */
    private final Set<Unit> improved;

    /**
     * Degraded game group.
     */
    private final Set<Unit> degraded;

    /**
     * The owner of the action.
     */
    private Unit owner;

    /**
     * @param description - init description
     * @param improved    - init improved
     * @param degraded    - init degraded
     */
    public ActionDegrade(String description, Set<Unit> improved, Set<Unit> degraded) {
        this.description = description;
        this.improved = improved;
        this.degraded = degraded;
    }

    /**
     * Apply degrade action to target unit.
     *
     * @param target is final goal of the action
     * @return Turn is result apply
     */
    @Override
    public final Turn perform(Unit target) {
        improved.remove(target);
        degraded.add(target);
        return new TurnImpl(ActionType.DEGRADE,
                owner.getType(), target.getType(),
                owner.getRace(), target.getRace(),
                0, target.getHealth(),
                description);
    }

    /**
     * Checks whether the action can be applied.
     *
     * @param target is checked goal
     * @return {@code true} if can be apply
     */
    @Override
    public final boolean canApply(Unit target) {
        return owner.getSide() != target.getSide();
    }

    /**
     * Set action owner
     *
     * @param owner is action owner
     */
    @Override
    public void setOwner(Unit owner) {
        this.owner = owner;
    }
}
