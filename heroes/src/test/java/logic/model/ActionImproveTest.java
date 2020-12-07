package logic.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ActionImproveTest {

    Set<Unit> improved;
    Set<Unit> degraded;
    ActionImprove actionImprove;

    @Before
    public void setUp() {
        improved = new HashSet<>();
        degraded = new HashSet<>();
        actionImprove = new ActionImprove("action degrade to", improved, degraded);
    }

    @Test
    public void perform() {
        final Unit unitActive = Mockito.mock(Unit.class);
        actionImprove.setOwner(unitActive);
        final Unit unitTarget = Mockito.mock(Unit.class);
        final Turn turn = actionImprove.perform(unitTarget);
        assertNotNull(turn);
    }

    @Test
    public void canApply() {
        final Unit unitGood = Mockito.mock(Unit.class);
        Mockito.when(unitGood.getSide()).thenReturn(WarSide.GOOD);
        final Unit unitEvil = Mockito.mock(Unit.class);
        Mockito.when(unitEvil.getSide()).thenReturn(WarSide.EVIL);

        actionImprove.setOwner(unitGood);
        assertFalse(actionImprove.canApply(unitEvil));
        assertTrue(actionImprove.canApply(unitGood));

        actionImprove.setOwner(unitEvil);
        assertFalse(actionImprove.canApply(unitGood));
        assertTrue(actionImprove.canApply(unitEvil));
    }

    @Test
    public void setOwner() {
        final Unit unitGood = Mockito.mock(Unit.class);
        actionImprove.setOwner(unitGood);
        actionImprove.canApply(unitGood);
    }
}