package edu.hm.shareit.persistence;

import edu.hm.HibernateUtils;
import edu.hm.shareit.models.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.io.Serializable;
import java.util.List;

public class PersistenceImpl implements Persistence {

    private static SessionFactory sessionFactory;
    private Session entityManager;
    private Transaction tx;

    public PersistenceImpl() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }


    @Override
    public <T, K extends Serializable> boolean exist(Class<T> tClass, K key) {
        entityManager = sessionFactory.getCurrentSession();
        tx = entityManager.beginTransaction();
        T elem = entityManager.get(tClass, key);
        tx.commit();
        return elem != null;
    }

    @Override
    public <T, K extends Serializable> T get(Class<T> tClass, K key) {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        T elem = entityManager.get(tClass, key);
        tx.commit();
        return elem;
    }

    @Override
    public <T> void add(T elem) {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        entityManager.save(elem);
        try {
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
        }
    }

    @Override
    public <T> List<T> getAll(Class<T> tClass) {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        String queryString = "from " + tClass.getSimpleName();
        List<T> list = entityManager.createQuery(queryString).list();
        tx.commit();
        return list;
    }

    @Override
    public <T> void update(T elem) {
        entityManager = sessionFactory.getCurrentSession();
        Transaction tx = entityManager.beginTransaction();
        entityManager.saveOrUpdate(elem);
        tx.commit();
    }

}