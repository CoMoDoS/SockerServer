package Model;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.security.MessageDigest;

public class AdminDAO {

    public static EntityManager entityManager;
    public static EntityManagerFactory entityManagerFactory;


    public static void insert(Admin admin)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(admin);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

    }

    public static Admin findById(int id)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Admin a1 = entityManager.find(Admin.class, id);
        entityManager.close();
        entityManagerFactory.close();
        return a1;
    }

    public static void delete(int id)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Admin a1 = entityManager.find(Admin.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(a1);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void update(int id, Admin admin)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Admin a1 = entityManager.find(Admin.class, id);
        entityManager.getTransaction().begin();
        a1.setId(admin.getId());
        a1.setParola(admin.getParola());
        a1.setName(admin.getName());
        a1.setEmail(admin.getEmail());
        a1.setStatus(admin.getStatus());
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public static Admin findByEmail(String email)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNamedQuery("Admin.findByEmail");
        query.setParameter("email",email);
        Admin a1 = (Admin) query.getSingleResult();

        entityManager.close();
        entityManagerFactory.close();
        return a1;
    }



}
