package logic.model;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class TroopImplTest {

    @Test
    public void whenIsEmptyTrue() {
        List<Unit> units = new ArrayList<>();
        assertTrue(new TroopImpl(units).isEmpty());
    }

    @Test
    public void whenIsEmptyFalse() {
        final Unit unit = Mockito.mock(Unit.class);
        final Unit unit2 = Mockito.mock(Unit.class);
        List<Unit> units = List.of(unit, unit2);
        assertFalse(new TroopImpl(units).isEmpty());
    }

    @Test
    public void whenGetRandomUnit() {
        final Unit unit = Mockito.mock(Unit.class);
        final Unit unit2 = Mockito.mock(Unit.class);
        List<Unit> units = List.of(unit, unit2);
        final TroopImpl troop = new TroopImpl(units);
        assertNotNull(troop.getRandomUnit());
        assertThat(troop.getRandomUnit(), instanceOf(Unit.class));
    }

    @Test
    public void getAllUnit() {
        final Unit unit = Mockito.mock(Unit.class);
        final Unit unit2 = Mockito.mock(Unit.class);
        List<Unit> units = List.of(unit, unit2);
        final TroopImpl troop = new TroopImpl(units);
        assertNotNull(troop.getAllUnit());
        assertThat(troop.getAllUnit(), instanceOf(List.class));
    }

    @Test
    public void removeUnit() {
        final Unit unit = Mockito.mock(Unit.class);
        List<Unit> units = new ArrayList<>();
        units.add(unit);
        final TroopImpl troop = new TroopImpl(units);
        assertTrue(troop.removeUnit(unit));
        assertFalse(troop.removeUnit(unit));
    }
}