package com.example.controller;

import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    // Endpoint para obtener todos los libros
    @GetMapping("/books")
    public String fetchAllBooks() {
        return bookService.fetchAllBooks();
    }

    // Endpoint para buscar libros por palabra clave
    @GetMapping("/search")
    public String searchBooks(@RequestParam String q) {
        return bookService.searchBooks(q);
    }

    // Endpoint para obtener detalles de un libro por ID
    @GetMapping("/books/{id}")
    public String fetchBookDetails(@RequestParam int id) {
        return bookService.fetchBookDetails(id);
    }
}
