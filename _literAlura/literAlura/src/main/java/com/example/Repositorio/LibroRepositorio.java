package com.example.Repositorio;

import com.example.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Libro;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {

    boolean existsByNombre(String nombre);

    @Query("SELECT DISTINCT b.idioma FROM Libro b ORDER BY b.idioma")
    List<String> buscarIdiomas();

    @Query("SELECT b FROM Libro b WHERE b.idioma = :idiomaSelecionado")
    List<Libro> buscarPorIdioma(String idiomaSelecionado);

    Libro findByNombre(String nombre);

    List<Libro> findTop10ByOrderByCantidadDeDescargasDesc();

    @Query("SELECT b FROM Libro b WHERE b.autor.nombre ILIKE %:pesquisa%")
    List<Libro> findByAutorNombre(String pesquisa);

}
