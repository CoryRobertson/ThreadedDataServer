package com.github.coryrobertson.ThreadedDataServer.Server;

import com.github.coryrobertson.ThreadedDataServer.Message;
import com.github.coryrobertson.ThreadedDataServer.Messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        if(input.equals("test"))
        {
            System.out.println("test command run");
        }
        //TODO: make command that changes webtext in ThreadedWebServer, would allow for some cool stuff!

        //TODO: also check for exit commands to close more threads
    }

}
