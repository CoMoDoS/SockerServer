package Model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ArticleDAO
{
    public static EntityManager entityManager;
    public static EntityManagerFactory entityManagerFactory;

    public static void insert(Article article)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(article);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

    }

    public static Article findById(int id)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Article a1 = entityManager.find(Article.class, id);
        entityManager.close();
        entityManagerFactory.close();
        return a1;
    }

    public static void delete(int id)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Article a1 = entityManager.find(Article.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(a1);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void update(int id, Article article)
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        Article a1 = entityManager.find(Article.class, id);
        entityManager.getTransaction().begin();
        a1.setId(article.getId());
        a1.setAbs(article.getAbs());
        a1.setAuthor(article.getAuthor());
        a1.setBody(article.getBody());
        a1.setTitle(article.getTitle());
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

}
