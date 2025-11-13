package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BuscaminasGUI extends JFrame {
    private Buscaminas juego;
    private JButton[][] botones;
    private int filas, columnas, minas;
    private JPanel panelTablero;
    private JLabel contadorLabel;
    private int banderasRestantes;
    private boolean juegoTerminado = false;

    public BuscaminasGUI() {
        setTitle("Buscaminas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        crearMenu();
        setSize(400, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Juego");
        JMenuItem nuevo = new JMenuItem("Nuevo Juego");
        nuevo.addActionListener(e -> mostrarMenuDificultad());
        menu.add(nuevo);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void mostrarMenuDificultad() {
        String[] opciones = {"Fácil (10x10, 5 minas)", "Normal (15x15, 10 minas)", "Difícil (20x20, 15 minas)"};
        int eleccion = JOptionPane.showOptionDialog(
                this,
                "Selecciona la dificultad:",
                "Dificultad",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (eleccion == JOptionPane.CLOSED_OPTION) return;

        switch (eleccion) {
            case 0 -> iniciarJuego(10, 10, 5);
            case 1 -> iniciarJuego(15, 15, 10);
            case 2 -> iniciarJuego(20, 20, 15);
        }
    }

    private void iniciarJuego(int f, int c, int m) {
        filas = f;
        columnas = c;
        minas = m;
        banderasRestantes = minas;
        juegoTerminado = false;

        juego = new Buscaminas(filas, columnas, minas);

        if (panelTablero != null)
            getContentPane().remove(panelTablero);

        panelTablero = new JPanel(new BorderLayout());

        contadorLabel = new JLabel("Banderas restantes: " + banderasRestantes, SwingConstants.CENTER);
        contadorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panelTablero.add(contadorLabel, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(filas, columnas));
        botones = new JButton[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 16));
                final int fi = i, co = j;

                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (juegoTerminado) return;

                        if (SwingUtilities.isRightMouseButton(e)) {
                            Casilla c = juego.getTablero()[fi][co];
                            if (!c.isAbierta()) {
                                if (!c.isBandera() && banderasRestantes == 0) {
                                    JOptionPane.showMessageDialog(null, "No te quedan banderas.");
                                    return;
                                }
                                juego.colocarBandera(fi, co);
                                if (c.isBandera()) banderasRestantes--;
                                else banderasRestantes++;
                                contadorLabel.setText("Banderas restantes: " + banderasRestantes);
                                actualizarBoton(fi, co);
                            }
                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            juego.revelarCasilla(fi, co);
                            actualizarTablero();
                            verificarEstado();
                        }
                    }
                });

                botones[i][j] = btn;
                panelBotones.add(btn);
            }
        }

        panelTablero.add(panelBotones, BorderLayout.CENTER);
        getContentPane().add(panelTablero, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        actualizarTablero();
    }

    private void actualizarBoton(int fila, int col) {
        JButton btn = botones[fila][col];
        Casilla c = juego.getTablero()[fila][col];
        btn.setText("");
        btn.setEnabled(!c.isAbierta());

        if (c.isAbierta()) {
            if (c.isMina()) btn.setText("*");
            else {
                int n = c.getMinasAlrededor();
                btn.setText(n == 0 ? "" : String.valueOf(n));
            }
        } else if (c.isBandera()) {
            btn.setText("B");
        } else {
            btn.setText("");
        }
    }

    private void actualizarTablero() {
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                actualizarBoton(i, j);
    }

    private void verificarEstado() {
        if (juego.isPerdio() || juego.gano()) {
            juegoTerminado = true;
            String mensaje = juego.isPerdio() ? "Perdiste" : "Ganaste";
            JOptionPane.showOptionDialog(
                this,
                mensaje,
                "Juego Terminado",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Volver a jugar"},
                "Volver a jugar"
            );
            mostrarMenuDificultad();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BuscaminasGUI::new);
    }
}


