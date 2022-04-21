package com.github.coryrobertson.ThreadedDataServer.Client;

import java.awt.*;

public class MousePosition
{
    private static boolean running = true; //TODO: eventually make this not static once i implement it differently

    public static void main(String[] args) throws InterruptedException
    {
        while(running)
        {
            Point mousePos = MouseInfo.getPointerInfo().getLocation();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = screenSize.width;
            int height = screenSize.height;
            //System.out.println(mousePos);
            //System.out.println(width + ", " + height);
            double radAngle = Math.atan2(mousePos.getY() - (height/2), mousePos.getX() - (width/2));
            double degreeAngle = Math.toDegrees(radAngle);
            //System.out.println(radAngle);
            System.out.println(degreeAngle);
            Thread.sleep(500);
        }
    }
}
