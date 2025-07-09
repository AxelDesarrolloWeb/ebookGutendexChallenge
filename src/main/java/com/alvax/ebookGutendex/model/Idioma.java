package com.alvax.ebookGutendex.model;

public enum Idioma {
    EN("Inglés"),
    ES("Español"),
    FR("Francés"),
    DE("Alemán"),
    UNKNOWN("Desconocido");

    private final String nombre;

    Idioma(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
