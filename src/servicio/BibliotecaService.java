package servicio;

import estructura.ArbolBST;
import modelo.Libro;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio de gestión de la biblioteca.
 * Encapsula la lógica de negocio para préstamos, devoluciones y consultas.
 */
public class BibliotecaService {

    private final ArbolBST catalogo;

    public BibliotecaService() {
        this.catalogo = new ArbolBST();
        cargarDatosPrueba();
    }

    // =========================================================
    //  DATOS DE PRUEBA
    // =========================================================

    private void cargarDatosPrueba() {
        Libro[] libros = {
            new Libro("978-0-06", "Cien Años de Soledad",    "García M., Gabriel",   "Sudamericana",   1967, "Literatura"),
            new Libro("978-0-07", "Ficciones",               "Borges, Jorge L.",     "Sur",            1944, "Ficción"),
            new Libro("978-0-08", "Rayuela",                 "Cortázar, Julio",      "Sudamericana",   1963, "Literatura"),
            new Libro("978-0-09", "La Casa de los Espíritus","Allende, Isabel",      "Plaza & Janés",  1982, "Ficción"),
            new Libro("978-0-10", "Veinte Poemas de Amor",   "Neruda, Pablo",        "Nascimento",     1924, "Poesía"),
            new Libro("978-0-11", "Desolación",              "Mistral, Gabriela",    "Instituto Hisp.",1922, "Poesía"),
            new Libro("978-0-12", "La Ciudad y los Perros",  "Vargas Ll., Mario",    "Seix Barral",    1963, "Literatura"),
            new Libro("978-0-13", "Pedro Páramo",            "Rulfo, Juan",          "FCE",            1955, "Literatura"),
        };
        for (Libro l : libros) catalogo.insertar(l);
    }

    // =========================================================
    //  GESTIÓN DEL CATÁLOGO
    // =========================================================

    public void registrarLibro(Libro libro) {
        catalogo.insertar(libro);
    }

    public Libro buscarPorAutor(String autor) {
        return catalogo.buscar(autor);
    }

    public Libro buscarPorIsbn(String isbn) {
        return catalogo.buscarPorIsbn(isbn);
    }

    public boolean eliminarLibro(String autor) {
        Libro libro = catalogo.buscar(autor);
        if (libro != null && !libro.isDisponible()) {
            throw new IllegalStateException(
                "No se puede eliminar el libro de '" + autor + "' porque está prestado a " + libro.getPrestatario());
        }
        return catalogo.eliminar(autor);
    }

    // =========================================================
    //  PRÉSTAMOS Y DEVOLUCIONES
    // =========================================================

    /**
     * Registra el préstamo de un libro.
     * @throws IllegalStateException si el libro no existe o ya está prestado.
     */
    public void registrarPrestamo(String autor, String nombrePrestatario) {
        if (nombrePrestatario == null || nombrePrestatario.trim().isEmpty())
            throw new IllegalArgumentException("El nombre del prestatario no puede estar vacío.");

        Libro libro = catalogo.buscar(autor);
        if (libro == null)
            throw new IllegalStateException("No se encontró ningún libro del autor '" + autor + "'.");
        if (!libro.isDisponible())
            throw new IllegalStateException(
                "El libro ya está prestado a '" + libro.getPrestatario() + "' desde " + libro.getFechaPrestamo());

        libro.setDisponible(false);
        libro.setPrestatario(nombrePrestatario.trim());
        libro.setFechaPrestamo(LocalDate.now());
    }

    /**
     * Registra la devolución de un libro.
     * @throws IllegalStateException si el libro no existe o no está prestado.
     */
    public void registrarDevolucion(String autor) {
        Libro libro = catalogo.buscar(autor);
        if (libro == null)
            throw new IllegalStateException("No se encontró ningún libro del autor '" + autor + "'.");
        if (libro.isDisponible())
            throw new IllegalStateException("El libro de '" + autor + "' no está registrado como prestado.");

        libro.setDisponible(true);
        libro.setPrestatario(null);
        libro.setFechaPrestamo(null);
    }

    // =========================================================
    //  LISTADOS Y FILTROS
    // =========================================================

    public List<Libro> listarTodosInOrden()     { return catalogo.recorridoInOrden(); }
    public List<Libro> listarTodosPreOrden()    { return catalogo.recorridoPreOrden(); }
    public List<Libro> listarTodosPostOrden()   { return catalogo.recorridoPostOrden(); }
    public List<Libro> listarDisponibles()      { return catalogo.listarDisponibles(); }
    public List<Libro> listarPrestados()        { return catalogo.listarPrestados(); }
    public List<Libro> buscarPorCategoria(String cat) { return catalogo.buscarPorCategoria(cat); }
    public List<Libro> buscarPorAutorParcial(String sub) { return catalogo.buscarPorAutorParcial(sub); }

    // =========================================================
    //  ESTADÍSTICAS
    // =========================================================

    public int totalLibros()       { return catalogo.contarNodos(); }
    public int alturaArbol()       { return catalogo.altura(); }
    public Libro primerAutor()     { return catalogo.encontrarMinimo(); }
    public Libro ultimoAutor()     { return catalogo.encontrarMaximo(); }
    public int totalDisponibles()  { return catalogo.listarDisponibles().size(); }
    public int totalPrestados()    { return catalogo.listarPrestados().size(); }

    public void imprimirArbol()    { catalogo.imprimirArbol(); }
    public boolean catalogoVacio() { return catalogo.isEmpty(); }
}
