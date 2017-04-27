package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MediaServiceImplTest {

    private MediaService service;

    private Book[] books = {
            new Book("Die Kaenguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3"),
            new Book("what if?", "Randall Munroe", "978-3-8135-0625-5"),
    };
    private Disc[] discs = {
            new Disc("Rennschwein Rudi Ruessel", "123456789", "Peter Timm", 0),
            new Disc("Deadpool", "456789123", "Tim Miller", 16),
            new Disc("Source Code", "101001011", "Duncan Jones", 12),
    };

    @Before
    public void before() {
        service = new MediaServiceImpl();
    }

    @Test
    public void testAddValidBook() {
        int oldCount = service.getBooks().length;
        ServiceResult sr = service.addBook(books[0]);
        assertEquals(ServiceResult.OK, sr);
        assertEquals(oldCount + 1, service.getBooks().length);
    }

    @Test
    public void testAddDuplicateBook() {
        service.addBook(books[0]);
        int oldCount = service.getBooks().length;
        ServiceResult sr = service.addBook(books[0]);
        assertEquals(ServiceResult.DUPLICATE, sr);
        assertEquals(oldCount, service.getBooks().length);
    }

    @Test
    public void testAddInvalidTitleBook() {
        ServiceResult sr = service.addBook(new Book("", "Marc-Uwe Kling", "978-3-548-37623-3"));
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }

    @Test
    public void testAddInvalidAuthorBook() {
        ServiceResult sr = service.addBook(new Book("Die Kaenguru-Chroniken", "", "978-3-548-37623-3"));
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }

    @Test
    public void testAddInvalidISBNBook() {
        ServiceResult sr = service.addBook(new Book("Die Kaenguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623"));
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }

}
