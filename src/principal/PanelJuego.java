package principal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import pantallas.PantallaDeJuego;
import pantallas.PantallaInicio;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelJuego extends JPanel implements Runnable {
    private boolean jugando;
    private boolean enMovimiento;
    private JFrame ventana;
    private int puntuacion;
    private boolean derechaPulsado;
    private boolean izquierdaPulsado;
    private Pantalla pantallaActual;
    private PanelJuego estePanel = this;

    PanelJuego(JFrame ventana) {
        this.ventana = ventana;
        jugando = false;
        enMovimiento = false;
        puntuacion = 0;

        pantallaActual = new PantallaInicio(this);

        new Thread(this).start();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent arg0) {
                pantallaActual.PintarFondo();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!jugando) {
                    jugando = true;
                    pantallaActual = new PantallaDeJuego(estePanel);
                }
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
                        pantallaActual.ArrancarBola();
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

    public void pintarFondoAColor(Graphics g) {
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        pantallaActual.PintarPantalla(g);
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

            pantallaActual.EjecutarFrame();

            repaint();
            Toolkit.getDefaultToolkit().sync();
        }
    }

    // GETTERS Y SETTERS
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

    public JFrame getVentana() {
        return this.ventana;
    }

    public void setVentana(JFrame ventana) {
        this.ventana = ventana;
    }

    public int getPuntuacion() {
        return this.puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
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

    public Pantalla getPantallaActual() {
        return this.pantallaActual;
    }

    public void setPantallaActual(Pantalla pantallaActual) {
        this.pantallaActual = pantallaActual;
    }
}
