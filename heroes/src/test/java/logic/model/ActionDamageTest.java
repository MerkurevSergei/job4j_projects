package logic.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ActionDamageTest {

    Set<Unit> improved;
    Set<Unit> degraded;
    ActionDamage actionDamage;

    @Before
    public void setUp() {
        improved = new HashSet<>();
        degraded = new HashSet<>();
        actionDamage = new ActionDamage("action damage to", 10, improved, degraded);
    }

    @Test
    public void perform() {
        final Unit unitActive = Mockito.mock(Unit.class);
        actionDamage.setOwner(unitActive);
        final Unit unitTarget = Mockito.mock(Unit.class);
        final Turn turn = actionDamage.perform(unitTarget);
        assertNotNull(turn);
    }

    @Test
    public void canApply() {
        final Unit unitGood = Mockito.mock(Unit.class);
        Mockito.when(unitGood.getSide()).thenReturn(WarSide.GOOD);
        final Unit unitEvil = Mockito.mock(Unit.class);
        Mockito.when(unitEvil.getSide()).thenReturn(WarSide.EVIL);

        actionDamage.setOwner(unitGood);
        assertTrue(actionDamage.canApply(unitEvil));
        assertFalse(actionDamage.canApply(unitGood));

        actionDamage.setOwner(unitEvil);
        assertTrue(actionDamage.canApply(unitGood));
        assertFalse(actionDamage.canApply(unitEvil));
    }

    @Test
    public void setOwner() {
        final Unit unitGood = Mockito.mock(Unit.class);
        actionDamage.setOwner(unitGood);
        actionDamage.canApply(unitGood);
    }
}