package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

public interface MediaService {
    ServiceResult addBook(Book book);

    ServiceResult addDisc(Disc disc);

    Book getBook(String isbn);

    Book[] getBooks();

    ServiceResult updateBook(String isbn, Book book);

    Disc getDisc(String barcode);

    Disc[] getDiscs();

    ServiceResult updateDisc(String barcode, Disc disc);
}
