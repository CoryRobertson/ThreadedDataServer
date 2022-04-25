package com.github.coryrobertson.ThreadedDataServer.Server;

import com.github.coryrobertson.ThreadedDataServer.Message;
import com.github.coryrobertson.ThreadedDataServer.Messages;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * An object that runs in a separate thread and handles a client with its own socket
 */
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

    /**
     * Runs a command from the client on the server, here is where we do the handling.
     * @param input
     */
    private void runCommand(String input)
    {

        //TODO: add exit command checking cause we die if we dont

        if(input.contains("exit")) {return;}

        //ArrayList<Integer> valueList = new ArrayList<>();
        int firstSpace;
        String compare;
        String inputCommand;
        try {
            firstSpace = input.indexOf(" ");
            compare = input.substring(0, firstSpace);
            inputCommand = input.substring(firstSpace + 1);
        } catch (StringIndexOutOfBoundsException e)
        {
            return;
        }




        if(compare.equals("test"))
        {
            System.out.println("test command run");
        }

        String setTextCommand = "settext";

        if(compare.equals(setTextCommand))
        {
            String inputText = input.substring(firstSpace+1);
            System.out.println("set webtext to: " + inputText);

            ThreadedWebServer.setWebText(inputText);
        }

        String servoCommand = "servo";
        if(compare.equals(servoCommand))
        {
            ThreadedDataServer.commandHandler.changeServoAngle(inputCommand);

        }

        String servosCommand = "servos";
        if (compare.equals(servosCommand))
        {
            ThreadedDataServer.commandHandler.changeServoAngles(inputCommand);

        }

        //TODO: also check for exit commands to close more threads
    }

}
