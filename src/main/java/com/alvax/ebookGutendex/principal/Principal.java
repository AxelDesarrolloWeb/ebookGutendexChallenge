package com.alvax.ebookGutendex.principal;

import com.alvax.ebookGutendex.model.*;
import com.alvax.ebookGutendex.repository.LibroRepository;
import com.alvax.ebookGutendex.service.ConsumoAPI;
import com.alvax.ebookGutendex.service.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repository;

    public Principal() {
        this.repository = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 7) {
            var menu = """
                    ==== MENÚ PRINCIPAL ====
                    1. Buscar libros por título
                    2. Buscar libros por autor
                    3. Top 10 libros más descargados
                    4. Libros por idioma
                    5. Buscar libros por tema
                    6. Libros con más de 25000 descargas
                    7. Salir
                    Seleccione una opción:""";
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibrosPorTitulo();
                    break;
                case 2:
                    buscarLibrosPorAutor();
                    break;
                case 3:
                    mostrarTop10Descargados();
                    break;
                case 4:
                    buscarLibrosPorIdioma();
                    break;
                case 5:
                    buscarLibrosPorTema();
                    break;
                case 6:
                    buscarLibrosConMasDe25000Descargas();
                    break;
                case 7:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibrosPorTitulo() {
        System.out.println("Ingrese el título a buscar:");
        var titulo = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + titulo.replace(" ", "%20"));
        DatosGutendex respuesta = conversor.obtenerDatos(json, DatosGutendex.class);

        if (respuesta != null && respuesta.resultados() != null) {
            System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
            for (DatosLibro datosLibro : respuesta.resultados()) {
                // Convertir lista de autores a string
                String autoresStr = convertirAutoresAString(datosLibro.authors());

                // Verificar si ya existe (CORREGIDO)
                if (!repository.existsByTituloAndAutores(datosLibro.titulo(), autoresStr)) {
                    // Crear y guardar libro
                    Libro libro = new Libro();
                    libro.setTitulo(datosLibro.titulo());
                    libro.setAutores(autoresStr); // Ahora es String
                    libro.setTemas(datosLibro.temas());
                    libro.setIdioma(!datosLibro.idiomas().isEmpty() ? datosLibro.idiomas().get(0) : "desconocido");
                    libro.setDescargas(datosLibro.descargas());
                    libro.setUrlTexto(datosLibro.urlTexto());

                    repository.save(libro);
                    mostrarLibro(libro);
                } else {
                    System.out.println("Libro ya existe en la base de datos: " + datosLibro.titulo());
                }
                System.out.println("----------------------------------");
            }
        } else {
            System.out.println("No se encontraron libros para: " + titulo);
        }
    }

    private void buscarLibrosPorAutor() {
        System.out.println("Ingrese el nombre del autor:");
        var autor = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + autor.replace(" ", "%20"));
        DatosGutendex respuesta = conversor.obtenerDatos(json, DatosGutendex.class);

        if (respuesta != null && respuesta.resultados() != null) {
            System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
            for (DatosLibro datosLibro : respuesta.resultados()) {
                // Convertir lista de autores a string
                String autoresStr = convertirAutoresAString(datosLibro.authors());

                // Verificar si ya existe
                if (!repository.existsByTituloAndAutores(datosLibro.titulo(), autoresStr)) {
                    // Crear y guardar libro
                    Libro libro = new Libro();
                    libro.setTitulo(datosLibro.titulo());
                    libro.setAutores(autoresStr);
                    libro.setTemas(datosLibro.temas());
                    libro.setIdioma(!datosLibro.idiomas().isEmpty() ? datosLibro.idiomas().get(0) : "desconocido");
                    libro.setDescargas(datosLibro.descargas());
                    libro.setUrlTexto(datosLibro.urlTexto());

                    repository.save(libro);
                    mostrarLibro(libro);
                } else {
                    System.out.println("Libro ya existe en la base de datos: " + datosLibro.titulo());
                }
                System.out.println("----------------------------------");
            }
        } else {
            System.out.println("No se encontraron libros para el autor: " + autor);
        }
    }

    // Método para convertir lista de autores a string
    private String convertirAutoresAString(List<DatosAutor> autores) {
        if (autores == null || autores.isEmpty()) return "Anónimo";
        return autores.stream()
                .map(a -> a.nombre())
                .collect(Collectors.joining(", "));
    }

    private void mostrarLibro(Libro libro) {
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autor(es): " + libro.getAutores());
        System.out.println("Idioma: " + libro.getIdioma());
        System.out.println("Descargas: " + libro.getDescargas());
        System.out.println("Temas: " + String.join(", ", libro.getTemas()));
        System.out.println("URL de texto: " + libro.getUrlTexto());
    }

    // Métodos para las otras opciones (consultas a la base de datos)
    private void mostrarTop10Descargados() {
        List<Libro> topLibros = repository.findTop10ByOrderByDescargasDesc();
        System.out.println("\n=== TOP 10 LIBROS MÁS DESCARGADOS ===");
        topLibros.forEach(this::mostrarLibro);
    }

    private void buscarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma (código de 2 letras):");
        var idioma = teclado.nextLine();
        List<Libro> libros = repository.findByIdioma(idioma.toLowerCase());
        System.out.println("\n=== LIBROS EN " + idioma.toUpperCase() + " ===");
        libros.forEach(this::mostrarLibro);
    }

    private void buscarLibrosPorTema() {
        System.out.println("Ingrese el tema a buscar:");
        var tema = teclado.nextLine();
        List<Libro> libros = repository.findByTema(tema);
        System.out.println("\n=== LIBROS SOBRE " + tema.toUpperCase() + " ===");
        libros.forEach(this::mostrarLibro);
    }

    private void buscarLibrosConMasDe25000Descargas() {
        List<Libro> libros = repository.findByDescargasGreaterThan(25000);
        System.out.println("\n=== LIBROS CON MÁS DE 25000 DESCARGAS ===");
        libros.forEach(this::mostrarLibro);
    }
}