package com.alvax.ebookGutendex.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public record DatosAutor(
        String name,
        Integer birth_year,
        Integer death_year) {
    public String nombre() { return name; }
}