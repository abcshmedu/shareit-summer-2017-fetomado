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
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        tx.commit();
        Disc disc = entityManager.get(Disc.class, barcode);
        return disc != null;
    }

    @Override
    public void addDisc(Disc disc) {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        if (entityManager.get(Disc.class, disc.getBarcode()) == null) {
            entityManager.save(disc);
        }
        tx.commit();
    }

    @Override
    public List<Disc> getDiscs() {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        String queryString = "from Disc";
        List<Disc> disc = entityManager.createQuery(queryString).list();
        tx.commit();
        return disc;
    }

    @Override
    public Disc getDisc(String barcode) {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        Disc disc =  entityManager.get(Disc.class, barcode);
        tx.commit();
        return disc;
    }

    @Override
    public void updateDisc(Disc disc) {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        entityManager.saveOrUpdate(disc);
        tx.commit();
    }

}
