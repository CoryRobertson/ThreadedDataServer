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
        MousePosition mousePosition = new MousePosition();
        boolean mousePosSubmitter = false;

        System.out.println("Mouse position submitter(y/n): ");
        input = in.nextLine();
        if(input.toLowerCase().equals("y")) {mousePosSubmitter = true;}

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
                if(!mousePosSubmitter)
                {
                    if (in.hasNextLine()) {
                        input = in.nextLine();

                        objectOutputStream.writeObject(input);
                    }
                }
                else
                {
                    int servoXDegrees = (int) mousePosition.map(mousePosition.getMapRangeX(),-1,1,0,180);
                    int servoYDegrees = (int) mousePosition.map(mousePosition.getMapRangeY(),-1,1,0,180);
                    String output = "servos " + servoXDegrees + " " + servoYDegrees;
                    objectOutputStream.writeObject(output);
                    try
                    {
                        Thread.sleep(1500);
                    }
                    catch (InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }
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
