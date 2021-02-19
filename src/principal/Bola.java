package principal;

public class Bola extends Sprite {

    public Bola(int alto, int ancho, int posX, int posY, String ruta, PanelJuego juego) {
        super(alto, ancho, posX, posY, ruta, juego);
    }
    
    private boolean Colisiona(Sprite otro) {
        boolean colisionaX = false;
        Sprite izquierda, derecha, arriba, abajo;
        if(this.getPosX() < otro.getPosX()) {
            izquierda = this;
            derecha = otro;
        } else {
            derecha = this;
            izquierda = otro;
        }
        colisionaX = izquierda.getPosX() + izquierda.getAncho() >= derecha.getPosX();
        boolean colisionaY;
        if(!colisionaX) {
            return false;
        }
        if(this.getPosY() < otro.getPosY()) {
            arriba = this;
            abajo = otro;
        } else {
            abajo = this;
            arriba = otro;
        }
        colisionaY = arriba.getPosY() + arriba.getAlto() >= abajo.getPosY();
        return colisionaX && colisionaY;
    }

    public boolean ColisionaArriba(Sprite otro) {
        Sprite izquierda, derecha;
        if (Colisiona(otro) && this.getPosY() > otro.getPosY()) {
            if(this.getPosX() < otro.getPosX()) {
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

    public boolean ColisionaAbajo(Sprite otro) {
        Sprite izquierda, derecha;
        if (Colisiona(otro) && this.getPosY() < otro.getPosY()) {
            if(this.getPosX() < otro.getPosX()) {
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

    public boolean ColisionaDerecha(Sprite otro) {
        Sprite arriba, abajo;
        if (Colisiona(otro) && this.getPosX() > otro.getPosX()) {
            if(this.getPosY() < otro.getPosY()) {
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

    public boolean ColisionaIzquierda(Sprite otro) {
        Sprite arriba, abajo;
        if (Colisiona(otro) && this.getPosX() < otro.getPosX()) {
            if(this.getPosY() < otro.getPosY()) {
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
