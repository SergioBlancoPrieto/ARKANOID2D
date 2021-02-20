package principal;

import javax.swing.JFrame;
import java.awt.GridLayout;

/**
 * Clase que crea e inicializa la ventana donde se incluirá el panle de juego
 * 
 * @author Sergio Blanco Prieto
 */
public class VentanaPrincipal {
    //Ventana que contendrá el panel de juego
    JFrame ventana;
    //Panel de juego donde se cargarán las pantallas del juego
    PanelJuego panelJuego;

    /**
     * Método que crea y posiciona la ventana
     */
    public VentanaPrincipal() {
        ventana = new JFrame("ARKANOID 2D");
        ventana.setBounds(400, 50, 400, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
    }

    /**
     * Método que crea el panel de juego y lo añade a la ventana
     */
    public void inicializarComponentes() {
        ventana.setLayout(new GridLayout(1, 1));
        panelJuego = new PanelJuego(ventana);
        ventana.add(panelJuego);
        panelJuego.setFocusable(true);
    }

    /**
     * Método que inicializa la ventana
     */
    public void inicializar() {
        ventana.setVisible(true);
        inicializarComponentes();
    }
}
