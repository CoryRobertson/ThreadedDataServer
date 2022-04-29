package com.github.coryrobertson.ThreadedDataServer.Server;

import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Mostly unused class, might use eventually but not at the moment
 */
public class ThreadedWebServer implements Runnable
{
    private static String webText = "";

    @Override
    public void run()
    {
        while(true) //TODO: allow this thread to die somehow, have not decided how yet though...
        {
            //TODO: change web text in a thread on its own maybe? would allow things to feel more instant
            //ThreadedWebServer.webText = String.valueOf(System.currentTimeMillis());
            try {
                new FtBasic(
                        new TkFork(new FkRegex("/",new DataPage())), 8123 //port can basically be anything we need
                ).start(Exit.NEVER);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void setWebText(String input)
    {
        webText = input;
    }

    public static String getWebText()
    {
        return webText;
    }

}
