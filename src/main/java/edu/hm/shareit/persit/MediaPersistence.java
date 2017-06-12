package edu.hm.shareit.persit;

import edu.hm.shareit.models.Book;

import java.util.List;

public interface MediaPersistence {


    boolean bookExist(String isbn);

    Book getBook(String isbn);

    void putBook(String isbn, Book book);

    List<Book> getBooks();

}
