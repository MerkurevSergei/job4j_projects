package logic.model;

import java.util.Set;

/**
 * The Action damage apply to target unit and change it.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class ActionDamage implements Action {

    /**
     * The action description.
     */
    private final String description;

    /**
     * The damage from action.
     */
    private final int damage;

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
     * @param damage      - init damage
     * @param improved    - init improved
     * @param degraded    - init degraded
     */
    public ActionDamage(String description, int damage, Set<Unit> improved, Set<Unit> degraded) {
        this.description = description;
        this.damage = damage;
        this.improved = improved;
        this.degraded = degraded;
    }

    /**
     * Apply damage action to target unit.
     *
     * @param target is final goal of the action
     * @return Turn is result apply
     */
    @Override
    public final Turn perform(Unit target) {
        int totalDamage = damage;
        if (improved.contains(target)) {
            totalDamage *= 1.5;
            improved.remove(target);
        }
        if (degraded.contains(target)) {
            totalDamage /= 2;
            degraded.remove(target);
        }
        target.setHealth(target.getHealth() - totalDamage);
        return new TurnImpl(ActionType.DAMAGE,
                owner.getType(), target.getType(),
                owner.getRace(), target.getRace(),
                totalDamage, target.getHealth(),
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
