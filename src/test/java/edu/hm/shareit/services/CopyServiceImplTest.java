package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Copy;
import edu.hm.shareit.models.Disc;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CopyServiceImplTest {

    private CopyService service;

    private Book book = new Book("Das Leben des Brian", "Peter Lustig", "979-5-648-37723-3");
    private Disc disc = new Disc("Interstellar", "999999987", "Christopher Nolan", 12);
    private Copy[] copies = {
            new Copy("Hans", book),
            new Copy("Egon", disc),
    };
    private MediaService tmpMs;

    @Before
    public void before() {
        service = new CopyServiceImpl();
        tmpMs = new MediaServiceImpl();
    }

    @Test
    public void test() {
        assert(true);
    }

    @Test
    public void testAddValidISBNCopy(){
        tmpMs.addBook(book);
        int oldCount = service.getCopies().length;
        ServiceResult sr = service.addCopy(copies[0].getOwner(),book.getIsbn() );
        assertEquals(ServiceResult.OK, sr);
        assertEquals(oldCount + 1, service.getCopies().length);
    }

    @Test
    public void testAddValidBarcodeCopy(){
        tmpMs.addDisc(disc);
        int oldCount = service.getCopies().length;
        ServiceResult sr = service.addCopy(copies[1].getOwner(),disc.getBarcode() );
        assertEquals(ServiceResult.OK, sr);
        assertEquals(oldCount + 1, service.getCopies().length);
    }


    @Test
    public void testAddInvalidISBNCopy(){
        tmpMs.addBook(book);
        int oldCount = service.getCopies().length;
        ServiceResult sr = service.addCopy(copies[0].getOwner(),"123");
        assertEquals(ServiceResult.NOT_FOUND, sr);
        assertEquals(oldCount, service.getCopies().length);
    }

    @Test
    public void testAddInvalidBarcodeCopy() {
        tmpMs.addDisc(disc);
        int oldCount = service.getCopies().length;
        ServiceResult sr = service.addCopy(copies[1].getOwner(), "456");
        assertEquals(ServiceResult.NOT_FOUND, sr);
        assertEquals(oldCount, service.getCopies().length);
    }
    
}