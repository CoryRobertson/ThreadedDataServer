package com.github.coryrobertson.ThreadedDataServer.ReverseServer;

import com.github.coryrobertson.ThreadedDataServer.Client.MousePosition;

import java.util.Scanner;

/**
 * A web host that hosts the entire data to be sent to the other device, in this case sending angle values to a rasperry pi
 */
public class WebHost
{


    public static void main(String[] args)
    {
        System.out.println("hosting web page on port 8111...");
        Thread reverseServerThread = new Thread(new ThreadedReverseServer());

        reverseServerThread.start();

        //Scanner in = new Scanner(System.in); // maybe use this for manual setting?

        MousePosition mousePosition = new MousePosition();

        String angleString = "";

        while(true)
        {
            int servoXDegrees = (int) mousePosition.map(mousePosition.getMapRangeX(),-1,1,0,180); // here we are mapping the mouse position from a range of -1 to 1, to a range of 0 to 180
            int servoYDegrees = (int) mousePosition.map(mousePosition.getMapRangeY(),-1,1,0,180);
            angleString = servoXDegrees + "," + servoYDegrees; // we then append the angles to a string comma separated

            ThreadedReverseServer.webText = angleString; // and we post it to the web page
        }

    }

}
