package com.github.coryrobertson.ThreadedDataServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadedDataServer
{

    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args)
    {

        System.out.println("Waiting on port 5000");

        int clientCount = 0;
        while(true)
        {
            try (ServerSocket serverSocket = new ServerSocket(5000))
            {
                Socket clientSocket = serverSocket.accept();
                clients.add(new ClientHandler(clientSocket,clientCount));
                clients.get(clientCount).start();
                clientCount++;

                System.out.println("new client thread began, client count: " + clients.size());

            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }


    }
}
