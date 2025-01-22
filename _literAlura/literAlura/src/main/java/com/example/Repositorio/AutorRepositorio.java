package com.example.Repositorio;

import com.example.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE (a.fechaDeFallecimiento >= :anoSeleccionado OR a.fechaDeFallecimiento IS NULL) AND a.fechaDeNacimiento <= :anoSeleccionado")
    List<Autor> buscarPorAnoDeFallecimiento(int anoSeleccionado);

    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    List<Autor> findByNombre(String nombre);

    @Query("SELECT COUNT(a) > 0 FROM Autor a WHERE a.nombre = :nombre")
    boolean existsByNombre(String nombre);
}
