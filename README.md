#  Sistema de Gestión de Biblioteca BST

Sistema de gestión de catálogo de biblioteca universitaria implementado con un **Árbol Binario de Búsqueda (BST)** en Java. Permite organizar, buscar, insertar y eliminar libros de forma eficiente usando el apellido del autor como criterio de ordenamiento.

---

##  Estructura del Proyecto

```
biblioteca-bst/
├── src/
│   ├── modelo/
│   │   ├── Libro.java         # Entidad principal
│   │   └── NodoBST.java       # Nodo del árbol
│   ├── estructura/
│   │   └── ArbolBST.java      # Implementación del BST
│   ├── servicio/
│   │   └── BibliotecaService.java  # Lógica de negocio
│   └── vista/
│       └── MenuPrincipal.java # Interfaz de consola
├── .gitignore
└── README.md
```

---

##  Requisitos

- Java JDK 8 o superior
- Terminal / Símbolo del sistema

---

##  Compilación y Ejecución

### 1. Compilar (desde la raíz del proyecto)

**Linux / macOS:**
```bash
find src -name "*.java" > sources.txt
javac -d out @sources.txt
```

**Windows:**
```cmd
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
```

### 2. Ejecutar

```bash
java -cp out vista.MenuPrincipal
```

---

##  Diagrama del BST (datos de prueba)

Los 8 libros precargados forman la siguiente estructura:

```
└── García M., Gabriel
    ├── Borges, Jorge L.
    │   ├── Allende, Isabel
    │   └── Cortázar, Julio
    └── Neruda, Pablo
        ├── Mistral, Gabriela
        └── Vargas Ll., Mario
            └── Rulfo, Juan
```

---

##  Funcionalidades

| # | Función | Descripción |
|---|---------|-------------|
| 1 | Registrar libro | Inserta un nuevo libro en el BST |
| 2 | Buscar por autor | Búsqueda eficiente O(log n) |
| 3 | Buscar por ISBN | Recorrido completo del árbol |
| 4 | Eliminar libro | Maneja los 3 casos clásicos del BST |
| 5 | Listado InOrden | Alfabético por autor |
| 6 | Listado PreOrden | Estructura jerárquica |
| 7 | Listado PostOrden | Para operaciones de limpieza |
| 8 | Registrar préstamo | Con nombre del prestatario y fecha |
| 9 | Registrar devolución | Libera el libro para préstamo |
| 10 | Libros disponibles | Filtro por disponibilidad |
| 11 | Libros prestados | Con info del prestatario |
| 12 | Buscar por categoría | Filtro por género |
| 13 | Estadísticas | Total, altura, mínimo, máximo, disponibles |
| 14 | Búsqueda parcial | Por subcadena del nombre del autor |
| 15 | Visualizar árbol | Impresión gráfica del BST en consola |

---

##  Complejidades

| Operación | Promedio | Peor caso |
|-----------|----------|-----------|
| Insertar | O(log n) | O(n) |
| Buscar | O(log n) | O(n) |
| Eliminar | O(log n) | O(n) |
| Recorridos | O(n) | O(n) |
| Mínimo/Máximo | O(log n) | O(n) |

---

##  Capturas de Pantalla

*(Agregar capturas tras ejecutar el sistema)*

---

##  Información del Estudiante

- **Nombre completo:** Juan Rivera
- **Código:** 1029647349
- **Asignatura:** Estructuras de Datos
- **Docente:** Julio Silva
- **Fecha de entrega:** 19/02/2026
