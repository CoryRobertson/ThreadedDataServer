package com.github.coryrobertson.ThreadedDataServer.ReverseServer;

import com.github.coryrobertson.ThreadedDataServer.Client.MousePosition;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

/**
 * A web host that hosts the entire data to be sent to the other device, in this case sending angle values to a rasperry pi
 */
public class WebHost implements NativeKeyListener
{

    public static boolean laserState = false;


    public static void main(String[] args)
    {

        System.out.println("hosting web page on port 8111...");
        Thread reverseServerThread = new Thread(new ThreadedReverseServer());

        globalHookKeyboard();

        reverseServerThread.start();

        //Scanner in = new Scanner(System.in); // maybe use this for manual setting?

        MousePosition mousePosition = new MousePosition();

        String webTextInput = "";


        while(true)
        {
            int servoXDegrees = (int) mousePosition.map(mousePosition.getMapRangeX(),-1,1,0,180); // here we are mapping the mouse position from a range of -1 to 1, to a range of 0 to 180
            int servoYDegrees = (int) mousePosition.map(mousePosition.getMapRangeY(),-1,1,0,180);
            webTextInput = servoXDegrees + "," + servoYDegrees; // we then append the angles to a string comma separated
            webTextInput += "," + laserState;

            ThreadedReverseServer.webText = webTextInput; // and we post it to the web page
        }

    }

    private static void globalHookKeyboard()
    {
        try { // setting up our keyboard listener
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(new WebHost());
    }

    public void nativeKeyPressed(NativeKeyEvent e)
    {
        if (e.getKeyCode() == NativeKeyEvent.VC_F1)
        {
            laserState = !laserState;
            System.out.println("toggled laser");
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_F3)
        {
            //pauseEffects = !pauseEffects;
        }
    }

}
