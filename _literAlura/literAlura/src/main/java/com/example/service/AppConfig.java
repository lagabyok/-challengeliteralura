package com.example.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.api.GutendexClient;
import com.example.Repositorio.AutorRepositorio;
import com.example.Repositorio.LibroRepositorio;

@Configuration
public class AppConfig {



    @Bean
    public DataConverter dataConverter() {
        return new DataConverter();
    }




}
