package Start;



import Model.*;
import Model.Writer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


// ClientHandler class
class ClientHandler extends Thread
{

    private final Socket s;

    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private Gson gson = new Gson();

//    private JsonObject jsonObject = new JsonObject();
    private JsonParser jsonParser = new JsonParser();
    private JsonArray jsonArray;
    private String msg;
    private String aux;
    private String json;
    private ArrayList<Article> list = new ArrayList<Article>();


    // Constructor
    public ClientHandler(Socket s, ObjectInputStream is, ObjectOutputStream os)
    {
        this.s = s;
        this.inputStream = is;
        this.outputStream = os;
    }

    @Override
    public void run()
    {
        Message received = null;
        while (true)
        {
            try {

                try {
                    received = (Message)inputStream.readObject();
                    System.out.println(received.toString());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if(received.getB().equals("Exit"))
                {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }
                // write on output stream based on the
                // answer from the client
                switch (received.getB())
                {

                    case "create-writer" :
                        jsonArray = (JsonArray) jsonParser.parse(received.getA());
                        aux = String.valueOf(jsonArray.get(0));
                        String name1 = aux.replace("\"","");
                        aux = String.valueOf(jsonArray.get(1));
                        String email1 = aux.replace("\"","");
                        aux = String.valueOf(jsonArray.get(2));
                        String pass1 = aux.replace("\"","");
                        aux = String.valueOf(jsonArray.get(3));
                        String stat1 = aux.replace("\"","");
                        Writer writer12= new Writer(name1,email1,pass1,stat1);
                        try{
                            WriterDAO.insert(writer12);
                        }catch (Exception e)
                        {
                            outputStream.writeObject(new Message("create-writer","error"));
                        }
                        outputStream.writeObject(new Message("create-writer","ok"));
                        break;

                    case "get-related" :
                        list = RelatedDAO.selectAll(Integer.parseInt(received.getA()));
                        json = gson.toJson(list);
                        outputStream.writeObject(new Message( "get-related", json));
                        break;

                    case "get-article" :
                        Article article = ArticleDAO.findById(Integer.parseInt(received.getA()));
                        System.out.println(article.toString());
                        String json1 = gson.toJson(article);
                        outputStream.writeObject(new Message("get-article" , json1));
                        break;

                    case "get-articles" :
                        ArrayList<Article> list = ArticleDAO.selectAll();
                        json= gson.toJson(list);
                        outputStream.writeObject(new Message("get-articles", json));
                        break;

                    case "login-writer" :

                        jsonArray = (JsonArray) jsonParser.parse(received.getA());
                        aux = String.valueOf(jsonArray.get(0));
                        String email = aux.replace("\"","");
                        aux = String.valueOf(jsonArray.get(1));
                        String pass = aux.replace("\"","");

                        Writer writer1 = new Writer();
                        Admin admin1 = new Admin();

                        try {
                            writer1 = WriterDAO.findByEmail(email);
                        }catch (Exception e) {
                        }
                        try {
                            admin1 = AdminDAO.findByEmail(email);
                        }catch (Exception e) {
                        }

                        if (admin1.getName() != null &&  admin1.getParola().compareTo(pass) == 0)
                            outputStream.writeObject(new Message("admin", "ok"));

                        if (writer1.getName() != null &&  writer1.getParola().compareTo(pass) == 0)
                            outputStream.writeObject(new Message("writer", "ok"));

                        else
                            outputStream.writeObject(new Message("login-writer", "no"));
                        break;

                    case "create-article" :
                        jsonArray = (JsonArray) jsonParser.parse(received.getA());
                        aux = String.valueOf(jsonArray.get(0));
                        String titleArticle = aux.replace("\"","");
                        aux = String.valueOf(jsonArray.get(1));
                        String absArticle = aux.replace("\"","");
                        aux = String.valueOf(jsonArray.get(2));
                        String autorArticle = aux.replace("\"","");
                        aux = String.valueOf(jsonArray.get(3));
                        String bodyArticle = aux.replace("\"","");

                        Article article12 = new Article(titleArticle,absArticle,autorArticle,bodyArticle);

                        try
                        {
                            ArticleDAO.insert(article12);
                        }catch (Exception e)
                        {
                            outputStream.writeObject(new Message("create-article", "error"));
                        }
                        outputStream.writeObject(new Message("create-article","ok"));
                        break;

                    case "update-article" :

                        jsonArray = (JsonArray) jsonParser.parse(received.getA());
                        aux = String.valueOf(jsonArray.get(0));
                        String idArticle = aux.replace("\"","");
                        int idArt = Integer.parseInt(idArticle);
                        aux = String.valueOf(jsonArray.get(1));
                        String titleArticle1 = aux.replace("\"","");
                        aux = String.valueOf(jsonArray.get(2));
                        String absArticle1 = aux.replace("\"","");
                        aux = String.valueOf(jsonArray.get(3));
                        String autorArticle1 = aux.replace("\"","");
                        aux = String.valueOf(jsonArray.get(4));
                        String bodyArticle1 = aux.replace("\"","");

                        Article article21 = new Article(titleArticle1,absArticle1,autorArticle1,bodyArticle1);
                        article21.setId(idArt);

                        try{
                            ArticleDAO.update(idArt,article21);
                        }catch (Exception e)
                        {
                            outputStream.writeObject(new Message("update-article", "error"));
                        }
                        outputStream.writeObject(new Message("update-article","ok"));
                        break;

                    case "delete-article" :
                        int idDelete = Integer.parseInt(received.getA());
                        try {
                            ArticleDAO.delete(idDelete);
                        }catch (Exception e)
                        {
                            outputStream.writeObject(new Message("delete-article", "error"));
                        }
                        outputStream.writeObject(new Message("delete-article","ok"));
                        break;

                    case "add-related" :
                        jsonArray = (JsonArray) jsonParser.parse(received.getA());
                        aux = String.valueOf(jsonArray.get(0));
                        String idArticle1 = aux.replace("\"","");
                        int idArt1 = Integer.parseInt(idArticle1);
                        aux = String.valueOf(jsonArray.get(1));
                        String idArticle2 = aux.replace("\"","");
                        int idArt2 = Integer.parseInt(idArticle2);
                        try{
                            RelatedDAO.insert(new Related(idArt1,idArt2));
                        }catch (Exception e)
                        {
                            outputStream.writeObject(new Message("add-related", "error"));
                        }
                        outputStream.writeObject(new Message("add-related","ok"));
                        break;

                    case "get-writer" :
                        Writer writer = WriterDAO.findByEmail(received.getA());
                        json = gson.toJson(writer);
                        outputStream.writeObject(new Message("get-writer",json));
                        break;

                    default:
                        outputStream.writeObject(new Message("Defaul","Default"));
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.inputStream.close();
            this.outputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }


}