package edu.hm.shareit.persit;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

import java.util.List;

public interface MediaPersistence {


    boolean bookExist(String isbn);

    Book getBook(String isbn);

    void putBook(Book book);

    List<Book> getBooks();

    boolean discExist(String barcode);

    void putDisc(Disc disc);

    List<Disc> getDiscs();

    Disc getDisc(String barcode);
}
