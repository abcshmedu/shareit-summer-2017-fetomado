package edu.hm.shareit.services;

import java.util.ArrayList;
import java.util.List;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;

/**
 * This is the implementation of the MediaService interface. It does the work
 * behind the /media/ resource.
 */
public class MediaServiceImpl implements MediaService {

	private List<Medium> books;
	private List<Medium> discs;

	/**
	 * Constructs a new instance.
	 */
	public MediaServiceImpl() {
		books = new ArrayList<>();
		
		books.add(new Book("Die Känguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3"));
		books.add(new Book("what if?", "Randall Munroe", "978-3-8135-0625-5"));

		discs = new ArrayList<>();
		discs.add(new Disc("Rennschwein Rudi R�ssel", "123456789", "Peter Timm", 0));
		discs.add(new Disc("Deadpool", "456789123", "Tim Miller", 16));
		discs.add(new Disc("Source Code", "101001011", "Duncan Jones", 12));

	}
	
    @Override
    public Medium addBook(Book book) {
    	books.add(book);
        return book;
    }
	
    @Override
    public Medium getBook(String isbn) {
        Medium singleBook = null;
        for (int i = 0; i < books.size(); i++) {
            Book book = (Book) books.get(i);
            if (book.getIsbn().equals(isbn)) {
                singleBook = books.get(i);
            }
        }
        return singleBook;
    }    
    
	@Override
	public Medium[] getBooks() {
		return books.toArray(new Medium[books.size()]);
	}
	
	@Override
	public Medium getDisc(String barcode) {
		Medium singleDisc = null;
		for (int i = 0; i < discs.size(); i++) {
			Disc disc = (Disc) discs.get(i);
			if (disc.getBarcode().equals(barcode)) {
				singleDisc = discs.get(i);
			}
		}
		return singleDisc;
	}

	@Override
	public Medium[] getDiscs() {
		return discs.toArray(new Medium[discs.size()]);
	}

}
