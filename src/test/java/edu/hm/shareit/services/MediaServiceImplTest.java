package edu.hm.shareit.services;

import edu.hm.shareit.GuiceInjectionTestFeature;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class MediaServiceImplTest {

    private MediaService service;
    private Persistence persistenceMock;

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
        persistenceMock = GuiceInjectionTestFeature.getInjectorInstance().getInstance(Persistence.class);
    }

    @Test
    public void testGetBooks() {
        when(persistenceMock.getAll(Book.class)).thenReturn(Arrays.asList(books));
        Book[] list = service.getBooks();
        assertArrayEquals(books, list);
    }

    @Test
    public void testAddValidBook() {
        when(persistenceMock.exist(Book.class, books[0].getIsbn())).thenReturn(false);
        ServiceResult sr = service.addBook(books[0]);
        assertEquals(ServiceResult.OK, sr);
    }

    @Test
    public void testAddDuplicateBook() {
        when(persistenceMock.exist(Book.class, books[0].getIsbn())).thenReturn(true);
        ServiceResult sr = service.addBook(books[0]);
        assertEquals(ServiceResult.DUPLICATE, sr);
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
    public void testGetDiscs() {
        when(persistenceMock.getAll(Disc.class)).thenReturn(Arrays.asList(discs));
        Disc[] list = service.getDiscs();
        assertArrayEquals(discs, list);
    }

    @Test
    public void testAddValidDisc() {
        when(persistenceMock.exist(Disc.class, discs[0].getBarcode())).thenReturn(false);
        ServiceResult sr = service.addDisc(discs[0]);
        assertEquals(ServiceResult.OK, sr);
    }

    @Test
    public void testAddDuplicateDisc() {
        when(persistenceMock.exist(Disc.class, discs[0].getBarcode())).thenReturn(true);
        ServiceResult sr = service.addDisc(discs[0]);
        assertEquals(ServiceResult.DUPLICATE, sr);
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
        when(persistenceMock.exist(Book.class, books[1].getIsbn())).thenReturn(true);
        when(persistenceMock.get(Book.class, books[1].getIsbn())).thenReturn(books[1]);
        Book book = service.getBook(books[1].getIsbn());
        assertEquals(books[1], book);
    }

    @Test
    public void testGetInvalidBook() {
        when(persistenceMock.exist(Book.class, books[0].getIsbn())).thenReturn(false);
        Book book = service.getBook(books[0].getIsbn());
        assertNull(book);
    }

    @Test
    public void testGetValidDisc() {
        when(persistenceMock.exist(Disc.class, discs[1].getBarcode())).thenReturn(true);
        when(persistenceMock.get(Disc.class, discs[1].getBarcode())).thenReturn(discs[1]);
        Disc disc = service.getDisc(discs[1].getBarcode());
        assertEquals(discs[1], disc);
    }

    @Test
    public void testGetInvalidDisc() {
        when(persistenceMock.exist(Disc.class, discs[0].getBarcode())).thenReturn(false);
        Disc disc = service.getDisc(discs[0].getBarcode());
        assertNull(disc);
    }

    @Test
    public void testUpdateBookInvalidISBN() {
        when(persistenceMock.exist(Book.class, books[0].getIsbn())).thenReturn(false);
        ServiceResult sr = service.updateBook(books[0].getIsbn(), books[0]);
        assertEquals(ServiceResult.NOT_FOUND, sr);
    }

    @Test
    public void testUpdateBookInvalidData() {
        when(persistenceMock.exist(Book.class, books[0].getIsbn())).thenReturn(true);
        when(persistenceMock.get(Book.class, books[0].getIsbn())).thenReturn(books[0]);
        Book bookToEdit = service.getBook(books[0].getIsbn());
        ServiceResult sr = service.updateBook(bookToEdit.getIsbn(), new Book("", "", bookToEdit.getIsbn()));
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }

    @Test
    public void testUpdateBookValidData() {
        when(persistenceMock.exist(Book.class, books[0].getIsbn())).thenReturn(true);
        when(persistenceMock.get(Book.class, books[0].getIsbn())).thenReturn(books[0]);
        Book bookToEdit = service.getBook(books[0].getIsbn());
        ServiceResult sr = service.updateBook(bookToEdit.getIsbn(), new Book("Being Hans Sarpei", "Hans Sarpei", bookToEdit.getIsbn()));
        assertEquals(ServiceResult.OK, sr);
    }

    @Test
    public void testUpdateDiscInvalidBarcode() {
        when(persistenceMock.exist(Disc.class, discs[0].getBarcode())).thenReturn(false);
        ServiceResult sr = service.updateDisc(discs[0].getBarcode(), discs[0]);
        assertEquals(ServiceResult.NOT_FOUND, sr);
    }

    @Test
    public void testUpdateDiscInvalidData() {
        when(persistenceMock.exist(Disc.class, discs[0].getBarcode())).thenReturn(true);
        ServiceResult sr = service.updateDisc(discs[0].getBarcode(), new Disc("", "", "", null));
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }

    @Test
    public void testUpdateDiscValidData() {
        when(persistenceMock.exist(Disc.class, discs[0].getBarcode())).thenReturn(true);
        when(persistenceMock.get(Disc.class, discs[0].getBarcode())).thenReturn(discs[0]);
        ServiceResult sr = service.updateDisc(discs[0].getBarcode(), new Disc("Test", discs[0].getBarcode(), "TestDirector", 18));
        assertEquals(ServiceResult.OK, sr);
    }
}
