package logic.model;

/**
 * Unit interface.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface Unit {
    /**
     * @return current unit health.
     */
    int getHealth();

    /**
     * @param health is new unit health.
     */
    void setHealth(int health);

    /**
     * @return random unit action.
     */
    Action getRandomAction();

    /**
     * @return the unit type.
     */
    UnitType getType();

    /**
     * @return the unit race.
     */
    UnitRace getRace();

    /**
     * @return the war side
     */
    WarSide getSide();
}
