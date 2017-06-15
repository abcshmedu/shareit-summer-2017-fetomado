package edu.hm.shareit.persistence;


import edu.hm.shareit.GuiceInjectionTestFeature;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Token;
import edu.hm.shareit.models.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersistenceImplTest {

    private Persistence persistence;

    static Book[] books = {
            new Book("Die Kaenguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3"),
            new Book("what if?", "Randall Munroe", "978-3-8135-0625-5"),
    };

    @BeforeClass
    public static void initialize() {
        Persistence persist = new PersistenceImpl();
        GuiceInjectionTestFeature.getInjectorInstance().injectMembers(persist);
        persist.add(books[0]);
    }

    @Before
    public void setUp() {
        persistence = new PersistenceImpl();
        GuiceInjectionTestFeature.getInjectorInstance().injectMembers(persistence);
    }

    @Test
    public void testAddBook() {
        List<Book> book = persistence.getAll(Book.class);
        persistence.add(books[1]);
        assertEquals(book.size() + 1, persistence.getAll(Book.class).size());
    }

    @Test
    public void testAddDuplicateBook() {
        int count = persistence.getAll(Book.class).size();
        persistence.add(books[0]);
        assertEquals(count, persistence.getAll(Book.class).size());
    }

    @Test
    public void testGetBook() {
        assertEquals(persistence.get(Book.class, books[0].getIsbn()).getIsbn(), books[0].getIsbn());
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book(books[0].getTitle(), books[0].getAuthor(), books[0].getIsbn());
        book.setAuthor("Felix Maurer");
        book.setTitle("Blaaaa");
        persistence.update(book);
        assertEquals(book, persistence.get(Book.class, book.getIsbn()));
    }

    @Test
    public void testGetUserOfToken() {
        User user = new User("testuser", "Test123");
        Token token = new Token(user);
        persistence.add(user);
        persistence.add(token);
        assertEquals(user, persistence.get(Token.class, token.getToken()).getUser());
    }

}
