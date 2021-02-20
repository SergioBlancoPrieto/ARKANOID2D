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
/**
 * Esta clase crea la pantalla de juego y administra todos sus componentes.
 * 
 * @author Sergio Blanco Prieto
 */
public class PantallaDeJuego implements Pantalla {
    //Bola del arkanoid
    private Bola bola;
    //Barra del arkanoid
    private Sprite barra;
    //Bloques del arkanoid y las rutas de sus imágenes
    private Sprite[][] bloques;
    private String[] rutas = { "imagenes/bloque_amarillo.png", "imagenes/bloque_blanco.png", "imagenes/bloque_cian.png",
            "imagenes/bloque_morado.png", "imagenes/bloque_naranja.png", "imagenes/bloque_rojo.png",
            "imagenes/bloque_rosa.png", "imagenes/bloque_verde.png" };
    //Referencia al panel de juego que contiene las pantallas
    private PanelJuego juego;
    //Variables para pintar el fondo
    private BufferedImage fondo = null;
    private Image fondoEscalado;

    /**
     * Constructor de la clase que asigna la referencia al panel de juego e inicializa los componentes
     * 
     * @param juego : referencia al panel de juego
     */
    public PantallaDeJuego(PanelJuego juego) {
        this.juego = juego;
        InicializarPantalla();
    }

    /**
     * Método que inicializa los componentes de la pantalla de juego
     */
    @Override
    public void InicializarPantalla() {
        //Se recorre la matriz de bloques y se les inicializa y asigna una imagen aleatoria del array de rutas,
        //por lo que cada partida la combinación de colores será distinta
        bloques = new Sprite[5][4];
        Random rd = new Random();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                bloques[i][j] = new Sprite(20, 50, ((i + 1) * 55), ((j + 1) * 50), rutas[rd.nextInt(rutas.length)],
                        juego);
            }
        }

        //Se inicializan la bola y la barra con sus respectivas imágenes
        bola = new Bola(20, 20, 190, 490, "imagenes/arkanoidball.png", juego);

        barra = new Sprite(40, 60, 170, 520, "imagenes/arkanoidbar.png", juego, juego.getVentana());

        //Se le asigna una imagen al fondo
        try {
            fondo = ImageIO.read(new File("imagenes/akranoidbg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graphics g = fondo.getGraphics();
    }

    /**
     * Método que pinta todos los componentes del juego en sus respectivas posiciones
     */
    @Override
    public void PintarPantalla(Graphics g) {
        //Primero se reescala el fondo y se pinta en toda la pantalla
        fondoEscalado = fondo.getScaledInstance(juego.getWidth(), juego.getHeight(), Image.SCALE_SMOOTH);
        g.drawImage(fondoEscalado, 0, 0, null);
        //Después se crea una línea de texto donde se mostrará la puntuación en todo momento
        if (g instanceof Graphics2D) {
            g.setColor(Color.WHITE);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.drawString("PUNTUACIÓN:" + juego.getPuntuacion(), 15, 30);

        //Finalmente se recorre la matriz de bloques y se pintan en la pantalla, seguidos de la barra y la bola
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

    /**
     * Método que comprueba si el jugador ha ganado recorriendo la matriz de bloques y comprobando si queda alguno
     */
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
        //Si el número de bloques es 0 se pasará a la pantalla de victoria
        victoria = numBloques == 0;
        if (victoria) {
            juego.setPantallaActual(new PantallaVictoria(juego));
            bola.setVelX(0);
            bola.setVelY(0);
        }
    }

    /**
     * Método que comprueba si la bola colisiona con otro sprite y modifica su velocidad en consecuencia
     * 
     * @param otro : sprite con el que se quiere comprobar si choca la bola
     * 
     * @return : devolverá true si la bola choca con el sprite y false si no lo hace
     */
    public boolean ComprobarBola(Sprite otro) {
        if (bola.ColisionaAbajo(otro)) {
            bola.setVelY(Math.abs(bola.getVelY()) * -1);
            return true;
        }
        if (bola.ColisionaArriba(otro)) {
            bola.setVelY(Math.abs(bola.getVelY()));
            return true;
        }
        if (bola.ColisionaDerecha(otro) || bola.ColisionaIzquierda(otro)) {
            bola.setVelX(bola.getVelX() * -1);
            return true;
        }
        return false;
    }

    /**
     * Método que se ejecutará cada vez que se refresque la pantalla en el panel de juego
     */
    @Override
    public void EjecutarFrame() {
        //Primero se actualizan las posiciones de la barra y la bola en ese orden
        barra.actualizarPosicionBarra(bola);
        bola.actualizarPosicion();
        //Después se comprueba se la bola colisiona con algún bloque, y si es así se elimina dicho bloque y se aumenta la puntuación
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
        //Finalmente se comprueba si la bola colisiona con la barra y si el jugador ha ganado
        ComprobarBola(barra);
        ComprobarVictoria();
    }

    /**
     * Método que pondrá la bola en marcha
     */
    @Override
    public void ArrancarBola() {
        bola.setVelX(5);
        bola.setVelY(-5);
    }
}
