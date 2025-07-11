package com.alvax.ebookGutendex.principal;

import com.alvax.ebookGutendex.model.*;
import com.alvax.ebookGutendex.repository.LibroRepository;
import com.alvax.ebookGutendex.service.ConsumoAPI;
import com.alvax.ebookGutendex.service.ConvierteDatos;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    // CORRECCIÓN: URL correcta para Gutendex API
    private final String URL_BASE = "https://gutendex.com/books?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repository;

    public Principal(LibroRepository repository) {
        this.repository = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 7) {
            var menu = """
                    ==== MENÚ PRINCIPAL ====
                    1. Buscar y guardar libros por título
                    2. Buscar y guardar libros por autor 
                    3. Listar libros registrados
                    4. Listar autores registrados
                    5. Top 10 libros más descargados
                    6. Libros por idioma
                    7. Buscar libros por tema
                    8. Libros con más de 25000 descargas
                    9. Buscar autores vivos de la época
                    10. Salir
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
                    listarLibrosRegistrados();
                    break;
                case 4:
                    listarAutoresRegistrados();
                    break;
                case 5:
                    mostrarTop10Descargados();
                    break;
                case 6:
                    buscarLibrosPorIdioma();
                    break;
                case 7:
                    buscarLibrosPorTema();
                    break;
                case 8:
                    buscarLibrosConMasDe25000Descargas();
                    break;
                case 9:
                    buscarAutoresVivos();
                    break;
                case 10:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void listarAutoresRegistrados() {
//        List<Libro> topLibros = repository.findTop10ByOrderByDescargasDesc();
//        System.out.println("Autores Registrados:");
//        topLibros.forEach(this::mostrarLibro);
    }

    private void buscarAutoresVivos() {
        
    }

    private void listarLibrosRegistrados() {
//        List<Libro> topLibros = repository.findTop10ByOrderByDescargasDesc();
//        System.out.println("Libros Registrados:");
//        topLibros.forEach(this::mostrarLibro);
    }

    private void buscarLibrosPorTitulo() {
        System.out.println("Ingrese el título a buscar:");
        var titulo = teclado.nextLine();
        // CORRECCIÓN: Usar URL correcta y encoding adecuado
        var json = consumoApi.obtenerDatos(URL_BASE + titulo.replace(" ", "%20"));

        // Depuración: Imprimir JSON para verificar respuesta
        System.out.println("Respuesta de la API:\n" + json);

        DatosGutendex respuesta = conversor.obtenerDatos(json, DatosGutendex.class);

        if (respuesta != null && respuesta.results() != null && !respuesta.results().isEmpty()) {
            System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
            for (DatosLibro datosLibro : respuesta.results()) {
                String autoresStr = convertirAutoresAString(datosLibro.autores());

                if (!repository.existsByTituloAndAutores(datosLibro.titulo(), autoresStr)) {
                    Libro libro = new Libro();
                    libro.setTitulo(datosLibro.titulo());
                    libro.setAutores(autoresStr);
                    libro.setTemas(datosLibro.temas());

                    // Guardar años del primer autor (si existe)
                    if (!datosLibro.autores().isEmpty()) {
                        DatosAutor primerAutor = datosLibro.autores().get(0);
                        libro.setBirthYearAutor(primerAutor.birth_year());
                        libro.setDeathYearAutor(primerAutor.death_year());
                    }

                    // CORRECCIÓN: Manejar múltiples idiomas
                    libro.setIdioma(!datosLibro.idiomas().isEmpty() ?
                            datosLibro.idiomas().get(0) : "desconocido");

                    libro.setDescargas(datosLibro.descargas());

                    // CORRECCIÓN: Construir URL usando ID del libro
                    libro.setUrlTexto("https://www.gutenberg.org/ebooks/" +
                            datosLibro.id() + ".txt.utf-8");

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

//    private void buscarAutoresVivosEnAnio() {
//        try {
//            System.out.println("Ingrese el año para ver los autores vivos en ese año:");
//            int anio = teclado.nextInt();
//            teclado.nextLine(); // Limpiar buffer
//
//            List<Libro> libros = repository.findAutoresVivosEnAnio(anio);
//            Set<String> autoresUnicos = new HashSet<>();
//
//            for (Libro libro : libros) {
//                if (libro.getAutores() != null) {
//                    autoresUnicos.add(libro.getAutores());
//                }
//            }
//
//            if (autoresUnicos.isEmpty()) {
//                System.out.println("\nNo se encontraron autores vivos en el año " + anio);
//                return;
//            }
//
//            System.out.println("\n=== AUTORES VIVOS EN EL AÑO " + anio + " ===");
//            autoresUnicos.forEach(System.out::println);
//
//        } catch (InputMismatchException e) {
//            System.out.println("Debe ingresar un número válido");
//            teclado.nextLine(); // Limpiar entrada inválida
//        }
//    }

    private void buscarLibrosPorAutor() {
        System.out.println("Ingrese el nombre del autor:");
        var autor = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + autor.replace(" ", "%20"));

        // Depuración: Imprimir JSON para verificar respuesta
        System.out.println("Respuesta de la API:\n" + json);

        DatosGutendex respuesta = conversor.obtenerDatos(json, DatosGutendex.class);

        if (respuesta != null && respuesta.results() != null && !respuesta.results().isEmpty()) {
            System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
            for (DatosLibro datosLibro : respuesta.results()) {
                String autoresStr = convertirAutoresAString(datosLibro.authors());

                if (!repository.existsByTituloAndAutores(datosLibro.titulo(), autoresStr)) {
                    Libro libro = new Libro();
                    libro.setTitulo(datosLibro.titulo());
                    libro.setAutores(autoresStr);
                    libro.setTemas(datosLibro.temas());

                    // CORRECCIÓN: Manejar múltiples idiomas
                    libro.setIdioma(!datosLibro.idiomas().isEmpty() ?
                            datosLibro.idiomas().get(0) : "desconocido");

                    libro.setDescargas(datosLibro.descargas());

                    // CORRECCIÓN: Construir URL usando ID del libro
                    libro.setUrlTexto("https://www.gutenberg.org/ebooks/" +
                            datosLibro.id() + ".txt.utf-8");

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

    private String convertirAutoresAString(List<DatosAutor> autores) {
        if (autores == null || autores.isEmpty()) return "Anónimo";
        return autores.stream()
                .map(DatosAutor::nombre)
                .collect(Collectors.joining(", "));
    }

    private void mostrarLibro(Libro libro) {
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autor(es): " + libro.getAutores());
        System.out.println("Idioma: " + libro.getIdioma());
        System.out.println("Descargas: " + libro.getDescargas());

        // Manejo seguro de temas
        if (libro.getTemas() != null && !libro.getTemas().isEmpty()) {
            System.out.println("Temas: " + String.join(", ", libro.getTemas()));
        } else {
            System.out.println("Temas: N/A");
        }

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