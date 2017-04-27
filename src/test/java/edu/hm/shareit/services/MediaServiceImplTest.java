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
            new Disc("ValidDisc", "111111111", "Director", 0),
    };

    @Before
    public void before() {
        service = new MediaServiceImpl();
    }

    @Test
    public void testAddValidBook() {
        ServiceResult sr = service.addBook(books[0]);
        assertEquals(ServiceResult.OK, sr);
        assertEquals(1, service.getBooks().length);
    }

    @Test
    public void testAddDuplicateBook() {
        service.addBook(books[0]);
        ServiceResult sr = service.addBook(books[0]);
        assertEquals(ServiceResult.DUPLICATE, sr);
        assertEquals(1, service.getBooks().length);
    }

    @Test
    public void testAddValidDisc() {
        int oldCount = service.getDiscs().length;
        ServiceResult sr = service.addDisc(discs[0]);
        assertEquals(ServiceResult.OK, sr);
        assertEquals(oldCount + 1, service.getDiscs().length);
    }

    @Test
    public void testAddDuplicateDisc() {
        int oldCount = service.getDiscs().length;
        ServiceResult sr = service.addDisc(discs[0]);
        assertEquals(ServiceResult.DUPLICATE, sr);
        assertEquals(oldCount, service.getDiscs().length);
    }

    @Test
    public void testAddInvalidTitleDisc() {
        ServiceResult sr = service.addDisc(new Disc("", "222222222", "Director", 0));
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }

    @Test
    public void testAddInvalidBarcodeDisc() {
        ServiceResult sr = service.addDisc(new Disc("Title", "", "Director", 0));
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }

    @Test
    public void testAddInvalidFskDisc() {
        ServiceResult sr = service.addDisc(new Disc("Title", "333333333", "Director", 99));
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }

}
