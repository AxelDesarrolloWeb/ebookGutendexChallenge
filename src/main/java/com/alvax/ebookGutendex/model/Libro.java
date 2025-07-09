package com.alvax.ebookGutendex.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import java.util.OptionalDouble;

@Entity
@Table(name = "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;

    private List<String> autores;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private List<String> temas;
    private String idioma;
    private int descargas;
    private String urlTexto;

@ManyToOne
    @JoinColumn(name = "autores_id")
    private Autor autor;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.id = datosLibro.id();
        this.titulo = datosLibro.title();
        this.autores = datosLibro.autores().stream()
                .map(DatosAutor::name)
                .toList();

        this.temas = datosLibro.subjects();

        this.idioma = !datosLibro.languages().isEmpty() ?
                datosLibro.languages().get(0) : "Desconocido";

        this.descargas = datosLibro.downloadCount();

        this.urlTexto = datosLibro.formats().plainText() != null ?

                datosLibro.formats().plainText() :

                datosLibro.formats().asciiText();

    }



    // Getters

    public int getId() {

        return id;

    }



    public String getTitulo() {

        return titulo;

    }



    public List<String> getAutores() {

        return autores;

    }



    public List<String> getTemas() {

        return temas;

    }



    public String getIdioma() {

        return idioma;

    }



    public int getDescargas() {

        return descargas;

    }



    public String getUrlTexto() {

        return urlTexto;

    }



    public void setId(int id) {

        this.id = id;

    }



    public void setTitulo(String titulo) {

        this.titulo = titulo;

    }



    public void setAutores(List<String> autores) {

        this.autores = autores;

    }



    public void setTemas(List<String> temas) {

        this.temas = temas;

    }



    public void setIdioma(String idioma) {

        this.idioma = idioma;

    }



    public void setDescargas(int descargas) {

        this.descargas = descargas;

    }



    public void setUrlTexto(String urlTexto) {

        this.urlTexto = urlTexto;

    }



    @Override

    public String toString() {

        return "Libro{" +

                "titulo='" + titulo + '\'' +

                ", autores=" + autores +

                ", idioma='" + idioma + '\'' +

                ", descargas=" + descargas +

                '}';

    }

}