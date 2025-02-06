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

    /* Pruebas para el método loanABook */
    @Test
    void shouldCreateLoanSuccessfullyWhenBookIsAvailable() {
        Book book = new Book("El Quijote", "Miguel de Cervantes", "123");
        User user = new User();
        user.setId("user1");
        user.setName("Juan Pérez");

        library.addUser(user);
        library.addBook(book);

        String userId = user.getId();
        String bookIsbn = book.getIsbn();

        Loan loan = library.loanABook(userId, bookIsbn);

        assertNotNull(loan);
        assertEquals(LoanStatus.ACTIVE, loan.getStatus());
        assertEquals(userId, loan.getUser().getId());
        assertEquals(bookIsbn, loan.getBook().getIsbn());
        assertNotNull(loan.getLoanDate());
        assertEquals(Integer.valueOf(0), library.getBooks().get(book));
    }

    @Test
    public void shouldThrowExceptionWhenBookIsNotAvailable() {
        Book book = new Book("El Quijote", "Miguel de Cervantes", "123");
        User user = new User();
        user.setId("user2");
        user.setName("Ana Gómez");

        library.addUser(user);

        String userId = user.getId();
        String bookIsbn = book.getIsbn();

        assertThrows(IllegalArgumentException.class, () -> {
            library.loanABook(userId, bookIsbn);
        });
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        Book book = new Book("1984", "George Orwell", "456");
        library.addBook(book);

        String userId = "non_existent_user";
        String bookIsbn = book.getIsbn();

        assertThrows(IllegalArgumentException.class, () -> {
            library.loanABook(userId, bookIsbn);
        });
    }

    @Test
    void shouldNotAllowUserToLoanSameBookTwice() {
        Book book = new Book("Cien Años de Soledad", "Gabriel García Márquez", "789");
        User user = new User();
        user.setId("user3");
        user.setName("Carlos Ruiz");

        library.addUser(user);
        library.addBook(book);

        String userId = user.getId();
        String bookIsbn = book.getIsbn();

        library.loanABook(userId, bookIsbn);

        assertThrows(IllegalArgumentException.class, () -> {
            library.loanABook(userId, bookIsbn);
        });
    }

    @Test
    void shouldAllowLoanWhenMultipleCopiesAvailable() {
        Book book = new Book("El Hobbit", "J.R.R. Tolkien", "321");
        User user1 = new User();
        user1.setId("user4");
        user1.setName("Lucía Fernández");

        User user2 = new User();
        user2.setId("user5");
        user2.setName("Mario López");

        library.addUser(user1);
        library.addUser(user2);

        library.addBook(book);
        library.addBook(book);

        Loan loan1 = library.loanABook(user1.getId(), book.getIsbn());
        Loan loan2 = library.loanABook(user2.getId(), book.getIsbn());

        assertNotNull(loan1);
        assertNotNull(loan2);
        assertEquals(LoanStatus.ACTIVE, loan1.getStatus());
        assertEquals(LoanStatus.ACTIVE, loan2.getStatus());
        assertEquals(Integer.valueOf(0), library.getBooks().get(book));
    }

    /* Pruebas para el método addBook */
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

    /* Pruebas para el método returnLoan */
    @Test
    public void shouldNotReturnALoanThatDoesNotExist() {
        Book book = new Book("El Quijote", "Miguel de Cervantes", "hola");
        User user = new User();
        user.setId("user1");
        user.setName("Juan Pérez");

        library.addBook(book);
        library.addBook(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setLoanDate(LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> {
            library.returnLoan(loan);
        });

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
        library.addUser(user);
    
        library.addBook(book);
        library.addBook(book);
    
        String userId = user.getId();
        String bookIsbn = book.getIsbn();
    
        Loan loan = library.loanABook(userId, bookIsbn);
    
        Loan returnedLoan = library.returnLoan(loan);
    
        assertNotNull(returnedLoan);
        assertEquals(LoanStatus.RETURNED, returnedLoan.getStatus());
        assertEquals(userId, returnedLoan.getUser().getId());
        assertEquals(bookIsbn, returnedLoan.getBook().getIsbn());
        assertNotNull(returnedLoan.getReturnDate());
        assertEquals(2, library.getBooks().get(book));
    }
}
