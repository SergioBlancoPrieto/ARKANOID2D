package principal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import pantallas.PantallaDeJuego;
import pantallas.PantallaInicio;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que controla el juego y contiene las diferentes pantallas posibles
 * 
 * @author Sergio Blanco Prieto
 */
public class PanelJuego extends JPanel implements Runnable {
    //Variables que indican el estado de la partida
    private boolean jugando;
    private boolean enMovimiento;

    //Referencia a la ventana contenedora del panel de juego
    private JFrame ventana;

    //Puntuación actual de la partida
    private int puntuacion;

    //Variables que controlan si las flechas derecha e izquierda del teclado están pulsadas
    private boolean derechaPulsado;
    private boolean izquierdaPulsado;

    //Pandalla actual en la que se encuentra el juego
    private Pantalla pantallaActual;

    //Referencia a este panel de juego
    private PanelJuego estePanel = this;

    /**
     * Constructor de la clase panel juego donde se inicializan las variables de la partida, se asigna la referencia a la ventana,
     * se crea la pantalla de inicio y se añaden los diferentes listeners del panel
     * 
     * @param ventana : referencia a la ventana contenedora del panel de juego
     */
    PanelJuego(JFrame ventana) {
        //Primero se asigna la referencia a la ventana y se inicializan las variables
        this.ventana = ventana;
        jugando = false;
        enMovimiento = false;
        puntuacion = 0;

        //Después se crea la pantalla de inicio
        pantallaActual = new PantallaInicio(this);

        //Se inicializa el hilo del panel de juego, donde se refrescará la ventana
        new Thread(this).start();

        //Y finalmente se crean los listeners del panel de juego
        inicializarListeners();
    }

    /**
     * Método que inicializa los listeners del panel de juego
     */
    public void inicializarListeners() {
        //El primer listener crea la función de iniciar el juego al pulsar con el ratón por primera vez en la pantalla
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!jugando) {
                    jugando = true;
                    pantallaActual = new PantallaDeJuego(estePanel);
                }
            }
        });

        //El segundo listener detectará si se están pulsando las felchas derecha e izquierda del teclado
        //(también es posible utilizar las teclas A y D)
        //También nos permitirá poner la bola en marcha pulsando el espacio
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

        //El último listener detectará si se están soltando las felchas derecha e izquierda del teclado o teclas A y D
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

    /**
     * Método que rellenará el panel completo de un color ya especificado
     * 
     * @param g : componente gráfico del panel de juego
     */
    public void pintarFondoAColor(Graphics g) {
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Método que pintará la pantalla correspondiente en el panel de juego
     * 
     * @param g : componente gráfico del panel de juego
     */
    @Override
    public void paintComponent(Graphics g) {
        pantallaActual.PintarPantalla(g);
    }

    /**
     * Método que se ejecutará al comenzar un hilo de esta clase
     * En este caso se ejecutará al crear el panel de juego desde el constructor
     * En este método se implementa la función de refrescar la pantalla contínuamente hasta cerrar el programa
     */
    @Override
    public void run() {
        //Primero se espera 0.2 segundos para dar tiempo a inicializar todo correctamente
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Después se crea un bucle infinito en el que cada 25 milisegundos se refrescará la pantalla
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
