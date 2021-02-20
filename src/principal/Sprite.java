package principal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import pantallas.PantallaGameOver;

/**
 * Clase que define cada sprite en el juego y controla sus posiciones y funciones
 * 
 * @author Sergio Blanco Prieto
 */
public class Sprite {
    //Posición del sprite en la ventana
    private int posX;
    private int posY;

    //Velocidad del sprite
    private int velX;
    private int velY;

    //Dimensiones del sprite
    private int ancho;
    private int alto;

    //Componentes para dar una imagen al sprite
    private String ruta;
    private BufferedImage img;
    private PanelJuego juego;

    //Referencia a la ventada donde se carga el juego
    private JFrame ventana;

    /**
     * Primer constructor de la clase sprite
     * Este constructor es utilizado por todos los sprites excepto la barra
     * 
     * @param alto : Alto del sprite
     * @param ancho : Ancho del sprite
     * @param posX : Posición del sprite en el eje X
     * @param posY : Posición del sprite en el eje Y
     * @param ruta : Ruta de la imagen del sprite
     * @param juego : Referencia Al panel de juego
     */
    public Sprite(int alto, int ancho, int posX, int posY, String ruta, PanelJuego juego) {
        this.posX = posX;
        this.posY = posY;
        this.ancho = ancho;
        this.alto = alto;
        this.ruta = ruta;
        pintarBuffer(ruta);
        this.juego = juego;
    }

    /**
     * Segundo constructor de la clase sprite
     * Este constructor es utilizado por la barra
     * 
     * @param alto : Alto del sprite
     * @param ancho : Ancho del sprite
     * @param posX : Posición del sprite en el eje X
     * @param posY : Posición del sprite en el eje Y
     * @param ruta : Ruta de la imagen del sprite
     * @param juego : Referencia Al panel de juego
     * @param ventana : Referencia a la venana principal
     */
    public Sprite(int alto, int ancho, int posX, int posY, String ruta, PanelJuego juego, JFrame ventana) {
        this.posX = posX;
        this.posY = posY;
        this.ancho = ancho;
        this.alto = alto;
        this.ruta = ruta;
        pintarBuffer(ruta);
        this.juego = juego;
        this.ventana = ventana;
    }

    /**
     * Método que pinta un sprite con una imagen en la pantalla
     * 
     * @param ruta : ruta de la imagen del sprite
     */
    protected void pintarBuffer(String ruta) {
        File fichero = new File(ruta);
        img = new BufferedImage(ancho, alto, BufferedImage.SCALE_SMOOTH);
        Graphics g = img.getGraphics();
        try {
            img = ImageIO.read(fichero);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, ancho, alto, null);
        g.dispose();
    }

    /**
     * Método que modifica la velocidad del sprite si toca un extremo de la pantalla
     * Este método es heredado y usado por la bola del arkanoid
     * Si toca el extremo inferión terminará la partida, creándose la oantalla de game over
     */
    public void actualizarPosicion() {

        if (posX + ancho >= juego.getWidth()) {
            velX = -Math.abs(velX);
        }

        if (posX < 0) {
            velX = Math.abs(velX);
        }

        if (posY + alto >= juego.getHeight()) {
            juego.setPantallaActual(new PantallaGameOver(juego));
            velX = 0;
            velY = 0;
        }

        if (posY < 0) {
            velY = Math.abs(velY);
        }

        posX = posX + velX;
        posY = posY + velY;
    }

    /**
     * Método que mueve la barra cuando pulsas las teclas de movimiento
     * La barra se dentendrá si alcanza los bordes de la pantalla
     * Si la bola no ha sido lanzada la moverá con ella
     * 
     * @param bola : referencia a la bola del arkanoid
     */
    public void actualizarPosicionBarra(Bola bola) {
        if (juego.getIzquierdaPulsado()) {
            posX = posX - 5;
            if (posX < 0) {
                posX = 0;
            }
            if (!juego.getEnMovimiento()) {
                bola.setPosX(posX + 20);
            }
        }
        if (juego.getDerechaPulsado()) {
            posX = posX + 5;
            if (posX > (ventana.getWidth() - ancho - 15)) {
                posX = ventana.getWidth() - ancho - 15;
            }
            if (!juego.getEnMovimiento()) {
                bola.setPosX(posX + 20);
            }
        }
    }

    /**
     * Método que pinta el sprite en el mapa
     * Utilizado por los sprites con imágenes de fondo en las diferentes pantallas
     * 
     * @param g : Componente gráfico del juego
     */
    public void pintarEnMundo(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawImage(img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), posX, posY, null);
    }

    //Getters y Setters
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }
}
