package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.persistence.Persistence;

import javax.inject.Inject;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the implementation of the MediaService interface. It does the work
 * behind the /media/ resource.
 */
public class MediaServiceImpl implements MediaService {

    @Inject
    private Persistence persist;
    private static final int FSK_MAX = 18;

    @Override
    public ServiceResult addBook(Book book) {
        book = new Book(book.getTitle(),
                book.getAuthor(),
                book.getIsbn().replace("-", ""));
        String regex = "^[0-9]{13}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(book.getIsbn());

        if (!matcher.matches() || book.getAuthor() == "" || book.getTitle() == "") {
            return ServiceResult.BAD_REQUEST;
        } else if (persist.exist(Book.class, book.getIsbn())) {
            return ServiceResult.DUPLICATE;
        } else {
            persist.add(book);
            return ServiceResult.OK;
        }
    }

    @Override
    public ServiceResult addDisc(Disc disc) {
        String regex = "^[0-9]{8,14}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(disc.getBarcode());

        if (disc.getDirector().isEmpty() || disc.getTitle().isEmpty() || (disc.getFsk() < 0 || disc.getFsk() > FSK_MAX)
                || !matcher.matches()) {
            return ServiceResult.BAD_REQUEST;
        } else if (persist.exist(Disc.class, disc.getBarcode())) {
            return ServiceResult.DUPLICATE;
        } else {
            persist.add(disc);
            return ServiceResult.OK;
        }
    }

    @Override
    public Book getBook(String isbn) {
        if (persist.exist(Book.class, isbn)) {
            return persist.get(Book.class, isbn);
        } else {
            return null;
        }
    }

    @Override
    public Book[] getBooks() {
        List<Book> books = persist.getAll(Book.class);
        return books.toArray(new Book[books.size()]);
    }

    @Override
    public ServiceResult updateBook(String isbn, Book book) {
        if (!persist.exist(Book.class, isbn)) {
            return ServiceResult.NOT_FOUND;
        }
        if ((book.getAuthor() == null || book.getAuthor().equals(""))
                && (book.getTitle() == null || book.getTitle().equals(""))) {
            return ServiceResult.BAD_REQUEST;
        }
        Book bookToEdit = persist.get(Book.class, isbn);
        if (book.getAuthor() != null && !book.getAuthor().equals("")) {
            bookToEdit.setAuthor(book.getAuthor());
        }
        if (book.getTitle() != null && !book.getTitle().equals("")) {
            bookToEdit.setTitle(book.getTitle());
        }
        persist.update(bookToEdit);
        return ServiceResult.OK;
    }

    @Override
    public Disc getDisc(String barcode) {
        if (persist.exist(Disc.class, barcode)) {
            return persist.get(Disc.class, barcode);
        } else {
            return null;
        }
    }

    @Override
    public Disc[] getDiscs() {
        List<Disc> discs = persist.getAll(Disc.class);
        return discs.toArray(new Disc[discs.size()]);
    }

    @Override
    public ServiceResult updateDisc(String barcode, Disc disc) {
        if (persist.exist(Disc.class, barcode)) {
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

    /**
     * Change the data of a disk. A disk with the given barcode has to exist. This has to be checked prior to calling this function.
     * Information that are left out or are empty will not be changed.
     * @param barcode the identifying barcode of the disc to change
     * @param disc    a Disc object containing the new information.
     * @return true if some data has been changed successfully, false otherwise
     */
    private boolean changeDisc(String barcode, Disc disc) {
        boolean change = false;
        Disc existDisc = persist.get(Disc.class, barcode);
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
        persist.update(existDisc);
        return change;
    }

}
