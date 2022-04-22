package com.github.coryrobertson.ThreadedDataServer.Server;

import com.github.coryrobertson.ThreadedDataServer.Message;
import com.github.coryrobertson.ThreadedDataServer.Messages;

import java.io.*;
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

        String servoCommand = "servo";
        if(input.substring(0,firstSpace).equals(servoCommand))
        {
            String inputCommand = input.substring(firstSpace + 1);

            ArrayList<String> inputCommands = new ArrayList<>();
            inputCommands.add("python");
            inputCommands.add("./main.py");

            String[] arr = inputCommand.split(" ");

            for(int i = 0; i < arr.length; i++)
            {
                inputCommands.add(arr[i]);
            }

            String[] command = inputCommands.toArray(new String[inputCommands.size()]);

            System.out.println("running servo command with args: " + command[2] + command[3]);

            String output = runCommand(command);
            System.out.println(output);

        }

        //TODO: also check for exit commands to close more threads
    }

    private static String runCommand(String... args)
    {
        ProcessBuilder processBuilder = new ProcessBuilder().command(args);
        StringBuilder sb = new StringBuilder();
        String output = null;
        int counter = 0;


        try
        {
            Process process = processBuilder.start();
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((output = bufferedReader.readLine()) != null)
            {
                sb.append(output);

                //process.wait();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return sb.toString();

    }

}
