package principal;

import java.awt.EventQueue;

/**
 * Clase main que se ejecutará al iniciar el programa
 * Su única función el llamar e inicializar la ventana donde se mostrará todo
 * 
 * @author Sergio Blanco Prieto
 */
public class Arkanoid2D {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
                    ventanaPrincipal.inicializar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
