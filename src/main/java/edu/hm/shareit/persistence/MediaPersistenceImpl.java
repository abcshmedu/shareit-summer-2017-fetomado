package edu.hm.shareit.persistence;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class MediaPersistenceImpl implements MediaPersistence {

    private static SessionFactory sessionFactory;
    private Session entityManager;
    private Transaction tx;

    public MediaPersistenceImpl() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        entityManager = sessionFactory.getCurrentSession();
        tx = entityManager.beginTransaction();
    }

    @Override
    public boolean bookExist(String isbn) {
        return entityManager.get(Book.class, isbn) != null;
    }

    @Override
    public Book getBook(String isbn) {
        return entityManager.get(Book.class, isbn);
    }

    @Override
    public void putBook(Book book) {
        if(!bookExist(book.getIsbn())) {
            entityManager.persist(book);
        }
    }

    @Override
    public List<Book> getBooks() {
        String queryString = "from Book";
        return entityManager.createQuery(queryString).list();
    }

    @Override
    public void updateBook(Book book) {
        entityManager.update(book);
    }

    @Override
    public boolean discExist(String barcode) {
        return entityManager.get(Disc.class, barcode) != null;
    }

    @Override
    public void putDisc(Disc disc) {
        if(!discExist(disc.getBarcode())) {
            entityManager.persist(disc);
        }

    }

    @Override
    public List<Disc> getDiscs() {
        String queryString = "from Disc";
        return entityManager.createQuery(queryString).list();
    }

    @Override
    public Disc getDisc(String barcode) {
        return entityManager.get(Disc.class, barcode);
    }

    @Override
    public void updateDisc(Disc disc) {
        entityManager.update(disc);
    }

}
