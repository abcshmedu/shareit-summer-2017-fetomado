package edu.hm.shareit.persistence;

import edu.hm.shareit.models.Book;
import org.hsqldb.Session;

import java.util.List;

public interface MediaPersistence {


    boolean bookExist(String isbn);

    Book getBook(String isbn);

    void putBook(String isbn, Book book);

    List<Book> getBooks();

}
