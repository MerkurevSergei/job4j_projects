package logic.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class UnitImplTest {

    Unit unit;

    @Before
    public void setUp() {
        Action actionMock = Mockito.mock(Action.class);
        final List<Action> actions = List.of(actionMock);
        unit = new UnitImpl(UnitRace.UNDEAD, UnitType.NECROMANCER, WarSide.EVIL, actions);
    }

    @Test
    public void getHealth() {
        assertSame(100, unit.getHealth());
    }

    @Test
    public void setHealth() {
        unit.setHealth(50);
        assertSame(50, unit.getHealth());
    }

    @Test
    public void getRandomAction() {
        assertNotNull(unit.getRandomAction());
    }

    @Test
    public void getType() {
        assertSame(UnitType.NECROMANCER, unit.getType());
    }

    @Test
    public void getRace() {
        assertSame(UnitRace.UNDEAD, unit.getRace());
    }

    @Test
    public void getSide() {
        assertSame(WarSide.EVIL, unit.getSide());
    }


}