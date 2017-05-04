package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

public interface MediaService {
    ServiceResult addBook(Book book);

    ServiceResult addDisc(Disc disc);

    Book getBook(String isbn);

    Book[] getBooks();

    /**
     * This function is used to change the author or the title of an existing book.
     * @param isbn unique identifier of the book to be changed
     * @param book book that contains the edited values
     * @return ServiceResult depending on the behaviour of the update-process
     */
    ServiceResult updateBook(String isbn, Book book);

    Disc getDisc(String barcode);

    Disc[] getDiscs();

    /**
     * This function is used to change the director, title or fsk of an existing disc.
     * @param barcode unique identifier of the disc to be changed
     * @param disc disc that contains the edited values
     * @return ServiceResult depending on the behaviour of the update-process
     */
    ServiceResult updateDisc(String barcode, Disc disc);
}
