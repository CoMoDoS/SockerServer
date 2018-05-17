package Start;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Start
{
    public static void main(String[] args) throws IOException
    {

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

