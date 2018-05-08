package Start;



import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;


// ClientHandler class
class ClientHandler extends Thread
{

    final Socket s;

    final ObjectInputStream inputStream;
    final ObjectOutputStream outputStream;
    Message m1 = new Message(11,"Mes1");
    Message m2 = new Message(12,"Mes2");


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
                outputStream.writeObject(new Message(1,"Type Exit to terminate connection"));
                // receive the answer from client
                Gson gson = new Gson();
                String json = gson.toJson(m1);
                try {
                    received = (Message)inputStream.readObject();
                    received.addList(m1);
                    received.addList(m2);
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
                        outputStream.writeObject(new Message(20,"First type"));

                        break;

                    case "Time" :
                        outputStream.writeObject(new Message(21,"Second type"));

                        break;

                    default:
                        outputStream.writeObject(new Message(30,"Default"));
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