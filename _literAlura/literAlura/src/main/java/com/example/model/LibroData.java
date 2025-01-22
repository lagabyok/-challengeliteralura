package com.example.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroData(
        @JsonAlias("title") String nombreDelLibro,
        @JsonAlias("download_count") Integer cantidadDeDescargas,
        @JsonAlias("languages") List<String> idiomas
) {
}
