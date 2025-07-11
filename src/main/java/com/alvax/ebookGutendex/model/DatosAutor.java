package com.alvax.ebookGutendex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DatosAutor(
        @JsonProperty("name") String name,
        @JsonProperty("birth_year") Integer birth_year,
        @JsonProperty("death_year") Integer death_year) {

    public String nombre() { return name; }
    public Integer birth_year() { return birth_year; }
    public Integer death_year() { return death_year; }
}