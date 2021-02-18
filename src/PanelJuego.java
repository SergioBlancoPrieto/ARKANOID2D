import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelJuego extends JPanel implements Runnable {
    private BufferedImage fondo = null;
    private Image fondoEscalado;
    private boolean jugando;
    private boolean enMovimiento;
    private Bola bola;
    private Sprite barra;
    private Sprite[][] bloques;
    private boolean derechaPulsado;
    private boolean izquierdaPulsado;
    private JFrame ventana;
    private String [] rutas = {"imagenes/bloque_amarillo.png", "imagenes/bloque_blanco.png", "imagenes/bloque_cian.png", "imagenes/bloque_morado.png", "imagenes/bloque_naranja.png", "imagenes/bloque_rojo.png", "imagenes/bloque_rosa.png", "imagenes/bloque_verde.png"};
    private boolean gameOver;
    private boolean victoria;
    private int puntuacion;

    PanelJuego(JFrame ventana) {
        this.ventana = ventana;
        jugando = false;
        enMovimiento = false;
        gameOver = false;
        victoria = false;
        puntuacion = 0;

        bloques = new Sprite[5][4];
        Random rd = new Random();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                bloques[i][j] = new Sprite(20, 50, ((i + 1) * 55), ((j + 1) * 50), rutas[rd.nextInt(rutas.length)],this);
            }
        }

        bola = new Bola(20, 20, 190, 490, "imagenes/arkanoidball.png", this);

        barra = new Sprite(40, 60, 170, 520, "imagenes/arkanoidbar.png", this, this.ventana);

        try {
            fondo = ImageIO.read(new File("imagenes/akranoidbg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graphics g = fondo.getGraphics();

        new Thread(this).start();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent arg0) {
                fondoEscalado = fondo.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jugando = true;
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                    izquierdaPulsado = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    derechaPulsado = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (!enMovimiento) {
                        bola.setVelX(5);
                        bola.setVelY(-5);
                    }
                    enMovimiento = true;
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                    izquierdaPulsado = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    derechaPulsado = false;
                }
            }
        });
    }

    public void pintarPartida(Graphics g) {
        g.drawImage(fondoEscalado, 0, 0, null);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if(bloques[i][j] != null) {
                    bloques[i][j].pintarEnMundo(g);
                }
            }
        }

        barra.pintarEnMundo(g);

        bola.pintarEnMundo(g);

        g.dispose();
    }

    public void pintarPantallaInicio(Graphics g) {
        Sprite fondo = new Sprite(getHeight(), getWidth(), 0, 0, "imagenes/pantallatitulo.png", this);
        fondo.pintarEnMundo(g);
        if (g instanceof Graphics2D) {
            g.setColor(Color.WHITE);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.drawString("PULSAR PARA EMPEZAR", 130, 500);
        g.dispose();
    }

    public void pintarPantallaGameOver(Graphics g) {
        Sprite fondo = new Sprite(300, getWidth(), 0, 100, "imagenes/gameover.png", this);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        pintarFondoAColor(g);
        fondo.pintarEnMundo(g);
        if (g instanceof Graphics2D) {
            g.setColor(Color.WHITE);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.drawString("PUNTUACIÓN: " + puntuacion, 150, 450);
        g.dispose();
    }

    public void pintarPantallaVictoria(Graphics g) {
        Sprite fondo = new Sprite(400, getWidth(), 0, 0, "imagenes/victoria.png", this);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        pintarFondoAColor(g);
        fondo.pintarEnMundo(g);
        g.dispose();
    }

    public void ComprobarVictoria() {
        int numBloques = bloques.length * bloques[0].length;
        for (int i = 0; i < bloques.length; i++) {
            for (int j = 0; j < bloques[0].length; j++) {
                if (bloques[i][j] == null) {
                    numBloques--;
                }
            }
        }
        victoria = numBloques == 0;
        if(victoria) {
            bola.setVelX(0);
            bola.setVelY(0);
        }
    }

    public void pintarFondoAColor(Graphics g) {
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        if (jugando) {
            if(gameOver) {
                pintarPantallaGameOver(g);
            } else {
                if (victoria) {
                    pintarPantallaVictoria(g);
                } else {
                    pintarPartida(g);
                }
            }
        } else {
            pintarPantallaInicio(g);
        }
    }

    public boolean ComprobarBola(Sprite otro) {
        if(bola.ColisionaAbajo(otro) || bola.ColisionaArriba(otro)) {
            bola.setVelY(bola.getVelY() * -1);
            return true;
        }
        if(bola.ColisionaDerecha(otro) || bola.ColisionaIzquierda(otro)) {
            bola.setVelY(bola.getVelX() * -1);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (jugando) {
                barra.actualizarPosicionBarra();
                bola.actualizarPosicion();
                for (int i = 0; i < bloques.length; i++) {
                    for (int j = 0; j < bloques[0].length; j++) {
                        if (bloques[i][j] != null) {
                            if (ComprobarBola(bloques[i][j])) {
                                bloques[i][j] = null;
                                puntuacion++;
                            }
                        }
                    }
                }
                ComprobarBola(barra);
                ComprobarVictoria();
            }

            repaint();
            Toolkit.getDefaultToolkit().sync();
        }
    }

    // GETTERS Y SETTERS
    public BufferedImage getFondo() {
        return this.fondo;
    }

    public void setFondo(BufferedImage fondo) {
        this.fondo = fondo;
    }

    public Image getFondoEscalado() {
        return this.fondoEscalado;
    }

    public void setFondoEscalado(Image fondoEscalado) {
        this.fondoEscalado = fondoEscalado;
    }

    public boolean isJugando() {
        return this.jugando;
    }

    public boolean getJugando() {
        return this.jugando;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }

    public boolean isEnMovimiento() {
        return this.enMovimiento;
    }

    public boolean getEnMovimiento() {
        return this.enMovimiento;
    }

    public void setEnMovimiento(boolean enMovimiento) {
        this.enMovimiento = enMovimiento;
    }

    public Bola getBola() {
        return this.bola;
    }

    public void setBola(Bola bola) {
        this.bola = bola;
    }

    public Sprite getBarra() {
        return this.barra;
    }

    public void setBarra(Sprite barra) {
        this.barra = barra;
    }

    public Sprite[][] getBloques() {
        return this.bloques;
    }

    public void setBloques(Sprite[][] bloques) {
        this.bloques = bloques;
    }

    public boolean isDerechaPulsado() {
        return this.derechaPulsado;
    }

    public boolean getDerechaPulsado() {
        return this.derechaPulsado;
    }

    public void setDerechaPulsado(boolean derechaPulsado) {
        this.derechaPulsado = derechaPulsado;
    }

    public boolean isIzquierdaPulsado() {
        return this.izquierdaPulsado;
    }

    public boolean getIzquierdaPulsado() {
        return this.izquierdaPulsado;
    }

    public void setIzquierdaPulsado(boolean izquierdaPulsado) {
        this.izquierdaPulsado = izquierdaPulsado;
    }


    public JFrame getVentana() {
        return this.ventana;
    }

    public void setVentana(JFrame ventana) {
        this.ventana = ventana;
    }

    public String[] getRutas() {
        return this.rutas;
    }

    public void setRutas(String[] rutas) {
        this.rutas = rutas;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isVictoria() {
        return this.victoria;
    }

    public boolean getVictoria() {
        return this.victoria;
    }

    public void setVictoria(boolean victoria) {
        this.victoria = victoria;
    }
}
