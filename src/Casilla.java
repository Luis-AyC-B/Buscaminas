package src;

public class Casilla {
    private int fila;
    private int columna;
    private boolean mina;
    private int minasAlrededor;
    private boolean abierta;

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.mina = false;
        this.minasAlrededor = 0;
        this.abierta = false;
    }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }
    public boolean isMina() { return mina; }
    public void setMina(boolean mina) { this.mina = mina; }
    public int getMinasAlrededor() { return minasAlrededor; }
    public void incrementarMinasAlrededor() { this.minasAlrededor++; }
    public boolean isAbierta() { return abierta; }
    public void setAbierta(boolean abierta) { this.abierta = abierta; }
}


