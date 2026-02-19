package estructura;

import modelo.Libro;
import modelo.NodoBST;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del Árbol Binario de Búsqueda (BST) para gestión de libros.
 * El criterio de ordenamiento es el apellido del autor (comparación alfabética).
 */
public class ArbolBST {

    private NodoBST raiz;

    public ArbolBST() {
        this.raiz = null;
    }

    // =========================================================
    //  1. INSERTAR
    // =========================================================

    /**
     * Inserta un nuevo libro en el BST ordenado por autor.
     * @throws IllegalArgumentException si ya existe un libro con ese autor.
     */
    public void insertar(Libro libro) {
        if (libro == null) throw new IllegalArgumentException("El libro no puede ser nulo.");
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty())
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        raiz = insertarRecursivo(raiz, libro);
    }

    private NodoBST insertarRecursivo(NodoBST nodo, Libro libro) {
        if (nodo == null) return new NodoBST(libro);

        int cmp = libro.getAutor().compareToIgnoreCase(nodo.libro.getAutor());
        if (cmp < 0) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, libro);
        } else if (cmp > 0) {
            nodo.derecho = insertarRecursivo(nodo.derecho, libro);
        } else {
            throw new IllegalArgumentException(
                "Ya existe un libro del autor '" + libro.getAutor() + "' en el catálogo.");
        }
        return nodo;
    }

    // =========================================================
    //  2. BUSCAR POR AUTOR
    // =========================================================

    /**
     * Busca un libro por el apellido/nombre del autor.
     * @return El libro encontrado o null si no existe.
     */
    public Libro buscar(String autor) {
        if (autor == null || autor.trim().isEmpty())
            throw new IllegalArgumentException("El autor de búsqueda no puede estar vacío.");
        NodoBST resultado = buscarRecursivo(raiz, autor.trim());
        return resultado != null ? resultado.libro : null;
    }

    private NodoBST buscarRecursivo(NodoBST nodo, String autor) {
        if (nodo == null) return null;
        int cmp = autor.compareToIgnoreCase(nodo.libro.getAutor());
        if (cmp < 0) return buscarRecursivo(nodo.izquierdo, autor);
        if (cmp > 0) return buscarRecursivo(nodo.derecho, autor);
        return nodo;
    }

    // =========================================================
    //  3. ELIMINAR
    // =========================================================

    /**
     * Elimina un libro del BST por autor, manejando los 3 casos clásicos:
     * - Nodo hoja (sin hijos)
     * - Nodo con un solo hijo
     * - Nodo con dos hijos (sucesor inorden)
     */
    public boolean eliminar(String autor) {
        if (autor == null || autor.trim().isEmpty())
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        int[] eliminado = {0};
        raiz = eliminarRecursivo(raiz, autor.trim(), eliminado);
        return eliminado[0] == 1;
    }

    private NodoBST eliminarRecursivo(NodoBST nodo, String autor, int[] eliminado) {
        if (nodo == null) return null;

        int cmp = autor.compareToIgnoreCase(nodo.libro.getAutor());

        if (cmp < 0) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, autor, eliminado);
        } else if (cmp > 0) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, autor, eliminado);
        } else {
            eliminado[0] = 1;
            // Caso 1: Nodo hoja
            if (nodo.izquierdo == null && nodo.derecho == null) return null;
            // Caso 2: Un solo hijo
            if (nodo.izquierdo == null) return nodo.derecho;
            if (nodo.derecho == null) return nodo.izquierdo;
            // Caso 3: Dos hijos → sucesor inorden (mínimo del subárbol derecho)
            NodoBST sucesor = encontrarMinimoNodo(nodo.derecho);
            nodo.libro = sucesor.libro;
            nodo.derecho = eliminarRecursivo(nodo.derecho, sucesor.libro.getAutor(), new int[]{0});
        }
        return nodo;
    }

    // =========================================================
    //  4-6. RECORRIDOS
    // =========================================================

    /** Recorrido InOrden (izq → raíz → der): lista libros alfabéticamente. */
    public List<Libro> recorridoInOrden() {
        List<Libro> lista = new ArrayList<>();
        inOrdenRecursivo(raiz, lista);
        return lista;
    }

    private void inOrdenRecursivo(NodoBST nodo, List<Libro> lista) {
        if (nodo == null) return;
        inOrdenRecursivo(nodo.izquierdo, lista);
        lista.add(nodo.libro);
        inOrdenRecursivo(nodo.derecho, lista);
    }

    /** Recorrido PreOrden (raíz → izq → der): muestra estructura jerárquica. */
    public List<Libro> recorridoPreOrden() {
        List<Libro> lista = new ArrayList<>();
        preOrdenRecursivo(raiz, lista);
        return lista;
    }

    private void preOrdenRecursivo(NodoBST nodo, List<Libro> lista) {
        if (nodo == null) return;
        lista.add(nodo.libro);
        preOrdenRecursivo(nodo.izquierdo, lista);
        preOrdenRecursivo(nodo.derecho, lista);
    }

    /** Recorrido PostOrden (izq → der → raíz): útil para operaciones de limpieza. */
    public List<Libro> recorridoPostOrden() {
        List<Libro> lista = new ArrayList<>();
        postOrdenRecursivo(raiz, lista);
        return lista;
    }

    private void postOrdenRecursivo(NodoBST nodo, List<Libro> lista) {
        if (nodo == null) return;
        postOrdenRecursivo(nodo.izquierdo, lista);
        postOrdenRecursivo(nodo.derecho, lista);
        lista.add(nodo.libro);
    }

    // =========================================================
    //  7-8. MÍNIMO Y MÁXIMO
    // =========================================================

    /** Retorna el libro con el autor alfabéticamente primero. */
    public Libro encontrarMinimo() {
        if (raiz == null) return null;
        return encontrarMinimoNodo(raiz).libro;
    }

    private NodoBST encontrarMinimoNodo(NodoBST nodo) {
        while (nodo.izquierdo != null) nodo = nodo.izquierdo;
        return nodo;
    }

    /** Retorna el libro con el autor alfabéticamente último. */
    public Libro encontrarMaximo() {
        if (raiz == null) return null;
        NodoBST nodo = raiz;
        while (nodo.derecho != null) nodo = nodo.derecho;
        return nodo.libro;
    }

    // =========================================================
    //  9-10. CONTAR Y ALTURA
    // =========================================================

    /** Retorna el número total de libros en el catálogo. */
    public int contarNodos() {
        return contarRecursivo(raiz);
    }

    private int contarRecursivo(NodoBST nodo) {
        if (nodo == null) return 0;
        return 1 + contarRecursivo(nodo.izquierdo) + contarRecursivo(nodo.derecho);
    }

    /** Calcula la altura del árbol. */
    public int altura() {
        return alturaRecursiva(raiz);
    }

    private int alturaRecursiva(NodoBST nodo) {
        if (nodo == null) return 0;
        return 1 + Math.max(alturaRecursiva(nodo.izquierdo), alturaRecursiva(nodo.derecho));
    }

    // =========================================================
    //  VISUALIZACIÓN DEL ÁRBOL EN CONSOLA
    // =========================================================

    /** Imprime el árbol en consola con formato gráfico. */
    public void imprimirArbol() {
        if (raiz == null) {
            System.out.println("  (árbol vacío)");
            return;
        }
        imprimirArbolRecursivo(raiz, "", true);
    }

    private void imprimirArbolRecursivo(NodoBST nodo, String prefijo, boolean esUltimo) {
        if (nodo == null) return;
        System.out.println(prefijo + (esUltimo ? "└── " : "├── ") + nodo.libro.getAutor());
        String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
        if (nodo.izquierdo != null || nodo.derecho != null) {
            if (nodo.derecho != null) imprimirArbolRecursivo(nodo.derecho, nuevoPrefijo, nodo.izquierdo == null);
            if (nodo.izquierdo != null) imprimirArbolRecursivo(nodo.izquierdo, nuevoPrefijo, true);
        }
    }

    // =========================================================
    //  BÚSQUEDA POR ISBN (recorrido completo)
    // =========================================================

    /** Busca un libro por ISBN recorriendo todo el árbol. */
    public Libro buscarPorIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty())
            throw new IllegalArgumentException("El ISBN no puede estar vacío.");
        return buscarIsbnRecursivo(raiz, isbn.trim());
    }

    private Libro buscarIsbnRecursivo(NodoBST nodo, String isbn) {
        if (nodo == null) return null;
        if (nodo.libro.getIsbn().equalsIgnoreCase(isbn)) return nodo.libro;
        Libro izq = buscarIsbnRecursivo(nodo.izquierdo, isbn);
        if (izq != null) return izq;
        return buscarIsbnRecursivo(nodo.derecho, isbn);
    }

    // =========================================================
    //  FILTROS
    // =========================================================

    /** Retorna todos los libros disponibles. */
    public List<Libro> listarDisponibles() {
        List<Libro> resultado = new ArrayList<>();
        filtrarRecursivo(raiz, resultado, true, null);
        return resultado;
    }

    /** Retorna todos los libros prestados. */
    public List<Libro> listarPrestados() {
        List<Libro> resultado = new ArrayList<>();
        filtrarRecursivo(raiz, resultado, false, null);
        return resultado;
    }

    /** Retorna libros de una categoría específica. */
    public List<Libro> buscarPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty())
            throw new IllegalArgumentException("La categoría no puede estar vacía.");
        List<Libro> resultado = new ArrayList<>();
        filtrarCategoria(raiz, resultado, categoria.trim());
        return resultado;
    }

    /** Búsqueda parcial por subcadena del nombre del autor. */
    public List<Libro> buscarPorAutorParcial(String subcadena) {
        List<Libro> resultado = new ArrayList<>();
        buscarParcialRecursivo(raiz, subcadena.toLowerCase(), resultado);
        return resultado;
    }

    private void buscarParcialRecursivo(NodoBST nodo, String sub, List<Libro> resultado) {
        if (nodo == null) return;
        if (nodo.libro.getAutor().toLowerCase().contains(sub)) resultado.add(nodo.libro);
        buscarParcialRecursivo(nodo.izquierdo, sub, resultado);
        buscarParcialRecursivo(nodo.derecho, sub, resultado);
    }

    private void filtrarRecursivo(NodoBST nodo, List<Libro> lista, boolean disponible, String categoria) {
        if (nodo == null) return;
        if (nodo.libro.isDisponible() == disponible) lista.add(nodo.libro);
        filtrarRecursivo(nodo.izquierdo, lista, disponible, categoria);
        filtrarRecursivo(nodo.derecho, lista, disponible, categoria);
    }

    private void filtrarCategoria(NodoBST nodo, List<Libro> lista, String categoria) {
        if (nodo == null) return;
        if (nodo.libro.getCategoria().equalsIgnoreCase(categoria)) lista.add(nodo.libro);
        filtrarCategoria(nodo.izquierdo, lista, categoria);
        filtrarCategoria(nodo.derecho, lista, categoria);
    }

    public boolean isEmpty() {
        return raiz == null;
    }
}
