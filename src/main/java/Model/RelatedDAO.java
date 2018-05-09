package Model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RelatedDAO {

    public static EntityManager entityManager;
    public static EntityManagerFactory entityManagerFactory;


    public static void insert(Related related)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(related);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

    }

    public static Related findById(int id)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Related a1 = entityManager.find(Related.class, id);
        entityManager.close();
        entityManagerFactory.close();
        return a1;
    }

    public static void delete(int id)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Related a1 = entityManager.find(Related.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(a1);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void update(int id, Related related)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Related a1 = entityManager.find(Related.class, id);
        entityManager.getTransaction().begin();
        a1.setId(related.getId());
        a1.setIdFrom(related.getIdFrom());
        a1.setIdTo(related.getIdTo());
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

}
