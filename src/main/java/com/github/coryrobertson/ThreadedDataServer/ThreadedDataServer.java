package com.github.coryrobertson.ThreadedDataServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadedDataServer
{

    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    private static final int maxClients = -1;
    public static void main(String[] args)
    {

        System.out.println("Waiting on port 5000");

        int clientCount = 0;

        while(true)
        {
            try (ServerSocket serverSocket = new ServerSocket(5000))
            {

                Socket clientSocket = serverSocket.accept();
                if(clientCount >= maxClients && maxClients > 0)
                {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    ArrayList<Message> msgs = new ArrayList<>();
                    msgs.add(new Message(0,"server full"));
                    objectOutputStream.writeObject(msgs);
                    clientSocket.close();
                    System.out.println("kicking new client, max client size reached");
                }
                else
                {
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
}
