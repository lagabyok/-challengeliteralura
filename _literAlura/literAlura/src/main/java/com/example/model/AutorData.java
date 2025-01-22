package com.example.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorData(
        @JsonAlias("name") String nombreDelAutor,
        @JsonAlias("birth_year") Integer anioDeNacimiento,
        @JsonAlias("death_year") Integer anioDeFallecimiento
) {
}
