package logic.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ActionDegradeTest {

    Set<Unit> improved;
    Set<Unit> degraded;
    ActionDegrade actionDegrade;

    @Before
    public void setUp() {
        improved = new HashSet<>();
        degraded = new HashSet<>();
        actionDegrade = new ActionDegrade("action degrade to", improved, degraded);
    }

    @Test
    public void perform() {
        final Unit unitActive = Mockito.mock(Unit.class);
        actionDegrade.setOwner(unitActive);
        final Unit unitTarget = Mockito.mock(Unit.class);
        final Turn turn = actionDegrade.perform(unitTarget);
        assertNotNull(turn);
    }

    @Test
    public void canApply() {
        final Unit unitGood = Mockito.mock(Unit.class);
        Mockito.when(unitGood.getSide()).thenReturn(WarSide.GOOD);
        final Unit unitEvil = Mockito.mock(Unit.class);
        Mockito.when(unitEvil.getSide()).thenReturn(WarSide.EVIL);

        actionDegrade.setOwner(unitGood);
        assertTrue(actionDegrade.canApply(unitEvil));
        assertFalse(actionDegrade.canApply(unitGood));

        actionDegrade.setOwner(unitEvil);
        assertTrue(actionDegrade.canApply(unitGood));
        assertFalse(actionDegrade.canApply(unitEvil));
    }

    @Test
    public void setOwner() {
        final Unit unitGood = Mockito.mock(Unit.class);
        actionDegrade.setOwner(unitGood);
        actionDegrade.canApply(unitGood);
    }
}