package logic.model;

import java.util.List;

/**
 * The Troop interface.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface Troop {

    /**
     * @return {@code true} if Troop is empty
     */
    boolean isEmpty();

    /**
     * @return random Unit
     */
    Unit getRandomUnit();

    /**
     * @return all unit
     */
    List<Unit> getAllUnit();

    /**
     * @param unit removed unit
     * @return {@code true} if remove success
     */
    boolean removeUnit(Unit unit);
}
