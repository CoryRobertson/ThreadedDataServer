package com.github.coryrobertson.ThreadedDataServer.Server;

import com.github.coryrobertson.ThreadedDataServer.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadedDataServer implements Runnable
{

    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    private static final int maxClients = -1;
    public static void main(String[] args)
    {


        int clientCount = 0;

//        System.out.println("Starting webserver on new thread...");
//
//        Thread webServer = new Thread(new ThreadedWebServer());
//        webServer.start();
        System.out.println("Waiting on port 5000...");

        while(true)
        {
            try (ServerSocket serverSocket = new ServerSocket(5000))
            {

                Socket clientSocket = serverSocket.accept();
                if(clientCount >= maxClients && maxClients > 0)
                {//this case occurs when too many clients are connecting
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    ArrayList<Message> msgs = new ArrayList<>();
                    msgs.add(new Message(0,"server full"));
                    objectOutputStream.writeObject(msgs);
                    clientSocket.close();
                    System.out.println("kicking new client, max client size reached");
                }
                else
                {//this case is when we allow a client to connect
                    clients.add(new ClientHandler(clientSocket, clientCount));
                    clients.get(clientCount).start();
                    clientCount++;

                    System.out.println("new client thread began, client count: " + clients.size());
                }

            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public void run()
    {
        main(new String[0]);
    }
}
