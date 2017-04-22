package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Medium;

public interface MediaService {
	public Medium addBook(Book book);
    public Medium[] getBooks();
    public Medium getDisc(String barcode);
    public Medium[] getDiscs();
}
