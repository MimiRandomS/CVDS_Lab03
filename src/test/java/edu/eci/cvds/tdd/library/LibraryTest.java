package edu.eci.cvds.tdd.library;

import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.loan.Loan;
import edu.eci.cvds.tdd.library.loan.LoanStatus;
import edu.eci.cvds.tdd.library.user.User;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LibraryTest { 
    private Library library;

    @Before
    public void setUp() {
        library = new Library();
    }
  
    /*  
     * Pruebas para el método loanABook
     */

    @Test
    public void shouldCreateLoanSuccessfullyWhenBookIsAvailable() {
        Book book = new Book("El Quijote", "Miguel de Cervantes", "hola");
        User user = new User();
        user.setId("user1");
        user.setName("Juan Pérez");

        library.addBook(book);

        String userId = user.getId();
        String bookIsbn = book.getIsbn();

        Loan loan = library.loanABook(userId, bookIsbn);

        // Validar que el préstamo se haya creado correctamente
        Assert.assertNotNull(loan);
        Assert.assertEquals(LoanStatus.ACTIVE, loan.getStatus());
        Assert.assertEquals(userId, loan.getUser().getId()); 
        Assert.assertEquals(bookIsbn, loan.getBook().getIsbn());  
        Assert.assertNotNull(loan.getLoanDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenBookIsNotAvailable() {
        Book book = new Book("El Quijote", "Miguel de Cervantes", "hola");
        User user = new User();
        user.setId("user2");
        user.setName("Ana Gómez");

        String userId = user.getId();
        String bookIsbn = book.getIsbn();

        library.loanABook(userId, bookIsbn);  // Lanza excepción porque el libro no está en la biblioteca
    }

    /*  
     * Pruebas para el método addBook
     */
      
    @Test
    public void shouldAddNewBookToSystem() {
        Book book1 = new Book("La guerra de los mundos", "H.G. Wells", "1");
        Library biblioteca = new Library();

        boolean result = biblioteca.addBook(book1);
        assertTrue(result);
    }

    @Test
    public void shouldNotAddNewBookNullValueToSystem() {
        Library biblioteca = new Library();

        assertThrows(IllegalArgumentException.class, () -> {
            biblioteca.addBook(null);
        });
    }

    @Test
    public void shouldAddBookExistInTheSystem() {
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
