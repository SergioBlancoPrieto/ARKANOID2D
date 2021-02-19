package pantallas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import principal.PanelJuego;
import principal.Sprite;
import java.awt.Color;
import java.awt.RenderingHints;
import principal.Pantalla;

public class PantallaGameOver implements Pantalla {
    private PanelJuego juego;

    public PantallaGameOver(PanelJuego juego) {
        this.juego = juego;
    }

    @Override
    public void InicializarPantalla() {

    }

    @Override
    public void PintarPantalla(Graphics g) {
        Sprite fondo = new Sprite(300, juego.getWidth(), 0, 100, "imagenes/gameover.png", juego);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, juego.getWidth(), juego.getHeight());
        juego.pintarFondoAColor(g);
        fondo.pintarEnMundo(g);
        if (g instanceof Graphics2D) {
            g.setColor(Color.WHITE);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.drawString("PUNTUACIÓN: " + juego.getPuntuacion(), 150, 450);
        g.dispose();
    }

    @Override
    public void EjecutarFrame() {
        // TODO Auto-generated method stub

    }

    @Override
    public void ArrancarBola() {
        // TODO Auto-generated method stub

    }

    @Override
    public void PintarFondo() {
        // TODO Auto-generated method stub

    }
    
}
