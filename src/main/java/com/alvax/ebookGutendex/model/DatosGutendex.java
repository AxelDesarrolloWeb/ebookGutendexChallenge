package com.alvax.ebookGutendex.model;

import java.util.List;

public record DatosGutendex(
        int count,
        String next,
        String previous,
        List<DatosLibro> resultados) {}
