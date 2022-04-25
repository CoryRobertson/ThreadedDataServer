package com.github.coryrobertson.ThreadedDataServer.Client;

import java.awt.*;

public class MousePosition implements Runnable
{

    private boolean running = true;

    private int mouseX = 0;
    private int mouseY = 0;

    private int angle = 0;

    private double mapRangeX;
    private double mapRangeY;


    public static void main(String[] args) throws InterruptedException
    {
        MousePosition mousePosition = new MousePosition();
        Thread mousePositionThread = new Thread(mousePosition);
        mousePositionThread.start();
        System.out.println("thread began");
        Thread.sleep(10000);
        mousePosition.stop();
        System.out.println("thread stopped");

    }

    /**
     * Probably don't actually run this as a thread, its much more efficient to simply call the functions.
     */
    @Override
    public void run()
    {
        while (running)
        {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.width;
            double height = screenSize.height;

            angle = (int) getAngleFromCenter();
            mouseX = getMouseX();
            mouseY = getMouseY();
            System.out.println("mousex: " + mouseX);
            System.out.println("mousey: " + mouseY);

            mapRangeX = getMapRangeX();
            mapRangeY = getMapRangeY();

            System.out.println("x range: " + mapRangeX);
            System.out.println("y range: " + mapRangeY);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            /*

            mapRangeX, mapRangeY

            -1,-1    |    1,-1
                     |
            --------0,0-------
                     |
            -1,1     |     1,1

             */
            
        }
    }


    public void stop()
    {
        running = false;
    }


    public int getMouseX()
    {
        return (int) MouseInfo.getPointerInfo().getLocation().getX();
    }


    public int getMouseY()
    {
        return (int) MouseInfo.getPointerInfo().getLocation().getY();
    }


    public double getAngleFromCenter()
    {
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        return Math.toDegrees(Math.atan2(mousePos.getY() - (height / 2.0), mousePos.getX() - (width / 2.0)));
    }

    private double map(double num, double in_min, double in_max, double out_min, double out_max)
    {
        return (num - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public double getMapRangeX()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.width;

        return map(mouseX, 0,width,-1,1);
    }

    public double getMapRangeY()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double height = screenSize.height;

        return map(mouseY,0,height,-1,1);
    }
}

