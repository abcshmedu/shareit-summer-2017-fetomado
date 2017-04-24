package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

public interface MediaService {
	public MediaServiceResult addBook(Book book);
	public MediaServiceResult addDisc(Disc disc);
	public Book getBook(String isbn);
    public Book[] getBooks();
    public Disc getDisc(String barcode);
    public Disc[] getDiscs();
}
