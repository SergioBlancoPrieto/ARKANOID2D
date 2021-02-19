package pantallas;

import principal.Bola;
import principal.PanelJuego;
import principal.Pantalla;
import principal.Sprite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.Image;

public class PantallaDeJuego implements Pantalla {
    private Bola bola;
    private Sprite barra;
    private Sprite[][] bloques;
    private String[] rutas = { "imagenes/bloque_amarillo.png", "imagenes/bloque_blanco.png", "imagenes/bloque_cian.png",
            "imagenes/bloque_morado.png", "imagenes/bloque_naranja.png", "imagenes/bloque_rojo.png",
            "imagenes/bloque_rosa.png", "imagenes/bloque_verde.png" };
    private PanelJuego juego;
    private BufferedImage fondo = null;
    private Image fondoEscalado;

    public PantallaDeJuego(PanelJuego juego) {
        this.juego = juego;
        InicializarPantalla();
    }

    @Override
    public void InicializarPantalla() {
        bloques = new Sprite[5][4];
        Random rd = new Random();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                bloques[i][j] = new Sprite(20, 50, ((i + 1) * 55), ((j + 1) * 50), rutas[rd.nextInt(rutas.length)],
                        juego);
            }
        }

        bola = new Bola(20, 20, 190, 490, "imagenes/arkanoidball.png", juego);

        barra = new Sprite(40, 60, 170, 520, "imagenes/arkanoidbar.png", juego, juego.getVentana());

        try {
            fondo = ImageIO.read(new File("imagenes/akranoidbg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graphics g = fondo.getGraphics();
    }

    @Override
    public void PintarPantalla(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, juego.getWidth(), juego.getHeight());
        g.drawImage(fondoEscalado, 0, 0, null);
        if (g instanceof Graphics2D) {
            g.setColor(Color.WHITE);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.drawString("PUNTUACIÓN:" + juego.getPuntuacion(), 15, 30);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (bloques[i][j] != null) {
                    bloques[i][j].pintarEnMundo(g);
                }
            }
        }

        barra.pintarEnMundo(g);

        bola.pintarEnMundo(g);

        g.dispose();

    }

    public void ComprobarVictoria() {
        boolean victoria = false;
        int numBloques = bloques.length * bloques[0].length;
        for (int i = 0; i < bloques.length; i++) {
            for (int j = 0; j < bloques[0].length; j++) {
                if (bloques[i][j] == null) {
                    numBloques--;
                }
            }
        }
        victoria = numBloques == 0;
        if (victoria) {
            juego.setPantallaActual(new PantallaVictoria(juego));
            bola.setVelX(0);
            bola.setVelY(0);
        }
    }

    public boolean ComprobarBola(Sprite otro) {
        if (bola.ColisionaAbajo(otro) || bola.ColisionaArriba(otro)) {
            bola.setVelY(bola.getVelY() * -1);
            return true;
        }
        if (bola.ColisionaDerecha(otro) || bola.ColisionaIzquierda(otro)) {
            bola.setVelX(bola.getVelX() * -1);
            return true;
        }
        return false;
    }

    @Override
    public void EjecutarFrame() {
        barra.actualizarPosicionBarra(bola);
        bola.actualizarPosicion();
        for (int i = 0; i < bloques.length; i++) {
            for (int j = 0; j < bloques[0].length; j++) {
                if (bloques[i][j] != null) {
                    if (ComprobarBola(bloques[i][j])) {
                        bloques[i][j] = null;
                        juego.setPuntuacion(juego.getPuntuacion() + 1);
                    }
                }
            }
        }
        ComprobarBola(barra);
        ComprobarVictoria();
    }

    @Override
    public void ArrancarBola() {
        bola.setVelX(5);
        bola.setVelY(-5);
    }

    @Override
    public void PintarFondo() {
        fondoEscalado = fondo.getScaledInstance(juego.getWidth(), juego.getHeight(), Image.SCALE_SMOOTH);
        juego.repaint();
    }
}
