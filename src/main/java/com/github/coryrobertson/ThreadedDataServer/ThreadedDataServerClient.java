package com.github.coryrobertson.ThreadedDataServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ThreadedDataServerClient
{

    public static ObjectInputStream objectInputStream = null;

    private static ObjectOutputStream objectOutputStream = null;

    public static ArrayList<Message> messages = new ArrayList<>();

    public static void main(String[] args)
    {
        Socket socket;
        Scanner in = new Scanner(System.in);
        String input = "";
        Thread clientMessageListThread = new Thread(new ClientMessageList());

        try
        {
            socket = new Socket("localhost", 5000);
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
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
