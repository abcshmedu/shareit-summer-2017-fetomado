package edu.hm.shareit.services;

import edu.hm.shareit.GuiceInjectionTestFeature;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MediaServiceImplTest {

    private MediaService service;

    private Book[] books = {
            new Book("Die Kaenguru-Chroniken", "Marc-Uwe Kling", "9783548376233"),
            new Book("what if?", "Randall Munroe", "9783813506255"),
    };
    private Disc[] discs = {
            new Disc("ValidDisc", "111111111", "Director", 0),
            new Disc("Deadpool", "456789123", "Tim Miller", 16),
    };

    @Before
    public void before() {
        service = new MediaServiceImpl();
        GuiceInjectionTestFeature.getInjectorInstance().injectMembers(service);
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

    @Test
    public void testGetValidBook() {
        service.addBook(books[1]);
        Book book = service.getBook("9783813506255");
        assertEquals(books[1], book);
    }

    @Test
    public void testGetInvalidBook() {
        Book book = service.getBook("9783813506259");
        assertNull(book);
    }

    @Test
    public void testGetValidDisc() {
        service.addDisc(discs[1]);
        Disc disc = service.getDisc("456789123");
        assertEquals(discs[1], disc);
    }

    @Test
    public void testGetInvalidDisc() {
        Disc disc = service.getDisc("99");
        assertNull(disc);
    }

    @Test
    public void testUpdateBookInvalidISBN() {
        service.addBook(books[0]);
        ServiceResult sr = service.updateBook("123", books[0]);
        assertEquals(ServiceResult.NOT_FOUND, sr);
    }

    @Test
    public void testUpdateBookInvalidData() {
        Book bookToEdit = service.getBook(books[0].getIsbn());
        ServiceResult sr = service.updateBook(bookToEdit.getIsbn(), new Book("", "", bookToEdit.getIsbn()));
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }

    @Test
    public void testUpdateBookValidData() {
        Book bookToEdit = service.getBook(books[0].getIsbn());
        ServiceResult sr = service.updateBook(bookToEdit.getIsbn(), new Book("Being Hans Sarpei", "Hans Sarpei", bookToEdit.getIsbn()));
        assertEquals(ServiceResult.OK, sr);
        assertEquals("Being Hans Sarpei", bookToEdit.getTitle());
        assertEquals("Hans Sarpei", bookToEdit.getAuthor());
    }

    @Test
    public void testUpdateDiscInvalidBarcode() {
        service.addDisc(discs[0]);
        ServiceResult sr = service.updateDisc("123", discs[0]);
        assertEquals(ServiceResult.NOT_FOUND, sr);
    }

    @Test
    public void testUpdateDiscInvalidData() {
        service.addDisc(discs[0]);
        Disc discToEdit = service.getDisc(discs[0].getBarcode());
        ServiceResult sr = service.updateDisc(discToEdit.getBarcode(), new Disc("", "", "", null));
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }

    @Test
    public void testUpdateDiscValidData() {
        service.addDisc((discs[0]));
        Disc discToEdit = service.getDisc(discs[0].getBarcode());
        ServiceResult sr = service.updateDisc(discToEdit.getBarcode(), new Disc("Test", discToEdit.getBarcode(), "TestDirector", 18));
        assertEquals(ServiceResult.OK, sr);
        assertEquals("Test", discToEdit.getTitle());
        assertEquals("TestDirector", discToEdit.getDirector());
        assertEquals(new Integer(18), discToEdit.getFsk());
    }

}
