package com.example;

import com.example.main.Main;
import com.example.Repositorio.AutorRepositorio;
import com.example.Repositorio.LibroRepositorio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.main.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example") // Solo una anotación es necesaria
public class LiterAluraApplication {

    @Autowired
    private AutorRepositorio autorRepositorio;  // Inyección de dependencias para AutorRepositorio

    @Autowired
    private LibroRepositorio libroRepositorio;  // Inyección de dependencias para LibroRepositorio

    @Autowired
    private Main main;  // Inyección de la clase Main

    public static void main(String[] args) {
        // Arrancar la aplicación Spring Boot
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    // Método para iniciar la clase Main después de que Spring haya configurado la aplicación
    @PostConstruct
    public void init() {
        main.menu();  // Llama al método menu de Main
    }
}
