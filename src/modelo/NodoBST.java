package modelo;

/**
 * Clase que representa un nodo del Árbol Binario de Búsqueda (BST).
 * Cada nodo almacena un libro y referencias a sus hijos izquierdo y derecho.
 */
public class NodoBST {
    public Libro libro;
    public NodoBST izquierdo;
    public NodoBST derecho;

    public NodoBST(Libro libro) {
        this.libro = libro;
        this.izquierdo = null;
        this.derecho = null;
    }
}
