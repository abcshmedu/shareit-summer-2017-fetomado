package edu.hm.shareit.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * This is the model class representing a book.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Book extends Medium {

    @Id private String isbn;
    private String author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        return author != null ? author.equals(book.author) : book.author == null;
    }

    @Override
    public int hashCode() {
        int result = isbn != null ? isbn.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    /**
     * Constructs a new book object.
     * @param title  the title of the book
     * @param author the author of the book
     * @param isbn   the ISBN number of the book
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
     * @return author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     * @param author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the ISBN number of the book.
     * @return isbn unique identifier of the book
     */
    public String getIsbn() {
        return isbn;
    }

}
