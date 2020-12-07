package logic.model;

import java.util.Set;

/**
 * The Troop factory.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface TroopFactory {

    /**
     * @return improved group.
     */
    Set<Unit> getImproved();

    /**
     * @return degraded group.
     */
    Set<Unit> getDegraded();

    /**
     * @param type side type
     * @return Troop
     */
    Troop createTroop(WarSide type);
}
