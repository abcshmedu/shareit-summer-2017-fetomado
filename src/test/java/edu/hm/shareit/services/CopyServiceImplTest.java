package edu.hm.shareit.services;

import com.sun.xml.internal.bind.v2.TODO;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Copy;
import edu.hm.shareit.models.Disc;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CopyServiceImplTest {

    private CopyServiceImpl service;
    private MediaServiceImpl tmpMs;

    private Book book = new Book("Das Leben des Brian", "Peter Lustig", "9795648377233");
    private Disc disc = new Disc("Interstellar", "999999987", "Christopher Nolan", 12);
    private String[] copyOwner = {"Hans", "Egon"};

    @Before
    public void before() {
        service = new CopyServiceImpl();
        tmpMs = new MediaServiceImpl();
        service.flushDataForTesting();
        // TODO Mock object via injection
        //tmpMs.flushDataForTesting();
    }

    @Test
    public void testAddValidISBNCopy() {
        tmpMs.addBook(book);
        int oldCount = service.getCopies().length;
        ServiceResult sr = service.addCopy(copyOwner[0], book.getIsbn());
        assertEquals(ServiceResult.OK, sr);
        assertEquals(oldCount + 1, service.getCopies().length);
    }

    @Test
    public void testAddValidBarcodeCopy() {
        tmpMs.addDisc(disc);
        int oldCount = service.getCopies().length;
        ServiceResult sr = service.addCopy(copyOwner[1], disc.getBarcode());
        assertEquals(ServiceResult.OK, sr);
        assertEquals(oldCount + 1, service.getCopies().length);
    }

    @Test
    public void testAddInvalidISBNCopy() {
        tmpMs.addBook(book);
        int oldCount = service.getCopies().length;
        ServiceResult sr = service.addCopy(copyOwner[0], "123");
        assertEquals(ServiceResult.NOT_FOUND, sr);
        assertEquals(oldCount, service.getCopies().length);
    }

    @Test
    public void testAddInvalidBarcodeCopy() {
        tmpMs.addDisc(disc);
        int oldCount = service.getCopies().length;
        ServiceResult sr = service.addCopy(copyOwner[1], "456");
        assertEquals(ServiceResult.NOT_FOUND, sr);
        assertEquals(oldCount, service.getCopies().length);
    }

    @Test
    public void testGetValidCopy() {
        tmpMs.addBook(book);
        service.addCopy(copyOwner[0], book.getIsbn());
        Copy copy = service.getCopy(service.getCopies()[0].getId());
        assertEquals(copyOwner[0], copy.getOwner());
        assertEquals(book, copy.getMedium());
    }

    @Test
    public void testGetInvalidCopy() {
        Copy copy = service.getCopy(0);
        assertNull(copy);
    }

    @Test
    public void testUpdateCopy() {
        tmpMs.addBook(book);
        service.addCopy(copyOwner[0], book.getIsbn());
        ServiceResult sr = service.updateCopy(service.getCopies()[0].getId(), copyOwner[1]);
        assertEquals(ServiceResult.OK, sr);
        assertEquals(copyOwner[1], service.getCopies()[0].getOwner());
    }

    @Test
    public void testUpdateCopyInvalidId() {
        tmpMs.addBook(book);
        service.addCopy(copyOwner[0], book.getIsbn());
        ServiceResult sr = service.updateCopy(99, copyOwner[1]);
        assertEquals(ServiceResult.NOT_FOUND, sr);
    }

    @Test
    public void testUpdateCopyInvalidOwner() {
        tmpMs.addBook(book);
        service.addCopy(copyOwner[0], book.getIsbn());
        ServiceResult sr = service.updateCopy(service.getCopies()[0].getId(), null);
        assertEquals(ServiceResult.BAD_REQUEST, sr);
    }
}
