package edu.hm.shareit.persistence;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

import java.util.List;

public interface MediaPersistence {


    boolean bookExist(String isbn);

    Book getBook(String isbn);

    void addBook(Book book);

    List<Book> getBooks();

    void updateBook(Book book);

    boolean discExist(String barcode);

    void addDisc(Disc disc);

    List<Disc> getDiscs();

    Disc getDisc(String barcode);

    void updateDisc(Disc disc);
}
