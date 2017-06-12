package edu.hm.shareit.persistence;


import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import org.hsqldb.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MediaPersistenceImplTest {

    private MediaPersistence persistence;

    static Book[] books = {
            new Book("Die Kaenguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3"),
            new Book("what if?", "Randall Munroe", "978-3-8135-0625-5"),
    };

    Disc[] discs = {
            new Disc("ValidDisc", "111111111", "Director", 0),
            new Disc("Deadpool", "456789123", "Tim Miller", 16),
    };

    @BeforeClass
    public static void initialize() {
        MediaPersistence persist = new MediaPersistenceImpl();
        persist.putBook(books[0]);
    }

    @Before
    public void setUp() {
        persistence = new MediaPersistenceImpl();
    }

    @Test
    public void testPutBook() {
        List<Book> book = persistence.getBooks();
        persistence.putBook(books[1]);
        assertEquals(book.size() + 1, persistence.getBooks().size());
    }

    @Test
    public void testPutDuplicateBook() {
        int count = persistence.getBooks().size();
        persistence.putBook(books[0]);
        assertEquals(count, persistence.getBooks().size());
    }

    @Test
    public void testGetBook() {
        assertEquals(persistence.getBook(books[0].getIsbn()).getIsbn(), books[0].getIsbn());
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book(books[0].getTitle(), books[0].getAuthor(), books[0].getIsbn());
        book.setAuthor("Felix Maurer");
        book.setTitle("Blaaaa");
        persistence.updateBook(book);
        assertEquals(book, persistence.getBook(book.getIsbn()));
    }


}