package vista;

import modelo.Libro;
import servicio.BibliotecaService;
import java.util.List;
import java.util.Scanner;

/**
 * Interfaz de usuario por consola para el Sistema de Gestión de Biblioteca (BST).
 */
public class MenuPrincipal {

    private final BibliotecaService servicio;
    private final Scanner scanner;

    public MenuPrincipal() {
        this.servicio = new BibliotecaService();
        this.scanner  = new Scanner(System.in);
    }

    // =========================================================
    //  PUNTO DE ENTRADA
    // =========================================================

    public static void main(String[] args) {
        new MenuPrincipal().ejecutar();
    }

    public void ejecutar() {
        mostrarBienvenida();
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            procesarOpcion(opcion);
        } while (opcion != 0);
        System.out.println("\n  ¡Hasta luego! Sistema cerrado.\n");
        scanner.close();
    }

    // =========================================================
    //  MENÚ PRINCIPAL
    // =========================================================

    private void mostrarBienvenida() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║         BIBLIOTECA UNIVERSITARIA - BST v1.0         ║");
        System.out.println("║    Sistema de Gestión de Catálogo con Árbol BST     ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("  Catálogo inicializado con " + servicio.totalLibros() + " libros de prueba.");
    }

    private void mostrarMenu() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║         SISTEMA DE GESTIÓN DE BIBLIOTECA (BST)      ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  📚 CATÁLOGO                                        ║");
        System.out.println("║   1. Registrar nuevo libro                          ║");
        System.out.println("║   2. Buscar libro por autor                         ║");
        System.out.println("║   3. Buscar libro por ISBN                          ║");
        System.out.println("║   4. Eliminar libro del catálogo                    ║");
        System.out.println("║                                                     ║");
        System.out.println("║  📋 LISTADOS                                        ║");
        System.out.println("║   5. Listar libros (InOrden - alfabético)           ║");
        System.out.println("║   6. Listar libros (PreOrden - estructura)          ║");
        System.out.println("║   7. Listar libros (PostOrden)                      ║");
        System.out.println("║                                                     ║");
        System.out.println("║  🔄 PRÉSTAMOS                                       ║");
        System.out.println("║   8. Registrar préstamo de libro                   ║");
        System.out.println("║   9. Registrar devolución de libro                 ║");
        System.out.println("║  10. Listar libros disponibles                     ║");
        System.out.println("║  11. Listar libros prestados                       ║");
        System.out.println("║                                                     ║");
        System.out.println("║  🔍 BÚSQUEDAS                                       ║");
        System.out.println("║  12. Buscar libros por categoría                   ║");
        System.out.println("║  14. Buscar por subcadena de autor                 ║");
        System.out.println("║                                                     ║");
        System.out.println("║  📊 EXTRA                                           ║");
        System.out.println("║  13. Estadísticas del catálogo                     ║");
        System.out.println("║  15. Visualizar árbol BST                          ║");
        System.out.println("║                                                     ║");
        System.out.println("║   0. Salir                                          ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    private void procesarOpcion(int opcion) {
        System.out.println();
        try {
            switch (opcion) {
                case  1: registrarLibro();         break;
                case  2: buscarPorAutor();         break;
                case  3: buscarPorIsbn();          break;
                case  4: eliminarLibro();          break;
                case  5: listarInOrden();          break;
                case  6: listarPreOrden();         break;
                case  7: listarPostOrden();        break;
                case  8: registrarPrestamo();      break;
                case  9: registrarDevolucion();    break;
                case 10: listarDisponibles();      break;
                case 11: listarPrestados();        break;
                case 12: buscarPorCategoria();     break;
                case 13: mostrarEstadisticas();    break;
                case 14: buscarAutorParcial();     break;
                case 15: visualizarArbol();        break;
                case  0: break; // Salir
                default: System.out.println("  ⚠️  Opción no válida. Ingrese un número entre 0 y 15.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("  ⚠️  Entrada inválida: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("  ❌ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  ❌ Error inesperado: " + e.getMessage());
        }
    }

    // =========================================================
    //  OPCIONES DEL MENÚ
    // =========================================================

    private void registrarLibro() {
        System.out.println("─── REGISTRAR NUEVO LIBRO ───────────────────────────");
        String isbn    = leerCadena("ISBN (ej: 978-0-00)    : ");
        String titulo  = leerCadena("Título                 : ");
        String autor   = leerCadena("Autor (Apellido, Nom.) : ");
        String editorial = leerCadena("Editorial              : ");
        int    anio    = leerEntero("Año de publicación     : ");
        String cat     = leerCadena("Categoría              : ");

        Libro libro = new Libro(isbn, titulo, autor, editorial, anio, cat);
        servicio.registrarLibro(libro);
        System.out.println("  ✅ Libro registrado exitosamente: " + titulo);
    }

    private void buscarPorAutor() {
        System.out.println("─── BUSCAR POR AUTOR ────────────────────────────────");
        String autor = leerCadena("Autor a buscar: ");
        Libro libro = servicio.buscarPorAutor(autor);
        if (libro != null) {
            System.out.println("\n  📖 Libro encontrado:");
            System.out.println(libro);
        } else {
            System.out.println("  ℹ️  No se encontró ningún libro del autor '" + autor + "'.");
        }
    }

    private void buscarPorIsbn() {
        System.out.println("─── BUSCAR POR ISBN ─────────────────────────────────");
        String isbn = leerCadena("ISBN a buscar: ");
        Libro libro = servicio.buscarPorIsbn(isbn);
        if (libro != null) {
            System.out.println("\n  📖 Libro encontrado:");
            System.out.println(libro);
        } else {
            System.out.println("  ℹ️  No se encontró ningún libro con ISBN '" + isbn + "'.");
        }
    }

    private void eliminarLibro() {
        System.out.println("─── ELIMINAR LIBRO ──────────────────────────────────");
        String autor = leerCadena("Autor del libro a eliminar: ");
        System.out.print("  ¿Confirma la eliminación? (s/n): ");
        String conf = scanner.nextLine().trim();
        if (conf.equalsIgnoreCase("s")) {
            boolean ok = servicio.eliminarLibro(autor);
            if (ok) System.out.println("  ✅ Libro eliminado del catálogo.");
            else    System.out.println("  ℹ️  No se encontró el libro del autor '" + autor + "'.");
        } else {
            System.out.println("  ⚠️  Operación cancelada.");
        }
    }

    private void listarInOrden() {
        System.out.println("─── LISTADO INORDEN (ALFABÉTICO) ────────────────────");
        imprimirListado(servicio.listarTodosInOrden());
    }

    private void listarPreOrden() {
        System.out.println("─── LISTADO PREORDEN (ESTRUCTURA ÁRBOL) ─────────────");
        imprimirListado(servicio.listarTodosPreOrden());
    }

    private void listarPostOrden() {
        System.out.println("─── LISTADO POSTORDEN ───────────────────────────────");
        imprimirListado(servicio.listarTodosPostOrden());
    }

    private void registrarPrestamo() {
        System.out.println("─── REGISTRAR PRÉSTAMO ──────────────────────────────");
        String autor       = leerCadena("Autor del libro   : ");
        String prestatario = leerCadena("Nombre estudiante : ");
        servicio.registrarPrestamo(autor, prestatario);
        System.out.println("  ✅ Préstamo registrado a: " + prestatario);
    }

    private void registrarDevolucion() {
        System.out.println("─── REGISTRAR DEVOLUCIÓN ────────────────────────────");
        String autor = leerCadena("Autor del libro a devolver: ");
        servicio.registrarDevolucion(autor);
        System.out.println("  ✅ Devolución registrada. Libro disponible nuevamente.");
    }

    private void listarDisponibles() {
        System.out.println("─── LIBROS DISPONIBLES ──────────────────────────────");
        List<Libro> lista = servicio.listarDisponibles();
        if (lista.isEmpty()) {
            System.out.println("  ℹ️  No hay libros disponibles actualmente.");
        } else {
            System.out.println("  Total: " + lista.size() + " libro(s) disponible(s)\n");
            imprimirListado(lista);
        }
    }

    private void listarPrestados() {
        System.out.println("─── LIBROS PRESTADOS ────────────────────────────────");
        List<Libro> lista = servicio.listarPrestados();
        if (lista.isEmpty()) {
            System.out.println("  ℹ️  No hay libros prestados actualmente.");
        } else {
            System.out.println("  Total: " + lista.size() + " libro(s) prestado(s)\n");
            for (Libro l : lista) {
                System.out.printf("  📕 %-35s | Autor: %-20s%n", l.getTitulo(), l.getAutor());
                System.out.printf("     Prestatario: %-20s | Fecha: %s%n", l.getPrestatario(), l.getFechaPrestamo());
                System.out.println("  " + "─".repeat(60));
            }
        }
    }

    private void buscarPorCategoria() {
        System.out.println("─── BUSCAR POR CATEGORÍA ────────────────────────────");
        String cat = leerCadena("Categoría a buscar: ");
        List<Libro> lista = servicio.buscarPorCategoria(cat);
        if (lista.isEmpty()) {
            System.out.println("  ℹ️  No se encontraron libros en la categoría '" + cat + "'.");
        } else {
            System.out.println("  " + lista.size() + " libro(s) encontrado(s) en '" + cat + "':\n");
            imprimirListado(lista);
        }
    }

    private void mostrarEstadisticas() {
        System.out.println("─── ESTADÍSTICAS DEL CATÁLOGO ───────────────────────");
        if (servicio.catalogoVacio()) {
            System.out.println("  ℹ️  El catálogo está vacío.");
            return;
        }
        Libro primero = servicio.primerAutor();
        Libro ultimo  = servicio.ultimoAutor();

        System.out.println("  📊 RESUMEN DEL CATÁLOGO:");
        System.out.println("  ┌─────────────────────────────────────────────────┐");
        System.out.printf ("  │  Total de libros      : %-24d│%n", servicio.totalLibros());
        System.out.printf ("  │  Altura del árbol     : %-24d│%n", servicio.alturaArbol());
        System.out.printf ("  │  Libros disponibles   : %-24d│%n", servicio.totalDisponibles());
        System.out.printf ("  │  Libros prestados     : %-24d│%n", servicio.totalPrestados());
        System.out.printf ("  │  Primer autor (A-Z)   : %-24s│%n",
            primero != null ? primero.getAutor() : "N/A");
        System.out.printf ("  │  Último autor (A-Z)   : %-24s│%n",
            ultimo  != null ? ultimo.getAutor()  : "N/A");
        System.out.println("  └─────────────────────────────────────────────────┘");
    }

    private void buscarAutorParcial() {
        System.out.println("─── BÚSQUEDA PARCIAL POR AUTOR ──────────────────────");
        String sub = leerCadena("Subcadena a buscar: ");
        List<Libro> lista = servicio.buscarPorAutorParcial(sub);
        if (lista.isEmpty()) {
            System.out.println("  ℹ️  No se encontraron autores que contengan '" + sub + "'.");
        } else {
            System.out.println("  " + lista.size() + " resultado(s):\n");
            imprimirListado(lista);
        }
    }

    private void visualizarArbol() {
        System.out.println("─── ÁRBOL BST (ESTRUCTURA) ──────────────────────────");
        System.out.println("  (Ordenado alfabéticamente por autor)\n");
        servicio.imprimirArbol();
    }

    // =========================================================
    //  UTILIDADES
    // =========================================================

    private void imprimirListado(List<Libro> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("  ℹ️  No hay libros para mostrar.");
            return;
        }
        System.out.printf("  %-3s %-32s %-22s %-15s %s%n",
            "#", "Título", "Autor", "Categoría", "Estado");
        System.out.println("  " + "─".repeat(90));
        int i = 1;
        for (Libro l : lista) {
            System.out.printf("  %-3d %s%n", i++, l.toStringCorto());
        }
        System.out.println("  " + "─".repeat(90));
        System.out.println("  Total: " + lista.size() + " libro(s)");
    }

    private String leerCadena(String prompt) {
        System.out.print("  " + prompt);
        String valor = scanner.nextLine().trim();
        if (valor.isEmpty()) throw new IllegalArgumentException("El campo no puede estar vacío.");
        return valor;
    }

    private int leerEntero(String prompt) {
        System.out.print("  " + prompt);
        String linea = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Se esperaba un número entero, se recibió: '" + linea + "'");
        }
    }
}
