package com.github.coryrobertson.ThreadedDataServer.ReverseServer;


import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;

import java.io.IOException;

/**
 * Hosts a webserver on a new thread, reads from a static variable for information, mainly useful for allowing the other end to do the reading instead of the client doing the sending
 */
public class ThreadedReverseServer implements Runnable
{

    public static String webText = "";

    @Override
    public void run()
    {
        //begin the webserver
        try {
            new FtBasic(
                    new TkFork(new FkRegex("/",new WebPage())), 8111 //port can basically be anything we need
            ).start(Exit.NEVER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
