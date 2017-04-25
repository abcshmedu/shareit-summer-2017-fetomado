package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

public interface MediaService {
    MediaServiceResult addBook(Book book);

    MediaServiceResult addDisc(Disc disc);

    Book getBook(String isbn);

    Book[] getBooks();

    MediaServiceResult updateBook(String isbn, Book book);

    Disc getDisc(String barcode);

    Disc[] getDiscs();

    MediaServiceResult updateDisc(String barcode, Disc disc);
}
