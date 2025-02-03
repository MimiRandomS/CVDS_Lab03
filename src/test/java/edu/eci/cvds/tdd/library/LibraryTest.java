package edu.eci.cvds.tdd.library;

import edu.eci.cvds.tdd.library.Library;
import edu.eci.cvds.tdd.library.book.Book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LibraryTest { 

    @Test
    public void shouldAddNewBookToSystem() {
        // Crear instancias de prueba
        Book book1 = new Book("La guerra de los mundos", "H.G. Wells", "1");
        Book book2 = new Book("Harry Potter y la piedra filosofal", "J.K. Rowling", "2");
        Library biblioteca = new Library();

        // Agregar libro a la biblioteca
        boolean result = biblioteca.addBook(book1);
        assertTrue(result);
    }

    @Test
    public void shouldNotAddNewBookNullValueToSystem() {
        // Crear instancias de prueba
        Library biblioteca = new Library();

        // Agregar null a la biblioteca
        boolean result = biblioteca.addBook(null);
        assertFalse(result);
    }
}
