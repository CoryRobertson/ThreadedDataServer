package com.github.coryrobertson.ThreadedDataServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ThreadedDataServerClient
{
    private static ObjectInputStream objectInputStream = null;
    private static ObjectOutputStream objectOutputStream = null;

    public static void main(String[] args)
    {
        Socket socket;
        Scanner in = new Scanner(System.in);
        String input = "";

        try
        {
            socket = new Socket("localhost", 5000);

            while(!input.equals("exit"))
            {
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                input = in.nextLine();
                objectOutputStream.writeObject(input);
            }
        }
        catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
