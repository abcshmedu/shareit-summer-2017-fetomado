package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Medium;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of the MediaService interface.
 * It does the work behind the /media/ resource.
 */
public class MediaServiceImpl implements MediaService {

    private List<Medium> books;

    /**
     * Constructs a new instance.
     */
    public MediaServiceImpl() {
        books = new ArrayList<>();
        books.add(new Book("Die Känguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3"));
        books.add(new Book("what if?", "Randall Munroe", "978-3-8135-0625-5"));
    }

    @Override
    public Medium[] getBooks() {
        return books.toArray(new Medium[books.size()]);
    }
}
