package src;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class BuscaminasTest {
    private Buscaminas juego;

    @Before
    public void setUp() {
        juego = new Buscaminas(5, 5, 3); 
    }

    @Test
    public void testInicializacionCorrecta() {
        assertNotNull(juego.getTablero());
        assertFalse(juego.isPerdio());
    }

    @Test
    public void testGenerarMinasNoColocaEnPrimeraCasilla() {
        juego.revelarCasilla(0, 0);
        assertFalse(juego.getTablero()[0][0].isMina());
    }

    @Test
    public void testActualizarMinasAlrededor() {
        juego.getTablero()[1][1].setMina(true);
        juego.actualizarMinasAlrededor();
        assertTrue(juego.getTablero()[0][0].getMinasAlrededor() > 0);
    }

    @Test
    public void testRevelarCasillaSegura() {
        juego.getTablero()[0][0].setMina(false);
        juego.actualizarMinasAlrededor();
        juego.revelarCasilla(0, 0);
        assertTrue(juego.getTablero()[0][0].isAbierta());
        assertFalse(juego.isPerdio());
    }

    @Test
    public void testRevelarCasillaConMinaPierde() {
        juego.getTablero()[2][2].setMina(true);
        juego.actualizarMinasAlrededor();
        juego.revelarCasilla(2, 2);
        assertTrue(juego.isPerdio());
    }

    @Test
    public void testColocarYQuitarBandera() {
        juego.colocarBandera(1, 1);
        assertTrue(juego.getTablero()[1][1].isBandera());
        juego.colocarBandera(1, 1);
        assertFalse(juego.getTablero()[1][1].isBandera());
    }

    @Test
    public void testGanarJuegoSinMinas() {
        Buscaminas sinMinas = new Buscaminas(2, 2, 0);
        sinMinas.revelarCasilla(0, 0);
        sinMinas.revelarCasilla(0, 1);
        sinMinas.revelarCasilla(1, 0);
        sinMinas.revelarCasilla(1, 1);
        assertTrue(sinMinas.gano());
    }

    @Test
    public void testObtenerCasillasConMinas() {
        juego.getTablero()[0][1].setMina(true);
        juego.getTablero()[1][1].setMina(true);
        List<Casilla> lista = juego.obtenerCasillasConMinas();
        assertEquals(2, lista.size());
    }

    @Test
    public void testNoRevelaCasillaConBandera() {
        juego.getTablero()[3][3].setBandera(true);
        juego.revelarCasilla(3, 3);
        assertFalse(juego.getTablero()[3][3].isAbierta());
    }
}
