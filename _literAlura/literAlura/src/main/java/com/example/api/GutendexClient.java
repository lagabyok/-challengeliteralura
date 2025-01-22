package com.example.api;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books/"; // URL base
    private final HttpClient client;

    public GutendexClient() {
        this.client = HttpClient.newHttpClient();
    }

    // Método para obtener todos los libros
    public String fetchAllBooks() throws IOException, InterruptedException {
        String url = BASE_URL; // Endpoint para obtener todos los libros

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Error en la solicitud. Código de estado: " + response.statusCode());
        }
    }

    // Método para buscar libros por palabra clave
    public String searchBooks(String searchQuery) throws IOException, InterruptedException {
        try {
            String encodedQuery = URLEncoder.encode(searchQuery, "UTF-8");
            String url = BASE_URL + "?search=" + encodedQuery;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new IOException("Error en la solicitud. Código de estado: " + response.statusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error de codificación o solicitud", e);
        }
    }

    // Método para obtener detalles de un libro específico por ID
    public String fetchBookDetails(int bookId) throws IOException, InterruptedException {
        String url = BASE_URL + "/" + bookId; // Endpoint para obtener detalles del libro

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Error en la solicitud. Código de estado: " + response.statusCode());
        }
    }

    // Método para buscar libros por título
    public String obtenerLibrosPorTitulo(String titulo) throws IOException, InterruptedException {
        try {
            String tituloCodificado = URLEncoder.encode(titulo, "UTF-8");
            String url = BASE_URL + "?search=" + tituloCodificado;

            System.out.println("URL de la API: " + url); // Para depuración

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new IOException("Error en la solicitud. Código de estado: " + response.statusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error de codificación o solicitud", e);
        }
    }

    public static void main(String[] args) {
        try {
            GutendexClient client = new GutendexClient();

            // Obtener todos los libros
            String allBooks = client.fetchAllBooks();
            System.out.println("Todos los libros: ");
            System.out.println(allBooks);

            // Buscar libros con una palabra clave
            String searchResults = client.searchBooks("shakespeare");
            System.out.println("Resultados de búsqueda: ");
            System.out.println(searchResults);

            // Obtener detalles de un libro específico
            String bookDetails = client.fetchBookDetails(1);
            System.out.println("Detalles del libro: ");
            System.out.println(bookDetails);

            // Buscar libros por título
            String librosPorTitulo = client.obtenerLibrosPorTitulo("hamlet");
            System.out.println("Libros encontrados por título: ");
            System.out.println(librosPorTitulo);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
