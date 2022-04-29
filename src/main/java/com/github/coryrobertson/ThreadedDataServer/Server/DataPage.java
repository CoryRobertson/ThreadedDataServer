package com.github.coryrobertson.ThreadedDataServer.Server;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsHtml;

/**
 * Another unused web page class, not intended to be used at the moment
 */
public class DataPage implements Take
{


    @Override
    public Response act(Request req)
    {
        return new RsHtml(ThreadedWebServer.getWebText()); //this text is gotten from a static variable from threaded web server
    }
}
