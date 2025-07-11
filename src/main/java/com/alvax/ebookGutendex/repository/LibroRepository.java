package com.alvax.ebookGutendex.repository;

import com.alvax.ebookGutendex.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Método corregido para verificar existencia
    boolean existsByTituloAndAutores(String titulo, String autores);

    @Query("SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.temas ORDER BY l.descargas DESC LIMIT 10")
    List<Libro> findTop10ByOrderByDescargasDesc();

    @Query("SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.temas ORDER BY l.descargas DESC")
    List<Libro> findByOrderByDescargasDesc();

    @Query("SELECT DISTINCT l.autores, l.birth_year, l.death_year FROM Libro l ORDER BY l.autores")
    List<Object[]> findDistinctAutoresConAnios();

    @Query("SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.temas WHERE l.idioma = :idioma")
    List<Libro> findByIdioma(@Param("idioma") String idioma);

    @Query("SELECT DISTINCT l FROM Libro l JOIN FETCH l.temas t WHERE LOWER(t) LIKE LOWER(CONCAT('%', :tema, '%'))")
    List<Libro> findByTema(@Param("tema") String tema);

    @Query("SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.temas WHERE l.descargas > :descargas")
    List<Libro> findByDescargasGreaterThan(@Param("descargas") int descargas);

    @Query("SELECT DISTINCT l FROM Libro l WHERE " +
            "l.birth_year <= :anio AND " +
            "l.death_year >= :anio")
    List<Libro> findAutoresVivosEnAnio(@Param("anio") int anio);
}