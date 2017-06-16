package edu.hm.shareit.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * This is the default implementation of our persistence layer.
 */
public class PersistenceImpl implements Persistence {

    @Inject
    private SessionFactory sessionFactory;
    private Session entityManager;
    private Transaction tx;

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
