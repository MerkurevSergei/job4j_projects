package presentation;

import logic.model.*;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ViewImplTest {

    @Test
    public void show() {
        final PrintStream out = System.out;
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);

        Turn mock = Mockito.mock(Turn.class);
        Mockito.when(mock.getActionType()).thenReturn(ActionType.DEGRADE);
        Mockito.when(mock.getActiveUnit()).thenReturn(UnitType.SHAMAN);
        Mockito.when(mock.getTargetUnit()).thenReturn(UnitType.NECROMANCER);
        Mockito.when(mock.getActiveUnitRace()).thenReturn(UnitRace.ORCS);
        Mockito.when(mock.getTargetUnitRace()).thenReturn(UnitRace.UNDEAD);
        Mockito.when(mock.getTotalDamage()).thenReturn(null);
        Mockito.when(mock.getTargetHealth()).thenReturn(50);
        Mockito.when(mock.actionInfo()).thenReturn("test action");

        new ViewImpl().show(mock);

        System.setOut(out);

        assertEquals("ORCS vs UNDEAD. SHAMAN test action NECROMANCER. remain health: 50" + System.lineSeparator(), byteArrayOutputStream.toString());
    }
}