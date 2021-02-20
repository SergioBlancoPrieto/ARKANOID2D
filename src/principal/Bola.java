package principal;

/**
 * Clase que define los métodos específicos de la bola del arkanoid
 * Al extender de la clase Sprite tiene también todas sus características
 * 
 * @author Sergio Blanco Prieto
 */
public class Bola extends Sprite {

    /**
     * Constructor de la clase bola donde se asignan todas sis características
     * 
     * @param alto : Alto de la bola
     * @param ancho : Ancho de la bola
     * @param posX : Posición inicial de la bola en el eje X
     * @param posY : Posición inicial de la bola en el eje Y
     * @param ruta : Ruta de la imagen de la bola
     * @param juego : Referencia al panel de juego
     */
    public Bola(int alto, int ancho, int posX, int posY, String ruta, PanelJuego juego) {
        super(alto, ancho, posX, posY, ruta, juego);
    }

    /**
     * Método que comprueba si la bola colisiona con otro sprite en cualquier lugar
     * 
     * @param otro : El sprite con el que puede estar colisionando la bola
     * 
     * @return : devuelve true si la bola colisiona con el sprite y false si no lo hace
     */
    private boolean Colisiona(Sprite otro) {
        boolean colisionaX = false;
        Sprite izquierda, derecha, arriba, abajo;
        //Primero se comprueba se colisionan en el eje X
        if (this.getPosX() < otro.getPosX()) {
            izquierda = this;
            derecha = otro;
        } else {
            derecha = this;
            izquierda = otro;
        }
        colisionaX = izquierda.getPosX() + izquierda.getAncho() >= derecha.getPosX();
        boolean colisionaY;
        //Si no es el caso se devuelve false y acaba el método
        if (!colisionaX) {
            return false;
        }
        //En caso afirmativo se comprobará si colisionan en el eje Y
        if (this.getPosY() < otro.getPosY()) {
            arriba = this;
            abajo = otro;
        } else {
            abajo = this;
            arriba = otro;
        }
        colisionaY = arriba.getPosY() + arriba.getAlto() >= abajo.getPosY();
        //Finalmente, si colisionan en ambos ejes devolverá true, y si no es el caso devolverá false
        return colisionaX && colisionaY;
    }

    /**
     * Método que comprueba si la bola está colisionando desde la parte de arriba
     * 
     * @param otro : Sprite con el que colisiona la bola
     * 
     * @return : Devuelve true si la bola colisiona por arriba y false si no es el caso
     */
    public boolean ColisionaArriba(Sprite otro) {
        Sprite izquierda, derecha;
        if (Colisiona(otro) && this.getPosY() > otro.getPosY()) {
            if (this.getPosX() < otro.getPosX()) {
                izquierda = this;
                derecha = otro;
            } else {
                derecha = this;
                izquierda = otro;
            }
            return izquierda.getPosX() + izquierda.getAncho() >= derecha.getPosX();
        } else {
            return false;
        }
    }

    /**
     * Método que comprueba si la bola está colisionando desde la parte de abajo
     * 
     * @param otro : Sprite con el que colisiona la bola
     * 
     * @return : Devuelve true si la bola colisiona por abajo y false si no es el caso
     */
    public boolean ColisionaAbajo(Sprite otro) {
        Sprite izquierda, derecha;
        if (Colisiona(otro) && this.getPosY() < otro.getPosY()) {
            if (this.getPosX() < otro.getPosX()) {
                izquierda = this;
                derecha = otro;
            } else {
                derecha = this;
                izquierda = otro;
            }
            return izquierda.getPosX() + izquierda.getAncho() >= derecha.getPosX();
        } else {
            return false;
        }
    }

    /**
     * Método que comprueba si la bola está colisionando desde la derecha
     * 
     * @param otro : Sprite con el que colisiona la bola
     * 
     * @return : Devuelve true si la bola colisiona por la derecha y false si no es el caso
     */
    public boolean ColisionaDerecha(Sprite otro) {
        Sprite arriba, abajo;
        if (Colisiona(otro) && this.getPosX() > otro.getPosX()) {
            if (this.getPosY() < otro.getPosY()) {
                arriba = this;
                abajo = otro;
            } else {
                abajo = this;
                arriba = otro;
            }
            return arriba.getPosY() + arriba.getAlto() >= abajo.getPosY();
        } else {
            return false;
        }
    }

    /**
     * Método que comprueba si la bola está colisionando desde la izquierda
     * 
     * @param otro : Sprite con el que colisiona la bola
     * 
     * @return : Devuelve true si la bola colisiona por la izquierda y false si no es el caso
     */
    public boolean ColisionaIzquierda(Sprite otro) {
        Sprite arriba, abajo;
        if (Colisiona(otro) && this.getPosX() < otro.getPosX()) {
            if (this.getPosY() < otro.getPosY()) {
                arriba = this;
                abajo = otro;
            } else {
                abajo = this;
                arriba = otro;
            }
            return arriba.getPosY() + arriba.getAlto() >= abajo.getPosY();
        } else {
            return false;
        }
    }
}
