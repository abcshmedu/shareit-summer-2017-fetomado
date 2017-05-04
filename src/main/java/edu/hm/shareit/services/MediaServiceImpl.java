package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        }

        if (discs == null) {
            discs = new HashMap<>();
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
        if (!books.containsKey(isbn)) {
            return ServiceResult.NOT_FOUND;
        }
        if ((book.getAuthor() == null || book.getAuthor().equals(""))
                && (book.getTitle() == null || book.getTitle().equals(""))) {
            return ServiceResult.BAD_REQUEST;
        }
        Book bookToEdit = books.get(isbn);
        if (book.getAuthor() != null && !book.getAuthor().equals("")) {
            bookToEdit.setAuthor(book.getAuthor());
        }
        if (book.getTitle() != null && !book.getTitle().equals("")) {
            bookToEdit.setTitle(book.getTitle());
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
            if ((disc.getDirector() == null || disc.getDirector().equals(""))
                    && (disc.getTitle() == null || disc.getTitle().equals(""))
                    && disc.getFsk() == null) {
                return ServiceResult.BAD_REQUEST;
            }
            if (changeDisc(barcode, disc)) {
                return ServiceResult.OK;
            }
        }
        return ServiceResult.NOT_FOUND;
    }

    private boolean changeDisc(String barcode, Disc disc) {
        boolean change = false;
        Disc existDisc = discs.get(barcode);
        if (disc.getDirector() != null && !disc.getDirector().equals("")) {
            existDisc.setDirector(disc.getDirector());
            change = true;
        }
        if (disc.getTitle() != null && !disc.getTitle().equals("")) {
            existDisc.setTitle(disc.getTitle());
            change = true;
        }
        if (disc.getFsk() != null && !disc.getFsk().equals(existDisc.getFsk()) && (disc.getFsk() >= 0 && disc.getFsk() <= FSK_MAX)) {
            existDisc.setFsk(disc.getFsk());
            change = true;
        }
        return change;
    }

    void flushDataForTesting() {
        books = new HashMap<>();
        discs = new HashMap<>();
    }

}
