package com.alvax.ebookGutendex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        String title,
        int id,
        List<DatosAutor> authors,
        List<String> subjects,
        List<String> languages,
        int download_count,
        String formattedTextUrl) {

    public String titulo() { return title; }
    public List<String> temas() { return subjects; }
    public List<String> idiomas() { return languages; }
    public int descargas() { return download_count; }
    public String urlTexto() { return formattedTextUrl; }
}
