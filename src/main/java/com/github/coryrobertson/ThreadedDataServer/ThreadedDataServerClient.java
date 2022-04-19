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

    private static ObjectInputStream objectInputStream = null;

    private static ObjectOutputStream objectOutputStream = null;

    public static ArrayList<Message> messages = new ArrayList<>();

    public static void main(String[] args)
    {
        Socket socket;
        Scanner in = new Scanner(System.in);
        String input = "";
        Thread clientMessageListThread = new Thread(new ClientMessageList());
        //Thread clientMessageHandler;
        //int lastKnownSize = -1;

        try
        {
            socket = new Socket("localhost", 5000);
            clientMessageListThread.start();
            //clientMessageHandler = new Thread(new ClientMessageHandler(socket, objectOutputStream, objectInputStream));
            //clientMessageHandler.start();
            while(!input.equals("exit"))
            {
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());

                //System.out.println("hasnext: " + in.hasNext());
                if(in.hasNextLine())
                {
                    input = in.nextLine();

                    objectOutputStream.writeObject(input);
                }

                messages = (ArrayList<Message>) objectInputStream.readObject();

            }
        }
        catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
