package com.github.coryrobertson.ThreadedDataServer.Server;

import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;

import java.io.IOException;

public class ThreadedWebServer implements Runnable
{
    private static String webText = "";

    @Override
    public void run()
    {
        while(true) //TODO: allow this thread to die somehow, have not decided how yet though...
        {
            //TODO: change web text in a thread on its own maybe? would allow things to feel more instant
            ThreadedWebServer.webText = String.valueOf(System.currentTimeMillis());
        }
    }

    public static String getWebText()
    {
        return webText;
    }

    public static void main(String[] args)
    {
        Thread webTextThread = new Thread(new ThreadedWebServer());
        webTextThread.start();


        try {
            new FtBasic(
                    new TkFork(new FkRegex("/",new DataPage())), 8123 //port can basically be anything we need
            ).start(Exit.NEVER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
