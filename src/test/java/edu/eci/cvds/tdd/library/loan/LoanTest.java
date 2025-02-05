package edu.eci.cvds.tdd.library.loan;

import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.user.User;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LoanTest {
    @Test
    public void shouldValidateInfoLoan() {
        Book book1 = new Book("La guerra de los mundos", "H.G. Wells", "1");
        User user1 = new User();
        user1.setId("1");
        user1.setName("Juan");
        Loan loan = new Loan();
        loan.setBook(book1);
        loan.setUser(user1);
        LocalDateTime dateNow = LocalDateTime.now();
        loan.setLoanDate(dateNow);
        loan.setStatus(LoanStatus.ACTIVE);
        LocalDateTime returnDate = LocalDateTime.of(2025, 3, 13, 8, 0, 0);
        loan.setReturnDate(returnDate);
        assertTrue(loan.getBook().equals(book1));
        assertTrue(loan.getUser().equals(user1));
        assertTrue(loan.getLoanDate().equals(dateNow));
        assertTrue(loan.getStatus().equals(LoanStatus.ACTIVE));
        assertTrue(loan.getReturnDate().equals(returnDate));
    }
}
