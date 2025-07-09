package com.alvax.ebookGutendex.repository;

import com.alvax.ebookGutendex.model.Autor;
import com.alvax.ebookGutendex.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface libroRepository extends JpaRepository<Libro, Long>{
    @Query("SELECT l FROM Libro s WHERE l.titulo = :titulo")
    List<Libro> librosPorSuTitulo(String titulo);

    // Corregir consulta para seleccionar en base al nombre del autor:
    @Query("SELECT titulo t FROM Libro l INNER JOIN Autor a ON n.autores_id = a.autores_id")
    List<Libro> librosPorAutor(String autor);

    @Query("SELECT titulo, descargas FROM Libro  ORDER BY descargas DESC LIMIT 10")
    List<Libro> top10LibrosDescargados();

    @Query("SELECT titulo FROM Libro s WHERE s.idioma = :idioma")
    List<Libro> librosPorIdioma(String idioma);

//    @Query("SELECT l FROM Libro s WHERE l.idioma = :idioma")
//    List<Libro> librosPorCantidadDescargas(String idioma);

    @Query("SELECT l COUNT(*) FROM Libro s WHERE l.descargas > 25000 ORDER BY descargas DESC")
    List<Libro> librosMuyPopulares();

    @Query("SELECT nombre FROM Autor a WHERE a BETWEEN :fechaAutorInicio AND :fechaAutorFin")
    List<Autor> autoresVivosDerterminadaEpoca(String fechaAutorInicio, String fechaAutorFin);


    Optional<Autor> findByNombre(String nombre);
}
