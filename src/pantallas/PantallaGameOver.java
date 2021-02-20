package pantallas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import principal.PanelJuego;
import principal.Sprite;
import java.awt.Color;
import java.awt.RenderingHints;
import principal.Pantalla;

/**
 * Esta clase crea la pantalla de game over y administra todos sus componentes.
 * 
 * @author Sergio Blanco Prieto
 */
public class PantallaGameOver implements Pantalla {
    //Referencia al panel de juego que contiene las pantallas
    private PanelJuego juego;

    /**
     * Constructor de la clase que asigna la referencia al panel de juego
     * 
     * @param juego : referencia al panel de juego
     */
    public PantallaGameOver(PanelJuego juego) {
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
     * Método que crea y pinta todos los componentes de la pantalla de game over en sus respectivas posiciones
     */
    @Override
    public void PintarPantalla(Graphics g) {
        //Primero se crea un sprite con la imagen de game over
        Sprite fondo = new Sprite(300, juego.getWidth(), 0, 100, "imagenes/gameover.png", juego);
        //Después se crea y pinta un fondo negro
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, juego.getWidth(), juego.getHeight());
        juego.pintarFondoAColor(g);
        //Tras esto se pinta la imagen de game over sobre dicho fondo
        fondo.pintarEnMundo(g);
        //Por último se pinta la puntuación obtenida en la partida
        if (g instanceof Graphics2D) {
            g.setColor(Color.WHITE);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.drawString("PUNTUACIÓN: " + juego.getPuntuacion(), 150, 450);
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
