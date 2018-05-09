package Model;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.security.MessageDigest;

public class WriterDAO {

    public static EntityManager entityManager;
    public static EntityManagerFactory entityManagerFactory;


    public static void insert(Writer writer)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(writer);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

    }

    public static Writer findById(int id)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Writer a1 = entityManager.find(Writer.class, id);
        entityManager.close();
        entityManagerFactory.close();
        return a1;
    }

    public static void delete(int id)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Writer a1 = entityManager.find(Writer.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(a1);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void update(int id, Writer writer)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Writer a1 = entityManager.find(Writer.class, id);
        entityManager.getTransaction().begin();
        a1.setId(writer.getId());
        a1.setParola(writer.getParola());
        a1.setName(writer.getName());
        a1.setEmail(writer.getEmail());
        a1.setStatus(writer.getStatus());
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public static Writer findByEmail(String email)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNamedQuery("Writer.findByEmail");
        query.setParameter("email",email);
        Writer a1 = (Writer) query.getSingleResult();

        entityManager.close();
        entityManagerFactory.close();
        return a1;
    }



}
