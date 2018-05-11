package Start;



import Model.*;
import Model.Writer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


// ClientHandler class
class ClientHandler extends Thread
{

    final Socket s;

    final ObjectInputStream inputStream;
    final ObjectOutputStream outputStream;
    Message m1 = new Message("11","Mes1");
    Message m2 = new Message("11","Mes2");
    Gson gson = new Gson();

    JsonObject jsonObject = new JsonObject();
    JsonParser jsonParser = new JsonParser();
    JsonArray jsonArray;
    String msg;
    String aux;
    String json;
    ArrayList<Article> list = new ArrayList<Article>();


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

                // Ask user what he wants

//                String json = gson.toJson(m1);
//                outputStream.writeObject(json);
                // receive the answer from client

                try {
                    received = (Message)inputStream.readObject();
//
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
                switch (received.getB()) {

                    case "Date" :
                        outputStream.writeObject(new Message("20","First type"));

                        break;

                    case "Time" :
                        outputStream.writeObject(new Message("21","Second type"));
                        break;
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
                            outputStream.writeObject(new Message("error","error"));
                        }

                        outputStream.writeObject(new Message("ok","ok"));

                        break;
                    case "get-related" :
                        list = RelatedDAO.selectAll(Integer.parseInt(received.getA()));
                        json = gson.toJson(list);
                        outputStream.writeObject(new Message("ok", json));
                        break;
                    case "get-article" :
                        Article article = ArticleDAO.findById(Integer.parseInt(received.getA()));
                        System.out.println(article.toString());
                        String json1 = gson.toJson(article);
                        outputStream.writeObject(new Message("31", json1));
                        break;
                    case "get-articles" :
                        ArrayList<Article> list = ArticleDAO.selectAll();
                        json= gson.toJson(list);
                        outputStream.writeObject(new Message("32", json));
                        break;
                    case "login-writer" :

                        System.out.println("gsdooon :   " + received.getA());
                        jsonArray = (JsonArray) jsonParser.parse(received.getA());
                        aux = String.valueOf(jsonArray.get(0));
                        String email = aux.replace("\"","");
                        String aux1 = String.valueOf(jsonArray.get(1));
                        String pass = aux1.replace("\"","");
                        System.out.println(email + pass);

                        Writer writer1 = new Writer();
                        Admin admin1 = new Admin();

                        int wr = 1;
                        int ad = 1;
                        try {
                            writer1 = WriterDAO.findByEmail(email);
                        }catch (Exception e)
                        {
                           wr = 0;
                            //outputStream.writeObject(new Message("error","errorWriter"));
                        }
                        try {
                            admin1 = AdminDAO.findByEmail(email);
                        }catch (Exception e)
                        {
                            ad = 0;
//                            outputStream.writeObject(new Message("error","errorAdmin"));
                        }

                        if (admin1.getName() != null &&  admin1.getParola().compareTo(pass) == 0)
                            outputStream.writeObject(new Message("admin", "ok"));
//                        else
//                            outputStream.writeObject(new Message("no", "no"));

                        if (writer1.getName() != null &&  writer1.getParola().compareTo(pass) == 0)
                            outputStream.writeObject(new Message("writer", "ok"));
//
                        else
                            outputStream.writeObject(new Message("no", "no"));
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
                            outputStream.writeObject(new Message("error", "error"));
                        }

                        outputStream.writeObject(new Message("ok","ok"));
                        break;

                    case "update-article" :

                        jsonArray = (JsonArray) jsonParser.parse(received.getA());
                        System.out.println("update-article :    " +  received.toString());

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
                        System.out.println(idArt +  "   update article : " + article21.toString());
                        article21.setId(idArt);

                        try{
                            ArticleDAO.update(idArt,article21);
                        }catch (Exception e)
                        {
                            outputStream.writeObject(new Message("error", "error"));
                        }
                        outputStream.writeObject(new Message("ok","ok"));
                        break;

                    case "delete-article" :
                        int idDelete = Integer.parseInt(received.getA());
                        try {
                            ArticleDAO.delete(idDelete);
                        }catch (Exception e)
                        {
                            outputStream.writeObject(new Message("error", "error"));
                        }
                        outputStream.writeObject(new Message("ok","ok"));
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
                            outputStream.writeObject(new Message("error", "error"));
                        }
                        outputStream.writeObject(new Message("ok","ok"));
                        break;


                    case "get-writer" :
                        Writer writer = WriterDAO.findByEmail(received.getA());
                        json = gson.toJson(writer);
                        outputStream.writeObject(new Message("33",json));
                        break;
                    default:
                        outputStream.writeObject(new Message("30","Default"));
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