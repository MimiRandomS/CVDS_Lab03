package edu.eci.cvds.tdd.library;

import edu.eci.cvds.tdd.library.book.Book;
import edu.eci.cvds.tdd.library.loan.Loan;
import edu.eci.cvds.tdd.library.loan.LoanStatus;
import edu.eci.cvds.tdd.library.user.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Library responsible for manage the loans and the users.
 */
public class Library {

    private final List<User> users;
    private final Map<Book, Integer> books;
    private final List<Loan> loans;

    public Library() {
        users = new ArrayList<>();
        books = new HashMap<>();
        loans = new ArrayList<>();
    }

    /**
     * Adds a new {@link edu.eci.cvds.tdd.library.book.Book} into the system, the book is store in a Map that contains
     * the {@link edu.eci.cvds.tdd.library.book.Book} and the amount of books available, if the book already exist the
     * amount should increase by 1 and if the book is new the amount should be 1, this method returns true if the
     * operation is successful false otherwise.
     *
     * @param book The book to store in the map.
     *
     * @return true if the book was stored false otherwise.
     */
    public boolean addBook(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("La función no permite un tipo de book nulo");
        }

        try {
            if (this.books.containsKey(book)) {
                this.books.put(book, this.books.get(book) + 1);
            } else {
                this.books.put(book, 1);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method creates a new loan with for the User identify by the userId and the book identify by the isbn,
     * the loan should be store in the list of loans, to successfully create a loan is required to validate that the
     * book is available, that the user exist and the same user could not have a loan for the same book
     * {@link edu.eci.cvds.tdd.library.loan.LoanStatus#ACTIVE}, once these requirements are meet the amount of books is
     * decreased and the loan should be created with {@link edu.eci.cvds.tdd.library.loan.LoanStatus#ACTIVE} status and
     * the loan date should be the current date.
     *
     * @param userId id of the user.
     * @param isbn book identification.
     *
     * @return The new created loan.
     */
    
    public Loan loanABook(String userId, String isbn) {
    // Buscar al usuario por su ID
    User user = null;
    for (User u : users) {
        if (u.getId().equals(userId)) {
            user = u;
            break;
        }
    }
    
    if (user == null) {
        throw new IllegalArgumentException("User does not exist.");
    }

    // Buscar el libro por su ISBN
    Book book = null;
    for (Book b : books.keySet()) {
        if (b.getIsbn().equals(isbn)) {
            book = b;
            break;
        }
    }

    if (book == null) {
        throw new IllegalArgumentException("Book not found.");
    }

    // Verificar si hay copias disponibles del libro
    Integer availableCopies = books.get(book);
    if (availableCopies == null || availableCopies <= 0) {
        throw new IllegalArgumentException("Book not available.");
    }

    // Verificar si el usuario ya tiene un préstamo activo del mismo libro
    for (Loan loan : loans) {
        if (loan.getUser().getId().equals(userId) && loan.getBook().getIsbn().equals(isbn) && loan.getStatus() == LoanStatus.ACTIVE) {
            throw new IllegalArgumentException("User has already borrowed this book.");
        }
    }

    // Crear un nuevo préstamo
    Loan newLoan = new Loan();
    newLoan.setUser(user);
    newLoan.setBook(book);
    newLoan.setLoanDate(LocalDateTime.now());
    newLoan.setStatus(LoanStatus.ACTIVE);
    
    // Actualizar la disponibilidad del libro
    books.put(book, availableCopies - 1);

    // Agregar el préstamo a la lista de préstamos
    loans.add(newLoan);

    return newLoan;
}

    /**
     * This method return a loan, meaning that the amount of books should be increased by 1, the status of the Loan
     * in the loan list should be {@link edu.eci.cvds.tdd.library.loan.LoanStatus#RETURNED} and the loan return
     * date should be the current date, validate that the loan exist.
     *
     * @param loan loan to return.
     *
     * @return the loan with the RETURNED status.
     */
    public Loan returnLoan(Loan loan) {
        if (loan == null) throw new IllegalArgumentException("La función no permite un tipo de loan nulo");
        if (!loans.contains(loan)) throw new IllegalArgumentException("El préstamo no existe en la lista de préstamos");

        Book book = loan.getBook();
        this.books.put(book, this.books.get(book) + 1);

        for (int i = 0; i < loans.size(); i++){
            if (loans.get(i).equals(loan)){
                loans.get(i).setStatus(LoanStatus.RETURNED);
                loans.get(i).setReturnDate(LocalDateTime.now());
                loan.setStatus(LoanStatus.RETURNED);
                loan.setReturnDate(LocalDateTime.now());
            }
        }
        return loan;
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public Map<Book, Integer> getBooks(){
        return this.books;
    }

}