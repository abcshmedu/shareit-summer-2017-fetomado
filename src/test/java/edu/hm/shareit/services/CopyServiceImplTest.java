package edu.hm.shareit.services;

import edu.hm.shareit.GuiceInjectionTestFeature;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Copy;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class CopyServiceImplTest {

    private CopyService service;
    private Persistence persistenceMock;

    private Book book = new Book("Das Leben des Brian", "Peter Lustig", "9795648377233");
    private Disc disc = new Disc("Interstellar", "999999987", "Christopher Nolan", 12);
    private String[] owners = {"Hans", "Egon"};
    private Copy[] copies = {new Copy(owners[0], book),
            new Copy(owners[1], disc)};

    @Before
    public void before() {
        service = new CopyServiceImpl();
        GuiceInjectionTestFeature.getInjectorInstance().injectMembers(service);
        persistenceMock = GuiceInjectionTestFeature.getInjectorInstance().getInstance(Persistence.class);
    }


    @Test
    public void testGetBooks() {
        when(persistenceMock.getAll(Copy.class)).thenReturn(Arrays.asList(copies));
        Copy[] list = service.getCopies();
        assertArrayEquals(copies, list);
    }

    @Test
    public void testAddValidISBNCopy() {
        when(persistenceMock.get(Book.class, book.getIsbn())).thenReturn(book);
        ServiceResult sr = service.addCopy(owners[0], book.getIsbn());
        assertEquals(ServiceResult.OK, sr);
    }

    @Test
    public void testAddValidBarcodeCopy() {
        when(persistenceMock.get(Disc.class, disc.getBarcode())).thenReturn(disc);
        ServiceResult sr = service.addCopy(owners[1], disc.getBarcode());
        assertEquals(ServiceResult.OK, sr);
    }

    @Test
    public void testAddInvalidIdentifierCopy() {
        when(persistenceMock.get(Book.class, "123")).thenReturn(null);
        when(persistenceMock.get(Disc.class, "123")).thenReturn(null);
        ServiceResult sr = service.addCopy(owners[0], "123");
        assertEquals(ServiceResult.NOT_FOUND, sr);
    }

    @Test
    public void testGetValidCopy() {
        when(persistenceMock.get(Copy.class, copies[0].getId())).thenReturn(copies[0]);
        when(persistenceMock.exist(Copy.class, copies[0].getId())).thenReturn(true);
        Copy copy = service.getCopy(copies[0].getId());
        assertEquals(owners[0], copy.getOwner());
        assertEquals(book, copy.getMedium());
    }

    @Test
    public void testGetInvalidCopy() {
        when(persistenceMock.exist(Copy.class, 23)).thenReturn(false);
        Copy copy = service.getCopy(23);
        assertNull(copy);
    }

    @Test
    public void testUpdateCopy() {
        when(persistenceMock.get(Book.class, book.getIsbn())).thenReturn(book);
        when(persistenceMock.get(Copy.class, copies[0].getId())).thenReturn(copies[0]);
        when(persistenceMock.exist(Copy.class, copies[0].getId())).thenReturn(true);
        ServiceResult sr = service.updateCopy(copies[0].getId(), owners[1]);
        assertEquals(ServiceResult.OK, sr);
    }

    @Test
    public void testUpdateCopyInvalidId() {
        when(persistenceMock.exist(Copy.class, 23)).thenReturn(false);
        ServiceResult sr = service.updateCopy(23, owners[1]);
        assertEquals(ServiceResult.NOT_FOUND, sr);
    }

    @Test
    public void testUpdateCopyInvalidOwner() {
        when(persistenceMock.get(Copy.class, copies[0].getId())).thenReturn(copies[0]);
        when(persistenceMock.exist(Copy.class, copies[0].getId())).thenReturn(true);
        ServiceResult sr = service.updateCopy(copies[0].getId(), null);
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }
}
