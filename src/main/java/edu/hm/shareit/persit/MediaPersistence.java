package edu.hm.shareit.persit;

import edu.hm.shareit.models.Book;

public interface MediaPersistence {


    boolean bookExist(String isbn);

    Book getBook(String isbn);

    void putBook(String isbn, Book book);

    Book[] getBooks();
}
