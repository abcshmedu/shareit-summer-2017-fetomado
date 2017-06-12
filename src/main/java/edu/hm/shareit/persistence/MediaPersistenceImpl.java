package edu.hm.shareit.persistence;

import edu.hm.HibernateUtils;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MediaPersistenceImpl implements MediaPersistence {

    private static SessionFactory sessionFactory;
    private Session entityManager;
    private Transaction tx;

    public MediaPersistenceImpl() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public boolean bookExist(String isbn) {
        entityManager = sessionFactory.getCurrentSession();
        tx = entityManager.beginTransaction();
        Book book = entityManager.get(Book.class, isbn);
        tx.commit();
        return book != null;
    }

    @Override
    public Book getBook(String isbn) {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        Book book = entityManager.get(Book.class, isbn);
        tx.commit();
        return book;
    }

    @Override
    public void addBook(Book book) {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        if (entityManager.get(Book.class, book.getIsbn()) == null) {
            entityManager.save(book);
        }
        tx.commit();
    }

    @Override
    public List<Book> getBooks() {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        String queryString = "from Book";
        List<Book> books = entityManager.createQuery(queryString).list();
        tx.commit();
        return books;
    }

    @Override
    public void updateBook(Book book) {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        entityManager.saveOrUpdate(book);
        tx.commit();
    }

    @Override
    public boolean discExist(String barcode) {
        return entityManager.get(Disc.class, barcode) != null;
    }

    @Override
    public void addDisc(Disc disc) {
        if (!discExist(disc.getBarcode())) {
            entityManager.save(disc);
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
        entityManager.saveOrUpdate(disc);
    }

}
