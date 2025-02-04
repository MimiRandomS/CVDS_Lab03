package edu.eci.cvds.tdd.library;

import java.util.Map;
import edu.eci.cvds.tdd.library.Library;
import edu.eci.cvds.tdd.library.book.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LibraryTest { 

    @Test
    public void shouldAddNewBookToSystem() {
        // Crear instancias de prueba
        Book book1 = new Book("La guerra de los mundos", "H.G. Wells", "1");
        Library biblioteca = new Library();

        // Agregar libro a la biblioteca
        boolean result = biblioteca.addBook(book1);
        assertTrue(result);
    }

    @Test
    public void shouldNotAddNewBookNullValueToSystem() {
        // Crear instancias de prueba
        Library biblioteca = new Library();

        // prueba error
        assertThrows(IllegalArgumentException.class, () -> {
            biblioteca.addBook(null);
        });
    }

    @Test
    public void shouldAddBookExistInTheSystem() {
        // Crear instancias de prueba
        Library biblioteca = new Library();
        Book book1 = new Book("La guerra de los mundos", "H.G. Wells", "1");
        
        boolean result1 = biblioteca.addBook(book1);
        boolean result2 = biblioteca.addBook(book1); 
        boolean result = result1 && result2;

        assertTrue(result);

        Map<Book, Integer> books = biblioteca.getBooks();
        Integer numBooks = books.get(book1); 
        
        assertTrue(numBooks != null && numBooks == 2);
    }
}
