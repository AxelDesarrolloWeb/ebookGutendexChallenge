package com.alvax.ebookGutendex.model;

import jakarta.persistence.*;
import java.util.List;


@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private List<String> autores; // Almacenar como texto separado por comas

    @ElementCollection
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
        this.autores = datosLibro.authors().stream()
                .map(DatosAutor::name)
                .toList();

        this.temas = datosLibro.subjects();

        this.idioma = !datosLibro.languages().isEmpty() ?
                datosLibro.languages().get(0) : "Desconocido";

        this.descargas = datosLibro.download_count();

        this.urlTexto = datosLibro.formattedTextUrl();

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



    public void setAutores(String autoresStr) {

        this.autores = autores;

    }
//    public void setAutores(String autoresStr) {
//    }


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