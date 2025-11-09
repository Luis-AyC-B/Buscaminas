package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BuscaminasGUI extends JFrame {
    private Buscaminas juego;
    private JButton[][] botones;
    private int filas, columnas, minas;
    private JPanel panelTablero;

    public BuscaminasGUI() {
        setTitle("Buscaminas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        crearMenu();
        setVisible(true);
        nuevoJuego();
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Nuevo Juego");
        JMenuItem nuevo = new JMenuItem("Nuevo Juego");
        nuevo.addActionListener(e -> nuevoJuego());
        menu.add(nuevo);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void nuevoJuego() {
        String input = JOptionPane.showInputDialog(
            this, "Ingrese Filas, Columnas y Minas separados por comas (ej: 8,8,10):");
        if (input == null) return;
        try {
            String[] partes = input.split(",");
            filas = Integer.parseInt(partes[0].trim());
            columnas = Integer.parseInt(partes[1].trim());
            minas = Integer.parseInt(partes[2].trim());
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,"Valores inválidos");
            return;
        }

        juego = new Buscaminas(filas,columnas,minas);

        if (panelTablero != null) getContentPane().remove(panelTablero);

        panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(filas, columnas));
        botones = new JButton[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton btn = new JButton();
                final int fi = i, co = j;
                btn.setFont(new Font("Arial", Font.BOLD, 16));
                btn.addActionListener(e -> {
                    juego.revelarCasilla(fi, co);
                    actualizarTablero();
                });
                botones[i][j] = btn;
                panelTablero.add(btn);
            }
        }

        getContentPane().add(panelTablero, BorderLayout.CENTER);
        pack();
        actualizarTablero();
    }

    private void actualizarBoton(int fila, int col) {
        JButton btn = botones[fila][col];
        Casilla c = juego.getTablero()[fila][col];

        if (c.isAbierta()) {
            btn.setEnabled(false);
            if (c.isMina()) btn.setText("*");
            else btn.setText(c.getMinasAlrededor() == 0 ? "" : String.valueOf(c.getMinasAlrededor()));
        } else {
            btn.setEnabled(true);
            btn.setText("");
        }
    }

    private void actualizarTablero() {
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                actualizarBoton(i,j);
    }

    public static void main(String[] args) {
        new BuscaminasGUI();
    }
}



