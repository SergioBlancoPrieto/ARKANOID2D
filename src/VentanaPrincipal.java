import javax.swing.JFrame;
import java.awt.GridLayout;

public class VentanaPrincipal {
    JFrame ventana;
    PanelJuego panelJuego;

    public VentanaPrincipal() {
        ventana = new JFrame("ARKANOID 2D");
        ventana.setBounds(400, 50, 400, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
    }

    public void inicializarComponentes() {
        ventana.setLayout(new GridLayout(1, 1));
        panelJuego = new PanelJuego(ventana);
        ventana.add(panelJuego);
        panelJuego.setFocusable(true);
    }

    public void inicializarListeners() {
        //Nada
    }

    public void inicializar() {
        ventana.setVisible(true);
        inicializarComponentes();
        inicializarListeners();
    }
}
