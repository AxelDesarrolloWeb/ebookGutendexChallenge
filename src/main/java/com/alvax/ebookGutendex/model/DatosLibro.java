package com.alvax.ebookGutendex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
// DatosLibro.java
public record DatosLibro(
        @JsonProperty("id") int id,
        @JsonProperty("title") String title,
        @JsonProperty("authors") List<DatosAutor> authors,
        @JsonProperty("subjects") List<String> subjects,
        @JsonProperty("languages") List<String> languages,
        @JsonProperty("download_count") int download_count,
        @JsonProperty("birth_year") int birth_year,
        @JsonProperty("death_year") int death_year) {

    public String titulo() { return title; }
    public List<DatosAutor> autores() { return authors; }
    public List<String> temas() { return subjects; }
    public List<String> idiomas() { return languages; }
    public int descargas() { return download_count; }
    public int birth_year() { return birth_year; }
    public int death_year() { return death_year; }
    public int id() { return id; } // AÑADIDO: Getter para ID
}
