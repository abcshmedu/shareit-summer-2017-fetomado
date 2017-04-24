package edu.hm.shareit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

/**
 * This is the implementation of the MediaService interface. It does the work
 * behind the /media/ resource.
 */
public class MediaServiceImpl implements MediaService {

	private static List<Book> books;
	private static List<Disc> discs;

	/**
	 * Constructs a new instance.
	 */
	public MediaServiceImpl() {
		if (books == null) {
			books = new ArrayList<>();
			books.add(new Book("Die Känguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3"));
			books.add(new Book("what if?", "Randall Munroe", "978-3-8135-0625-5"));
		}

		if (discs == null) {
			discs = new ArrayList<>();
			discs.add(new Disc("Rennschwein Rudi Rüssel", "123456789", "Peter Timm", 0));
			discs.add(new Disc("Deadpool", "456789123", "Tim Miller", 16));
			discs.add(new Disc("Source Code", "101001011", "Duncan Jones", 12));
		}

	}
	
    @Override
    public MediaServiceResult addBook(Book book) {
        String regex = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(book.getIsbn());

        if (!matcher.matches() || book.getAuthor() == "" || book.getTitle() == "") {
            return MediaServiceResult.BAD_REQUEST;
        } else {
            books.add(book);
            return MediaServiceResult.OK;
        }
    }
    
    @Override
    public MediaServiceResult addDisc(Disc disc) {
        discs.add(disc);
        return MediaServiceResult.OK;
    }
	
    @Override
    public Book getBook(String isbn) {
        Book singleBook = null;
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getIsbn().equals(isbn)) {
                singleBook = books.get(i);
            }
        }
        return singleBook;
    }    
    
	@Override
	public Book[] getBooks() {
		return books.toArray(new Book[books.size()]);
	}
	
	@Override
	public Disc getDisc(String barcode) {
		Disc singleDisc = null;
		for (int i = 0; i < discs.size(); i++) {
			Disc disc = discs.get(i);
			if (disc.getBarcode().equals(barcode)) {
				singleDisc = discs.get(i);
			}
		}
		return singleDisc;
	}

	@Override
	public Disc[] getDiscs() {
		return discs.toArray(new Disc[discs.size()]);
	}

}
