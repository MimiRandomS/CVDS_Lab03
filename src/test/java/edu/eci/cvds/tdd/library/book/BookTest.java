package edu.eci.cvds.tdd.library.book;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BookTest {
    @Test
    public void shouldValidateInfoBook() {
        Book book = new Book("La guerra de los mundos", "H.G. Wells", "1");
        assertTrue(book.getTittle().equals("La guerra de los mundos"));
        assertTrue(book.getAuthor().equals("H.G. Wells"));
        assertTrue(book.getIsbn().equals("1"));
    }

    @Test
    public void shouldBookEquals() {
        Book book1 = new Book("La guerra de los mundos", "H.G. Wells", "1");
        Book book2 = new Book("La guerra de los mundos", "H.G. Wells", "1");
        assertTrue(book1.equals(book2));
    }
}