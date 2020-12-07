package logic.model;

/**
 * The Action apply to target unit and change it.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface Action {
    /**
     * Apply action to target unit.
     *
     * @param target is final goal of the action
     * @return Turn is result apply
     */
    Turn perform(Unit target);

    /**
     * Checks whether the action can be applied.
     *
     * @param target is checked goal
     * @return {@code true} if can be apply
     */
    boolean canApply(Unit target);

    /**
     * Set action owner
     *
     * @param owner is action owner
     */
    void setOwner(Unit owner);
}
