package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

/**
 * Interface for the service managing of mediums.
 */
public interface MediaService {

    /**
     * Add a new book.
     * @param book the book which should be added
     * @return the result of the operation
     */
    ServiceResult addBook(Book book);

    /**
     * Add a new disc.
     * @param disc the disc which should be added
     * @return the result of the operation
     */
    ServiceResult addDisc(Disc disc);

    Book getBook(String isbn);

    Book[] getBooks();

    ServiceResult updateBook(String isbn, Book book);

    Disc getDisc(String barcode);

    Disc[] getDiscs();

    ServiceResult updateDisc(String barcode, Disc disc);
}
