package principal;

import java.awt.Graphics;

/**
 * Interfaz que sirve de lienzo para crear las diferentes pantallas en sus respectivas clases
 * 
 * @author Sergio Blanco Prieto
 */
public interface Pantalla {
    //Método que inicializa los componentes de la pantalla
    public void InicializarPantalla();

    //Método que crea (en algunas pantallas) y pinta los componentes la pantalla
    public void PintarPantalla(Graphics g);

    //Método que se ejecutará al refrescar la pantalla
    public void EjecutarFrame();

    //Método que se encargará de poner la bola del arkanoid en marcha. Solo tendrá contenido en la pantalla de juego
    public void ArrancarBola();
}
