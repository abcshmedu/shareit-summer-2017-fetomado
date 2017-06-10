package edu.hm.shareit;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.*;

public class TestTest {

    private static SessionFactory sessionFactory;
    private Session entityManager;
    private Transaction tx;

    @BeforeClass
    public static void initialize() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @AfterClass
    public static void shutDown() {
        sessionFactory.close();
    }

    @Before
    public void setUp() {
        entityManager = sessionFactory.getCurrentSession();
        tx = entityManager.beginTransaction();
    }

    @After
    public void tearDown() {
        if (entityManager.isOpen()) {
            tx.commit();
        }
    }

    @Test
    public void test() {
        Book book = new Book("Die Kaenguru-Chroniken", "Marc-Uwe Kling", "978-3-548-37623-3");
        entityManager.persist(book);
        Disc disc =  new Disc("Deadpool", "456789123", "Tim Miller", 16);
        entityManager.persist(disc);
    }

}
