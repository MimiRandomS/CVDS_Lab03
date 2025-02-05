package edu.eci.cvds.tdd.library;

import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.loan.Loan;
import edu.eci.cvds.tdd.library.loan.LoanStatus;
import edu.eci.cvds.tdd.library.user.User;
import java.time.LocalDateTime;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibraryTest { 
    private Library library;

    @BeforeEach
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
        assertNotNull(loan);
        assertEquals(LoanStatus.ACTIVE, loan.getStatus());
        assertEquals(userId, loan.getUser().getId()); 
        assertEquals(bookIsbn, loan.getBook().getIsbn());  
        assertNotNull(loan.getLoanDate());
    }

    @Test
    public void shouldThrowExceptionWhenBookIsNotAvailable() {
        Book book = new Book("El Quijote", "Miguel de Cervantes", "hola");
        User user = new User();
        user.setId("user2");
        user.setName("Ana Gómez");
    
        String userId = user.getId();
        String bookIsbn = book.getIsbn();
    
        assertThrows(IllegalArgumentException.class, () -> {
            library.loanABook(userId, bookIsbn);
        });
    } 
    
    /*  
     * Pruebas para el método addBook
     */
    @Test
    public void shouldAddNewBookToSystem() {
        Book book1 = new Book("La guerra de los mundos", "H.G. Wells", "1");

        boolean result = library.addBook(book1);
        assertTrue(result);
    }

    @Test
    public void shouldNotAddNewBookNullValueToSystem() {

        assertThrows(IllegalArgumentException.class, () -> {
            library.addBook(null);
        });
    }

    @Test
    public void shouldAddBookExistInTheSystem() {
        Book book1 = new Book("La guerra de los mundos", "H.G. Wells", "1");
        
        boolean result1 = library.addBook(book1);
        boolean result2 = library.addBook(book1); 
        boolean result = result1 && result2;

        assertTrue(result);

        Map<Book, Integer> books = library.getBooks();
        Integer numBooks = books.get(book1); 
        
        assertTrue(numBooks != null && numBooks == 2);
    }

    /*  
     * Pruebas para el método returnLoan
     */
    @Test
    public void shouldNotReturnALoanThatDoesNotExist() {
        Book book = new Book("El Quijote", "Miguel de Cervantes", "hola");
        User user = new User();
        user.setId("user1");
        user.setName("Juan Pérez");

        library.addBook(book);
        library.addBook(book);

        // crer un préstamo que no existe en library
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setLoanDate(LocalDateTime.now());

        // intentar devolver un prestamo que no existe
        assertThrows(IllegalArgumentException.class, () -> {
            library.returnLoan(loan);
        });

        // intentar devolver un prestamo nulo
        assertThrows(IllegalArgumentException.class, () -> {
            library.returnLoan(null);
        });
    }

    @Test
    public void shouldReturnALoanSuccessfully() {
        Book book = new Book("El Quijote", "Miguel de Cervantes", "hola");
        User user = new User();
        user.setId("user1");
        user.setName("Juan Pérez");
    
        library.addBook(book);
        library.addBook(book);
    
        String userId = user.getId();
        String bookIsbn = book.getIsbn();
    
        Loan loan = library.loanABook(userId, bookIsbn);
    
        Loan returnedLoan = library.returnLoan(loan);
    
        // Validar que el préstamo se haya devuelto correctamente
        assertNotNull(returnedLoan);
        assertEquals(LoanStatus.RETURNED, returnedLoan.getStatus());
        assertEquals(userId, returnedLoan.getUser().getId()); 
        assertEquals(bookIsbn, returnedLoan.getBook().getIsbn());  
        assertNotNull(returnedLoan.getReturnDate());
        assertEquals(2, library.getBooks().get(book));
    }
    
}

