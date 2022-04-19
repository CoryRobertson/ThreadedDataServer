package com.github.coryrobertson.ThreadedDataServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread
{
    private Socket socket;

    private int id;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

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
                input = (String)objectInputStream.readObject();
                Message msg = new Message(id,input);

                Messages.addMessage(msg);

            }
            catch (IOException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }


        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
