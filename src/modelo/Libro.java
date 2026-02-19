package modelo;

import java.time.LocalDate;

/**
 * Clase que representa un libro en el catálogo de la biblioteca.
 */
public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private int anioPublicacion;
    private String categoria;
    private boolean disponible;
    private String prestatario;
    private LocalDate fechaPrestamo;

    public Libro(String isbn, String titulo, String autor, String editorial,
                 int anioPublicacion, String categoria) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.categoria = categoria;
        this.disponible = true;
        this.prestatario = null;
        this.fechaPrestamo = null;
    }

    // Getters y Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public String getPrestatario() { return prestatario; }
    public void setPrestatario(String prestatario) { this.prestatario = prestatario; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════════════════╗\n");
        sb.append(String.format("  ISBN        : %s%n", isbn));
        sb.append(String.format("  Título      : %s%n", titulo));
        sb.append(String.format("  Autor       : %s%n", autor));
        sb.append(String.format("  Editorial   : %s%n", editorial));
        sb.append(String.format("  Año         : %d%n", anioPublicacion));
        sb.append(String.format("  Categoría   : %s%n", categoria));
        sb.append(String.format("  Estado      : %s%n", disponible ? "✅ Disponible" : "❌ Prestado"));
        if (!disponible && prestatario != null) {
            sb.append(String.format("  Prestatario : %s%n", prestatario));
            sb.append(String.format("  Fecha Préstamo: %s%n", fechaPrestamo));
        }
        sb.append("╚══════════════════════════════════════════════════╝");
        return sb.toString();
    }

    public String toStringCorto() {
        return String.format("%-30s | %-20s | %-15s | %s",
            titulo.length() > 28 ? titulo.substring(0, 28) + ".." : titulo,
            autor.length() > 18 ? autor.substring(0, 18) + ".." : autor,
            categoria,
            disponible ? "Disponible" : "Prestado - " + prestatario);
    }
}
