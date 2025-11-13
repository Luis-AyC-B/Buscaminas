package src;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CasillaTest {
    private Casilla casilla;

    @Before
    public void setUp() {
        casilla = new Casilla(1, 2);
    }

    @Test
    public void testInicializacionCorrecta() {
        assertEquals(1, casilla.getFila());
        assertEquals(2, casilla.getColumna());
        assertFalse(casilla.isMina());
        assertFalse(casilla.isAbierta());
        assertFalse(casilla.isBandera());
        assertEquals(0, casilla.getMinasAlrededor());
    }

    @Test
    public void testSetYGetMina() {
        casilla.setMina(true);
        assertTrue(casilla.isMina());
        casilla.setMina(false);
        assertFalse(casilla.isMina());
    }

    @Test
    public void testIncrementarMinasAlrededor() {
        assertEquals(0, casilla.getMinasAlrededor());
        casilla.incrementarMinasAlrededor();
        assertEquals(1, casilla.getMinasAlrededor());
        casilla.incrementarMinasAlrededor();
        assertEquals(2, casilla.getMinasAlrededor());
    }

    @Test
    public void testSetYGetAbierta() {
        assertFalse(casilla.isAbierta());
        casilla.setAbierta(true);
        assertTrue(casilla.isAbierta());
    }

    @Test
    public void testSetYGetBandera() {
        assertFalse(casilla.isBandera());
        casilla.setBandera(true);
        assertTrue(casilla.isBandera());
        casilla.setBandera(false);
        assertFalse(casilla.isBandera());
    }
}
