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

public class Sprite {

    private int posX;
    private int posY;
    private int velX;
    private int velY;
    private int ancho;
    private int alto;
    private String ruta;
    private BufferedImage img;
    private PanelJuego juego;
    private JFrame ventana;

    public Sprite(int alto, int ancho, int posX, int posY, String ruta, PanelJuego juego) {
        this.posX = posX;
        this.posY = posY;
        this.ancho = ancho;
        this.alto = alto;
        this.ruta = ruta;
        pintarBuffer(ruta);
        this.juego = juego;
    }

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

    public void pintarEnMundo(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawImage(img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), posX, posY, null);
    }

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
