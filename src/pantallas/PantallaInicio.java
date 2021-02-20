package pantallas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import principal.PanelJuego;
import principal.Pantalla;
import principal.Sprite;
import java.awt.Color;
import java.awt.RenderingHints;

/**
 * Esta clase crea la pantalla de inicio y administra todos sus componentes.
 * 
 * @author Sergio Blanco Prieto
 */
public class PantallaInicio implements Pantalla {
    //Referencia al panel de juego que contiene las pantallas
    private PanelJuego juego;

    /**
     * Constructor de la clase que asigna la referencia al panel de juego
     * 
     * @param juego : referencia al panel de juego
     */
    public PantallaInicio(PanelJuego juego) {
        this.juego = juego;
    }

    /**
     * Método que inicializa los componentes
     * En este caso no hay componentes que inicializar
     */
    @Override
    public void InicializarPantalla() {
        //Nada
    }

    /**
     * Método que crea y pinta todos los componentes de la pantalla de inicio en sus respectivas posiciones
     */
    @Override
    public void PintarPantalla(Graphics g) {
        //Primero se crea un sprite con la imagen de fondo y se pinta en la pantalla
        Sprite fondo = new Sprite(juego.getHeight(), juego.getWidth(), 0, 0, "imagenes/pantallatitulo.png", juego);
        fondo.pintarEnMundo(g);
        //Por último se pinta un mensaje de "Pulsa para empezar"
        if (g instanceof Graphics2D) {
            g.setColor(Color.WHITE);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.drawString("PULSAR PARA EMPEZAR", 130, 500);
        g.dispose();
    }

    /**
     * Método que se ejecutará cada vez que se refresque la pantalla en el panel de juego
     */
    @Override
    public void EjecutarFrame() {
        //Nada
    }

    /**
     * Método que pondrá la bola en marcha
     */
    @Override
    public void ArrancarBola() {
        //Nada
    }
}
