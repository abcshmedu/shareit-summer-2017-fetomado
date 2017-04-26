package edu.hm.shareit.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

/**
 * This is the implementation of the MediaService interface. It does the work
 * behind the /media/ resource.
 */
public class MediaServiceImpl implements MediaService {

    private static Map<String, Book> books;
    private static Map<String, Disc> discs;
    private static final int FSK_MAX = 18;

    /**
     * Constructs a new instance.
     */
    public MediaServiceImpl() {
        if (books == null) {
            books = new HashMap<>();
            books.put("978-3-548-37623-3", new Book("Die Kaenguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3"));
            books.put("978-3-8135-0625-5", new Book("what if?", "Randall Munroe", "978-3-8135-0625-5"));
        }

        if (discs == null) {
            discs = new HashMap<>();
            discs.put("123456789", new Disc("Rennschwein Rudi Ruessel", "123456789", "Peter Timm", 0));
            discs.put("456789123", new Disc("Deadpool", "456789123", "Tim Miller", 16));
            discs.put("101001011", new Disc("Source Code", "101001011", "Duncan Jones", 12));
        }

    }
    
    @Override
    public ServiceResult addBook(Book book) {
        String regex = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(book.getIsbn());

        if (!matcher.matches() || book.getAuthor() == "" || book.getTitle() == "") {
            return ServiceResult.BAD_REQUEST;
        } else if (books.containsKey(book.getIsbn())) {
            return ServiceResult.DUPLICATE;
        } else {
            books.put(book.getIsbn(), book);
            return ServiceResult.OK;
        }
    }
    
    @Override
    public ServiceResult addDisc(Disc disc) {
        String regex = "^[1-9][0-9]{8,14}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(disc.getBarcode());

        if (disc.getDirector().isEmpty() || disc.getTitle().isEmpty() || (disc.getFsk() < 0 || disc.getFsk() > FSK_MAX)
                || !matcher.matches()) {
            return ServiceResult.BAD_REQUEST;
        } else if (discs.containsKey(disc.getBarcode())) {
            return ServiceResult.DUPLICATE;
        } else {
            discs.put(disc.getBarcode(), disc);
            return ServiceResult.OK;
        }
    }
    
    @Override
    public Book getBook(String isbn) {
        if (books.containsKey(isbn)) {
            return books.get(isbn);
        } else {
            return null;
        }
    }   
    
    @Override
    public Book[] getBooks() {
        return books.values().toArray(new Book[books.size()]);
    }
    
    @Override
    public ServiceResult updateBook(String isbn, Book book) {
        if (books.containsKey(isbn)) {
            Book existBook = books.get(isbn);
            if (existBook.getIsbn().equals(isbn)) {
                if (book.getAuthor() != null && !book.getAuthor().equals("")) {
                    existBook.setAuthor(book.getAuthor());
                }
                if (book.getTitle() != null && !book.getTitle().equals("")) {
                    existBook.setTitle(book.getTitle());
                }
            }
        }
        return ServiceResult.OK;
    }
    
    @Override
    public Disc getDisc(String barcode) {
        if (discs.containsKey(barcode)) {
            return discs.get(barcode);
        } else {
            return null;
        }
    }

    @Override
    public Disc[] getDiscs() {
        return discs.values().toArray(new Disc[discs.size()]);
    }
    
    @Override
    public ServiceResult updateDisc(String barcode, Disc disc) {
        if (discs.containsKey(barcode)) {
            Disc existDisc = discs.get(barcode);
            if (existDisc.getBarcode().equals(barcode)) {
                if (disc.getDirector() != null && !disc.getDirector().equals("")) {
                    existDisc.setDirector(disc.getDirector());
                }
                if (disc.getTitle() != null && !disc.getTitle().equals("")) {
                    existDisc.setTitle(disc.getTitle());
                }
                if (disc.getFsk() != existDisc.getFsk() && (disc.getFsk() >= 0 && disc.getFsk() <= FSK_MAX)) {
                    existDisc.setFsk(disc.getFsk());
                }
            }
        }
        return ServiceResult.OK;
    }

}
