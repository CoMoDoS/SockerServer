package Start;

import Model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Start
{
    public static void main(String[] args) throws IOException
    {
//        AdminDAO.insert(new Admin("admin1","a@a.a","a","ac"));
//        WriterDAO.insert(new Writer("w1","w@w.w","w", "ac"));
//        WriterDAO.insert(new Writer("w2","w2@w.w","w", "ac"));
//
//        ArticleDAO.insert(new Article("first","the first article","eu","There was created the first article for testing purposes"));
//        ArticleDAO.insert(new Article("second","the second article","el","There was created the second article for testing purposes"));
//        ArticleDAO.insert(new Article("third","the third article","eu","There was created the third article for testing purposes"));

//        RelatedDAO.insert(new Related(1,2));
//        RelatedDAO.insert(new Related(1,3));
//        RelatedDAO.insert(new Related(2,3));
//        RelatedDAO.insert(new Related(3,1));
//        Article article1= ArticleDAO.findById(1);
//        Article article2 = ArticleDAO.findById(2);
//        Article article3 = ArticleDAO.findById(3);
//
//        ArrayList<Article> list = new ArrayList<Article>();
//        list = RelatedDAO.selectAll(1);
//
//        for ( Article a : list )
//            System.out.println(a.toString());
//        System.out.println(article1.toString());
//        article1.setTitle("modificat");
//        ArticleDAO.update(1,article1);
//
//        System.out.println(ArticleDAO.findById(1).toString());

//        server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);
        start(ss);

    }

    public static void start(ServerSocket ss) throws IOException {
        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams

                ObjectInputStream is = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new ClientHandler(s,is, os);

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}

