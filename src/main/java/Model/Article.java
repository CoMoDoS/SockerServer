package Model;

import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
public class Article
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "incrementor")
    @GenericGenerator(name = "incrementor", strategy = "increment")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "abstract")
    private String abs;

    @Column(name = "author")
    private String author;

    @Column(name = "body")
    private String body;

   // private ArrayList<Article> relatedArticles = new ArrayList<Article>();


    public Article(String title, String abs, String author, String body) {
        this.title = title;
        this.abs = abs;
        this.author = author;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

//    public ArrayList<Article> getRelatedArticles() {
//        return relatedArticles;
//    }
//
//    public void setRelatedArticles(ArrayList<Article> relatedArticles) {
//        this.relatedArticles = relatedArticles;
//    }
//
//    public void addArticle(Article article){
//        this.relatedArticles.add(article);
//    }


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", abs='" + abs + '\'' +
                ", author='" + author + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
