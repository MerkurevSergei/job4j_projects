package logic.model;

import java.util.List;
import java.util.Random;

/**
 * The Troop interface implementation.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class TroopImpl implements Troop {

    /**
     * Consist troop units
     */
    private final List<Unit> units;

    /**
     * @param units init
     */
    public TroopImpl(List<Unit> units) {
        this.units = units;
    }

    /**
     * @return {@code true} if Troop is empty
     */
    @Override
    public final boolean isEmpty() {
        return units.isEmpty();
    }

    /**
     * @return random Unit
     */
    @Override
    public final Unit getRandomUnit() {
        Random r = new Random();
        return units.get(r.nextInt(units.size()));
    }

    /**
     * @return all unit
     */
    @Override
    public final List<Unit> getAllUnit() {
        return units;
    }

    /**
     * @param unit removed unit
     * @return {@code true} if remove success
     */
    @Override
    public final boolean removeUnit(Unit unit) {
        return units.remove(unit);
    }
}
