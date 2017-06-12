package edu.hm.shareit.persistence;


import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
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

    static Disc[] discs = {
            new Disc("ValidDisc", "111111111", "Director", 0),
            new Disc("Deadpool", "456789123", "Tim Miller", 16),
    };

    @BeforeClass
    public static void initialize() {
        MediaPersistence persist = new MediaPersistenceImpl();
        persist.addBook(books[0]);
        persist = new MediaPersistenceImpl();
        persist.addDisc(discs[0]);
    }

    @Before
    public void setUp() {
        persistence = new MediaPersistenceImpl();
    }

    @Test
    public void testAddBook() {
        List<Book> book = persistence.getBooks();
        persistence.addBook(books[1]);
        assertEquals(book.size() + 1, persistence.getBooks().size());
    }

    @Test
    public void testAddDuplicateBook() {
        int count = persistence.getBooks().size();
        persistence.addBook(books[0]);
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

    @Test
    public void testAddDisc() {
        int count = persistence.getDiscs().size();
        persistence.addDisc(discs[1]);
        assertEquals(count + 1, persistence.getDiscs().size());
    }

    @Test
    public void testAddDuplicateDisc() {
        int count = persistence.getDiscs().size();
        persistence.addDisc(discs[0]);
        assertEquals(count, persistence.getDiscs().size());
    }

    @Test
    public void testGetDisc() {
        assertEquals(persistence.getDisc(discs[0].getBarcode()).getBarcode(), discs[0].getBarcode());
    }

    @Test
    public void testUpdateDisc() {
        Disc disc = new Disc(discs[0].getTitle(), discs[0].getBarcode(), discs[0].getDirector(), discs[0].getFsk());
        disc.setTitle("Felix Maurer");
        disc.setDirector("Blaaaa");
        persistence.updateDisc(disc);
        assertEquals(disc, persistence.getDisc(disc.getBarcode()));
    }

}