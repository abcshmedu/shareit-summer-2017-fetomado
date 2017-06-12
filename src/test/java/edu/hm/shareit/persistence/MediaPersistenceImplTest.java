package edu.hm.shareit.persistence;


import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import org.hsqldb.Session;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MediaPersistenceImplTest {
    private MediaPersistence persistence;
    Session entityManager;

    public MediaPersistenceImplTest() {
        persistence = new MediaPersistenceImpl();
    }

    Book[] books = {
            new Book("Die Kaenguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3"),
            new Book("what if?", "Randall Munroe", "978-3-8135-0625-5"),
    };

    Disc[] discs = {
            new Disc("ValidDisc", "111111111", "Director", 0),
            new Disc("Deadpool", "456789123", "Tim Miller", 16),
    };

    @BeforeClass
    public void initialize() {
        persistence.putBook(books[0]);

    }

    @Test
    public void testPutBook() {
    }

    @Test
    public void testGetBook() {

    }


}