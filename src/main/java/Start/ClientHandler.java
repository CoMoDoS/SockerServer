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
                    System.out.println(received.getA() + received.getB());
                    for( Message i : received.getMessageList())
                    {
                        System.out.println(i.getA() + " " + i.getB());
                    }
//                    outputStream.writeObject(message);

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
                        System.out.println(email + aux1);
                        try {
                            Writer writer1 = WriterDAO.findByEmail(email);

                            if (writer1.getParola().compareTo(pass) == 0)
                                outputStream.writeObject(new Message("ok", "ok"));
                            else
                                outputStream.writeObject(new Message("no", "no"));
                        }catch (Exception e)
                        {
                            outputStream.writeObject(new Message("error","error"));
                        }


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