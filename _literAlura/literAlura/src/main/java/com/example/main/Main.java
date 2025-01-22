package com.example.main;

import com.example.service.BookService;
import com.example.api.GutendexClient;
import com.example.model.LibroData;
import com.example.model.AutorData;
import com.example.model.Autor;
import com.example.model.Libro;
import com.example.Repositorio.AutorRepositorio;
import com.example.Repositorio.LibroRepositorio;
import com.example.service.DataConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class Main {

        private static final String MENU = """
            **********************************************
            1 - Busca libro por título
            2 - Lista libros registrados
            3 - Lista autores registrados
            4 - Lista autores vivos en año elegido
            5 - Lista libros en idioma elegido
            6 - Top 10 de libros
            7 - Buscar por nombre de autores
            8 - Promedio de descargas por autor

            0 - Salir
            **********************************************
            """;

        private Scanner scanner = new Scanner(System.in);
        private GutendexClient gutendexClient = new GutendexClient();
        private AutorRepositorio autorRepositorio;
        private LibroRepositorio libroRepositorio;
        private DataConverter dataConverter = new DataConverter();

        public Main(AutorRepositorio autorRepositorio, LibroRepositorio libroRepositorio) {
                this.autorRepositorio = autorRepositorio;
                this.libroRepositorio = libroRepositorio;
        }

        public void menu() {
                int option;
                do {
                        printMenu();
                        option = getUserInput();
                        handleOption(option);
                } while (option != 0);
        }

        private void printMenu() {
                System.out.println(MENU);
        }

        private int getUserInput() {
                int option = scanner.nextInt();
                scanner.nextLine(); // consumir la nueva línea restante
                return option;
        }

        private void handleOption(int option) {
                switch (option) {
                        case 1 -> buscarNuevoLibro();
                        case 2 -> buscarLibrosRegistrados();
                        case 3 -> buscarAutoresRegistrados();
                        case 4 -> buscarAutoresVivosPorAño();
                        case 5 -> buscarLibrosPorIdioma();
                        case 6 -> buscarTop10();
                        case 7 -> buscarAutorPorNombre();
                        case 8 -> promedioDeDescargasPorAutor();
                        case 0 -> System.out.println("Saliendo...");
                        default -> System.out.println("\n\n*** Opción Inválida ***\n\n");
                }
        }

        private void buscarNuevoLibro() {
                System.out.println("\n¿Qué libro deseas buscar?");
                String userSearch = scanner.nextLine();
                try {
                        // Reemplaza fetchBooks con el método adecuado como searchBooks
                        String response = gutendexClient.searchBooks(userSearch);
                        guardarEnDb(response);
                } catch (IOException | InterruptedException e) {
                        System.err.println("Error al consumir la API: " + e.getMessage());
                }
        }

        private void guardarEnDb(String data) {
                try {
                        // Deserializando correctamente los datos JSON
                        LibroData libroData = dataConverter.getData(data, LibroData.class);
                        AutorData autorData = dataConverter.getData(data, AutorData.class);

                        // Creación de los objetos Autor y Libro a partir de los datos obtenidos
                        Autor autor = new Autor(autorData);
                        Libro libro = new Libro(libroData);

                        // Verificación si el autor ya está registrado (corregido)
                        Autor autorDb = autorRepositorio.existsByNombre(autor.getNombre())
                                ? autorRepositorio.findByNombre(autor.getNombre()).stream().findFirst().orElse(null)
                                : autorRepositorio.save(autor);

                        // Verificación si el libro ya está registrado
                        Libro libroDb = libroRepositorio.existsByNombre(libro.getNombre())
                                ? libroRepositorio.findByNombre(libro.getNombre())
                                : libroRepositorio.save(libro);

                        libroDb.setAutor(autorDb);

                        System.out.println(libroDb);
                } catch (NullPointerException e) {
                        System.out.println("\n\n*** Libro no encontrado ***\n\n");
                }
        }

        private void buscarLibrosRegistrados() {
                var librosDb = libroRepositorio.findAll();
                if (!librosDb.isEmpty()) {
                        System.out.println("\nLibros registrados en la base de datos:");
                        librosDb.forEach(System.out::println);
                } else {
                        System.out.println("\n¡No se ha encontrado ningun libro en la base de datos!");
                }
        }

        private void buscarAutoresRegistrados() {
                var autoresDb = autorRepositorio.findAll();
                if (!autoresDb.isEmpty()) {
                        System.out.println("\nAutores registrados en la base de datos:");
                        autoresDb.forEach(System.out::println);
                } else {
                        System.out.println("\n¡No se ha encontrado ningun autor en la base de datos!");
                }
        }

        private void buscarAutoresVivosPorAño() {
                System.out.println("\n¿En qué año desea buscar?");
                int yearSelected = scanner.nextInt();
                scanner.nextLine();
                var autoresDb = autorRepositorio.buscarPorAnoDeFallecimiento(yearSelected);
                if (!autoresDb.isEmpty()) {
                        System.out.println("\n\nAutores vivos en el año: " + yearSelected);
                        autoresDb.forEach(System.out::println);
                } else {
                        System.out.println("\n¡No se ha encontrado ningun autor para esta fecha!");
                }
        }

        private void buscarLibrosPorIdioma() {
                var idiomasDb = libroRepositorio.buscarIdiomas();
                System.out.println("\nIdiomas registrados en la base de datos:");
                idiomasDb.forEach(System.out::println);
                System.out.println("\nSeleccione uno de los idiomas registrados en la base de datos:\n");
                String selectedIdioma = scanner.nextLine();
                libroRepositorio.buscarPorIdioma(selectedIdioma).forEach(System.out::println);
        }

        private void buscarTop10() {
                var top10Books = libroRepositorio.findTop10ByOrderByCantidadDeDescargasDesc();
                top10Books.forEach(System.out::println);
        }

        private void buscarAutorPorNombre() {
                System.out.println("¿Cuál es el nombre del autor?");
                String autorSearch = scanner.nextLine();
                List<Autor> autores = autorRepositorio.findByNombre(autorSearch);
                if (!autores.isEmpty()) {
                        autores.forEach(System.out::println);
                } else {
                        System.out.println("*** Autor no encontrado ***");
                }
        }

        private void promedioDeDescargasPorAutor() {
                System.out.println("¿Qué autor desea buscar?");
                String autorSearch = scanner.nextLine();
                var librosPorAutor = libroRepositorio.findByAutorNombre(autorSearch);
                if (!librosPorAutor.isEmpty()) {
                        var stats = librosPorAutor.stream()
                                .mapToInt(Libro::getCantidadDeDescargas)
                                .summaryStatistics();
                        System.out.println("Promedio de descargas: " + stats.getAverage());
                } else {
                        System.out.println("*** Autor no encontrado ***");
                }
        }
}
