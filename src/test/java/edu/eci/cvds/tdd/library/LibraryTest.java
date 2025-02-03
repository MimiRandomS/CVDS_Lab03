package edu.eci.cvds.tdd.library;

import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDateTime;

package edu.eci.cvds.tdd.library;

import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.loan.Loan;
import edu.eci.cvds.tdd.library.loan.LoanStatus;
import edu.eci.cvds.tdd.library.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LibraryTest {
    private Library library;

    @Before
    public void setUp() {
        library = new Library();
    }

    @Test
    public void shouldCreateLoanSuccessfullyWhenBookIsAvailable() {
        
        Book book = new Book("El Quijote", "Miguel de Cervantes", "hola");
        User user = new User();
        user.setId("user1");
        user.setName("Juan Pérez");

        library.addBook(book);

        String userId = user.getId();
        String bookIsbn = book.getIsbn();

        Loan loan = library.LoanABook(userId, bookIsbn);

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
        library.LoanABook(userId, bookIsbn);  // Lanza excepción porque el libro no está en la biblioteca
    }
}
