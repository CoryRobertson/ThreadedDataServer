package com.github.coryrobertson.ThreadedDataServer.Client;

import com.github.coryrobertson.ThreadedDataServer.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ThreadedDataServerClient extends Thread
{

    public static ObjectInputStream objectInputStream = null;

    private static ObjectOutputStream objectOutputStream = null;

    public static ArrayList<Message> messages = new ArrayList<>();

    @Override
    public void run()
    {
        main(new String[0]);
    }

    public static void main(String[] args)
    {
        Socket socket;
        Scanner in = new Scanner(System.in);
        String input = "";
        ClientMessageList clientMessageListThread = new ClientMessageList();
        String host;

        try
        {
            System.out.println("Enter host to connect to: ");
            host = in.nextLine();
            socket = new Socket(host, 5000);
            clientMessageListThread.start();

            while(!input.equals("exit"))
            {
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                //messages = (ArrayList<Message>) objectInputStream.readObject();

                if(in.hasNextLine())
                {
                    input = in.nextLine();

                    objectOutputStream.writeObject(input);
                }

                messages = (ArrayList<Message>) objectInputStream.readObject();

            }
            System.out.println("disconnected...");
            clientMessageListThread.stopThread();
            System.exit(0);
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
