package pantallas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import principal.PanelJuego;
import principal.Pantalla;
import principal.Sprite;
import java.awt.Color;
import java.awt.RenderingHints;

public class PantallaInicio implements Pantalla {
    private PanelJuego juego;

    public PantallaInicio(PanelJuego juego) {
        this.juego = juego;
    }

    @Override
    public void InicializarPantalla() {
        
    }

    @Override
    public void PintarPantalla(Graphics g) {
        Sprite fondo = new Sprite(juego.getHeight(), juego.getWidth(), 0, 0, "imagenes/pantallatitulo.png", juego);
        fondo.pintarEnMundo(g);
        if (g instanceof Graphics2D) {
            g.setColor(Color.WHITE);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.drawString("PULSAR PARA EMPEZAR", 130, 500);
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
