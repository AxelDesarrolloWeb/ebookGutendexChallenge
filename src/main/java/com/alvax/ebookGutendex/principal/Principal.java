package com.alvax.ebookGutendex.principal;

import com.alvax.ebookGutendex.model.Autor;
import com.alvax.ebookGutendex.model.DatosLibro;
import com.alvax.ebookGutendex.model.DatosResultados;
import com.alvax.ebookGutendex.model.Libro;
import com.alvax.ebookGutendex.repository.libroRepository;
import com.alvax.ebookGutendex.service.ConsumoAPI;
import com.alvax.ebookGutendex.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final String BASE_URL = "https://gutendex.com/books";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private libroRepository repository;
    private Scanner scanner = new Scanner(System.in);

    public void muestraElMenu() {
        int opcion;
        do {
            System.out.println("\n==== MENÚ PRINCIPAL ====");
            System.out.println("1. Buscar libros por título para guardarlos");
            System.out.println("2. Buscar libros por autor para guardarlos");
            System.out.println("3. Top 10 libros más descargados");
            System.out.println("4. Libros por idioma");
            System.out.println("5. Buscar libros por tema");
            System.out.println("6. Libros con más de 25000 descargas");
            System.out.println("7. Listar autores registrados");
            System.out.println("8. Listar autores vivos en un determinado año");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    buscarPorTitulo();
                    break;
                case 2:
                    buscarPorAutor();
                    break;
                case 3:
                    top10Descargados();
                    break;
                case 4:
                    librosPorIdioma();
                    break;
                case 5:
                    buscarPorTema();
                    break;
                case 6:
                    librosPopulares();
                    break;
                case 7:
                    autoresRegistrados();
                    break;
                case 8:
                    autoresVivos();
                    break;
                case 9:
                    System.out.println("¡Gracias por usar el buscador de libros!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 7);
    }


    private void buscarPorTitulo() {
        System.out.print("Ingrese el título a buscar: ");
        String titulo = scanner.nextLine();
        List<Libro> libros = repository.librosPorSuTitulo(titulo);
        String url = BASE_URL + "?search=" + titulo.replace(" ", "%20");
        mostrarResultados(url);
    }

    private void buscarPorAutor() {
        System.out.print("Ingrese el nombre del autor: ");
        String autor = scanner.nextLine();
        String url = BASE_URL + "?search=" + autor.replace(" ", "%20") + "&author_year_start=1800";
        mostrarResultados(url);

        // ******QUEDASTE AQUÍ******
        List<Libro> libros = repository.librosPorAutor(autor);
    }

    private void top10Descargados() {
        String url = BASE_URL + "?sort=popular";
        String json = consumoAPI.obtenerDatos(url);
        DatosResultados datos = conversor.obtenerDatos(json, DatosResultados.class);

        List<Libro> libros = convertirALibros(datos.resultados());

        List<Libro> top10 = libros.stream()
                .limit(10)
                .sorted(Comparator.comparing(Libro::getDescargas).reversed())
                .collect(Collectors.toList());

        System.out.println("\n=== TOP 10 LIBROS MÁS DESCARGADOS ===");
        top10.forEach(libro ->
                System.out.printf("%s (%d descargas)%n", libro.getTitulo(), libro.getDescargas())
        );


        libros = repository.top10LibrosDescargados();

        // Mostrar estadísticas del top 10
        mostrarEstadisticas(top10);
    }

    private void librosPorIdioma() {
        System.out.print("Ingrese el código de idioma (ej: en, fr): ");
        String idioma = scanner.nextLine();
        String url = BASE_URL + "?languages=" + idioma;


        List<Libro> libros = repository.librosPorIdioma(idioma);
        mostrarResultados(url);
    }

    private void buscarPorTema() {
        System.out.print("Ingrese un tema (ej: Fiction, Science, etc...): ");
        String tema = scanner.nextLine();
        String url = BASE_URL + "?topic=" + tema.replace(" ", "%20");
        mostrarResultados(url);
    }

    private void librosPopulares() {
        String url = BASE_URL + "?mime_type=text%2Fplain";
        String json = consumoAPI.obtenerDatos(url);
        DatosResultados datos = conversor.obtenerDatos(json, DatosResultados.class);

//        List<Libro> libros = convertirALibros(datos.resultados());

        List<Libro> libros = repository.librosMuyPopulares();

        List<Libro> populares = libros.stream()
                .filter(libro -> libro.getDescargas() > 25000)
                .sorted(Comparator.comparing(Libro::getDescargas).reversed())
                .collect(Collectors.toList());

        System.out.println("\n=== LIBROS POPULARES (25000+ DESCARGAS) ===");
        populares.forEach(libro ->
                System.out.printf("%s - %d descargas%n", libro.getTitulo(), libro.getDescargas())
        );

        // Mostrar estadísticas de libros populares
        mostrarEstadisticas(populares);
    }

    // Método para buscar autores vivos en una determinada época:
    private void autoresVivos() {
        System.out.print("Ingrese un año para listar los autores vivos de la época");
        String fechaAutorInicio = scanner.nextLine();
        String fechaAutorFin = scanner.nextLine();
        String url = BASE_URL+"?author_year_start="+fechaAutorInicio+"&author_year_end="+fechaAutorFin;

        List<Autor> autor = repository.autoresVivosDerterminadaEpoca(fechaAutorInicio,fechaAutorFin);
        mostrarResultados(url);
    }

    private void autoresRegistrados() {

    }

    private void mostrarEstadisticas(List<Libro> libros) {
        // Filtrar libros con descargas válidas (>0)
        DoubleSummaryStatistics est = libros.stream()
                .filter(libro -> libro.getDescargas() > 0)
                .collect(Collectors.summarizingDouble(Libro::getDescargas));

        System.out.println("\n=== ESTADÍSTICAS ===");
        System.out.println("• Libros encontrados: " + libros.size());
        System.out.println("• Cantidad media de descargas: " + String.format("%.2f", est.getAverage()));
        System.out.println("• Cantidad máxima de descargas: " + est.getMax());
        System.out.println("• Cantidad mínima de descargas: " + est.getMin());
        System.out.println("• Registros evaluados: " + est.getCount());
        System.out.println("• Total de descargas: " + est.getSum());
        System.out.println("==================================\n");
    }

    private void mostrarResultados(String url) {
        String json = consumoAPI.obtenerDatos(url);
        DatosResultados datos = conversor.obtenerDatos(json, DatosResultados.class);

        List<Libro> libros = convertirALibros(datos.resultados());

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros.");
            return;
        }

        System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
        libros.forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor(es): " + String.join(", ", libro.getAutores()));
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("Temas: " + String.join(", ", libro.getTemas()));
            System.out.println("URL de texto: " + libro.getUrlTexto());
            System.out.println("----------------------------------");
        });

        // Nueva sección de estadísticas
        mostrarEstadisticas(libros);
    }

    private List<Libro> convertirALibros(List<DatosLibro> datosLibros) {
        return datosLibros.stream()
                .map(Libro::new)
                .collect(Collectors.toList());
    }
}