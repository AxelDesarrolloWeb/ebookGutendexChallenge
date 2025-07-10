package com.alvax.ebookGutendex.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// DatosGutendex.java
public record DatosGutendex(
        @JsonProperty("count") int count,
        @JsonProperty("next") String next,
        @JsonProperty("previous") String previous,
        @JsonProperty("results") List<DatosLibro> results) {}
