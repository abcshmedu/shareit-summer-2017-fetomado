package edu.hm.shareit.models;

/**
 * This is the model class representing a book.
 */
public class Book extends Medium {

    private String author, isbn;

    /**
     * Constructs a new book object.
     * @param title the title of the book
     * @param author the author of the book
     * @param isbn the ISBN number of the book
     */
    public Book(String title, String author, String isbn) {
        super(title);
        this.author = author;
        this.isbn = isbn;
    }

    /**
     * Private constructor needed for Jackson compatibility
     */
    private Book() {
        super("");
    }

    /**
     * Returns the author of the book.
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the ISBN number of the book.
     * @return the ISBN number
     */
    public String getIsbn() {
        return isbn;
    }

}
