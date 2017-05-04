package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

public interface MediaService {
    ServiceResult addBook(Book book);

    ServiceResult addDisc(Disc disc);

    /**
     * Returns the book with unique ISBN number.
     * @param isbn unique identifier of the book
     * @return the book is it exists, null otherwise
     */
    Book getBook(String isbn);

    /**
     * Returns all books that exist in the service.
     * @return an array with all books
     */
    Book[] getBooks();

    ServiceResult updateBook(String isbn, Book book);

    /**
     * Returns the disc with unique Barcode.
     * @param barcode unique identifier of the disc
     * @return the disc is it exists, null otherwise
     */
    Disc getDisc(String barcode);

    /**
     * Returns all discs that exist in the service.
     * @return an array with all discs
     */
    Disc[] getDiscs();

    ServiceResult updateDisc(String barcode, Disc disc);
}
