package com.alvax.ebookGutendex.repository;

import com.alvax.ebookGutendex.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Buscar por título (insensible a mayúsculas)
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    // Buscar por autor (búsqueda en el string de autores)
    @Query("SELECT l FROM Libro l WHERE LOWER(l.autores) LIKE LOWER(CONCAT('%', :autor, '%'))")
    List<Libro> findByAutor(@Param("autor") String autor);

    // Top 10 libros más descargados
    List<Libro> findTop10ByOrderByDescargasDesc();

    // Libros por idioma
    List<Libro> findByIdioma(String idioma);

    // Buscar libros por tema
    @Query("SELECT l FROM Libro l JOIN l.temas t WHERE LOWER(t) LIKE LOWER(CONCAT('%', :tema, '%'))")
    List<Libro> findByTema(@Param("tema") String tema);

    // Libros con más de X descargas
    List<Libro> findByDescargasGreaterThan(int descargas);

    // Verificar si un libro ya existe
 boolean existsByTituloAndAutores(String titulo, String autores);
}