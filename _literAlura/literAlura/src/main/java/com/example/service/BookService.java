package com.example.service;

import com.example.api.GutendexClient;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BookService {

    private final GutendexClient gutendexClient;

    public BookService() {
        this.gutendexClient = new GutendexClient();
    }

    // Método para obtener todos los libros
    public String fetchAllBooks() {
        try {
            return gutendexClient.fetchAllBooks(); // Llamamos a fetchAllBooks() de GutendexClient
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al obtener todos los libros", e);
        }
    }

    // Método para buscar libros por palabra clave
    public String searchBooks(String query) {
        try {
            return gutendexClient.searchBooks(query); // Llamamos a searchBooks() de GutendexClient
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al buscar libros", e);
        }
    }

    // Método para obtener detalles de un libro por ID
    public String fetchBookDetails(int bookId) {
        try {
            return gutendexClient.fetchBookDetails(bookId); // Llamamos a fetchBookDetails() de GutendexClient
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al obtener los detalles del libro", e);
        }
    }
}
