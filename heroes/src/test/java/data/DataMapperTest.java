package data;

import logic.model.Troop;
import logic.model.Unit;
import logic.model.WarSide;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class DataMapperTest {

    @Test
    public void whenGetImproved() {
        final DataMapper dataMapper = new DataMapper();
        final Set<Unit> improved = dataMapper.getImproved();
        assertNotNull(improved);
    }

    @Test
    public void whenGetDegraded() {
        final DataMapper dataMapper = new DataMapper();
        final Set<Unit> degraded = dataMapper.getDegraded();
        assertNotNull(degraded);
    }

    @Test
    public void createTroop() {
        final DataMapper dataMapper = new DataMapper();
        final Troop troopEvil = dataMapper.createTroop(WarSide.EVIL);
        final Troop troopGood = dataMapper.createTroop(WarSide.GOOD);
        assertSame(troopEvil.getRandomUnit().getSide(), WarSide.EVIL);
        assertSame(troopGood.getRandomUnit().getSide(), WarSide.GOOD);
    }
}