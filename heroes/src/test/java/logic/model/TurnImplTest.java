package logic.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class TurnImplTest {

    Turn turn;

    @Before
    public void setUp() {
        turn = new TurnImpl(ActionType.DEGRADE,
                UnitType.SHAMAN, UnitType.NECROMANCER,
                UnitRace.ORCS, UnitRace.UNDEAD,
                null, 50, "test action");
    }

    @Test
    public void getActionType() {
        assertSame(ActionType.DEGRADE, turn.getActionType());
    }

    @Test
    public void getActiveUnit() {
        assertSame(UnitType.SHAMAN, turn.getActiveUnit());
    }

    @Test
    public void getTargetUnit() {
        assertSame(UnitType.NECROMANCER, turn.getTargetUnit());
    }

    @Test
    public void getActiveUnitRace() {
        assertSame(UnitRace.ORCS, turn.getActiveUnitRace());
    }

    @Test
    public void getTargetUnitRace() {
        assertSame(UnitRace.UNDEAD, turn.getTargetUnitRace());
    }

    @Test
    public void getTotalDamage() {
        assertSame(null, turn.getTotalDamage());
    }

    @Test
    public void getTargetHealth() {
        assertSame(50, turn.getTargetHealth());
    }

    @Test
    public void actionInfo() {
        assertSame("test action", turn.actionInfo());
    }
}