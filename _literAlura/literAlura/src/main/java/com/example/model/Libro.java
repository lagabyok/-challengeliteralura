package com.example.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String idioma;

    @Column(name = "cantidad_de_descargas", nullable = false)
    private Integer cantidadDeDescargas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    public Libro(LibroData data) {
        this.nombre = data.nombreDelLibro();
        this.idioma = String.join(",", data.idiomas());
        this.cantidadDeDescargas = data.cantidadDeDescargas();
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCantidadDeDescargas() {
        return cantidadDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\n---------------------------------------" +
                "\nNombre: " + nombre +
                "\nIdioma: " + idioma +
                "\nAutor: " + (autor != null ? autor.getNombre() : "Desconocido") +
                "\nCantidad de Descargas: " + cantidadDeDescargas;
    }
}
