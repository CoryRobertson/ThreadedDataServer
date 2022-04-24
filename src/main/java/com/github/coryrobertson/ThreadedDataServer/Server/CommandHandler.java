package com.github.coryrobertson.ThreadedDataServer.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandHandler
{
    private int[] servoAngles = new int[16];
    public CommandHandler() {}

    /**
     * converts a string of inputs into an array with the headers added to the front
     * @param input
     * @param headers
     * @return
     */
    public String[] parseInput(String input, String... headers)
    {
        ArrayList<String> inputCommands = new ArrayList<>();
        for(int i = 0; i < headers.length; i++)
        {
            inputCommands.add(headers[i]);
        }

        String[] arr = input.split(" ");

        for(int i = 0; i < arr.length; i++)
        {
            inputCommands.add(arr[i]);
        }

        String[] command = inputCommands.toArray(new String[inputCommands.size()]);

        return command;
    }

    /**
     * a wrapped function to change the servo angle
     * @param inputCommand
     * @throws IllegalArgumentException when servo angle is not within acceptable range or servo number is not in an acceptable range
     */
    public void changeServoAngle(String inputCommand) throws IllegalArgumentException
    {

        String[] command = parseInput(inputCommand,"python","./main.py");
        int servoNumber = Integer.parseInt(command[2]);
        int servoAngle = Integer.parseInt(command[3]);

        if(servoNumber < 0 || servoNumber > 15)
        {
            throw new IllegalArgumentException("Servo number not in expected range of 0-15: " + servoNumber);
        }
        if(servoAngle < 0 || servoAngle > 180)
        {
            throw new IllegalArgumentException("Servo angle not in expected range of 0-180: " + servoAngle);
        }

        System.out.println("running servo command with args: " + servoNumber + servoAngle);
        servoAngles[servoNumber] = servoAngle;

        System.out.println(Arrays.toString(servoAngles));
        String output = runCommand(command);
        System.out.println(output);

    }

    private String runCommand(String... args)
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
