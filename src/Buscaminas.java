package src;

import java.util.LinkedList;
import java.util.List;

public class Buscaminas {
    private Casilla[][] tablero;
    private int filas, columnas, minas;
    private boolean minasGeneradas = false;

    public Buscaminas(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
        inicializarTablero();
    }

    private void inicializarTablero() {
        tablero = new Casilla[filas][columnas];
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                tablero[i][j] = new Casilla(i, j);
    }

    public Casilla[][] getTablero() {
        return tablero;
    }

    private void generarMinas(int filaIgnorar, int colIgnorar) {
        int generadas = 0;
        while (generadas < minas) {
            int f = (int)(Math.random() * filas);
            int c = (int)(Math.random() * columnas);
            if ((f == filaIgnorar && c == colIgnorar) || tablero[f][c].isMina())
                continue;
            tablero[f][c].setMina(true);
            generadas++;
        }
        actualizarMinasAlrededor();
        minasGeneradas = true;
    }

    private void actualizarMinasAlrededor() {
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                if (tablero[i][j].isMina()) {
                    for (Casilla c : obtenerAlrededor(i,j))
                        c.incrementarMinasAlrededor();
                }
    }

    private List<Casilla> obtenerAlrededor(int fila, int col) {
        List<Casilla> lista = new LinkedList<>();
        for (int i = fila-1; i <= fila+1; i++)
            for (int j = col-1; j <= col+1; j++)
                if (i>=0 && i<filas && j>=0 && j<columnas && !(i==fila && j==col))
                    lista.add(tablero[i][j]);
        return lista;
    }

    public void revelarCasilla(int fila, int col) {
        Casilla c = tablero[fila][col];
        if (!minasGeneradas) generarMinas(fila,col);
        if (c.isAbierta()) return;
        c.setAbierta(true);
        if (c.isMina()) return;
        if (c.getMinasAlrededor() == 0) {
            for (Casilla ady : obtenerAlrededor(fila,col))
                revelarCasilla(ady.getFila(), ady.getColumna());
        }
    }
}
