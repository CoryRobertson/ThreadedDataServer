package com.github.coryrobertson.ThreadedDataServer.Server;

import com.github.coryrobertson.ThreadedDataServer.Message;
import com.github.coryrobertson.ThreadedDataServer.Messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread
{
    private Socket socket;

    private int id;

    private ObjectInputStream objectInputStream = null;
    private ObjectOutputStream objectOutputStream = null;

    public ClientHandler(Socket socket, int id)
    {
        this.socket = socket;
        this.id = id;
    }

    @Override
    public void run()
    {
        super.run();
        String input = "";
        while (!input.equals("exit"))
        {
            try
            {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("waiting on an object");
                input = (String)objectInputStream.readObject();
                Message msg = new Message(id,input);

                Messages.addMessage(msg);
                System.out.println("writing object to output stream");
                runCommand(input);

                objectOutputStream.writeObject(Messages.getMessages());

            }
            catch (IOException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void runCommand(String input)
    {
        ArrayList<Integer> valueList = new ArrayList<>();
        int firstSpace = input.indexOf(" ");


        if(input.equals("test"))
        {
            System.out.println("test command run");
        }

        String setTextCommand = "settext";

        if(input.substring(0,firstSpace).equals(setTextCommand))
        {
            String inputText = input.substring(firstSpace+1);
            System.out.println("set webtext to: " + inputText);

            ThreadedWebServer.setWebText(inputText);
        }


        //TODO: also check for exit commands to close more threads
    }

}
