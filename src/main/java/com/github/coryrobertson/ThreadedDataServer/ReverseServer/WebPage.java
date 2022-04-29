package com.github.coryrobertson.ThreadedDataServer.ReverseServer;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsHtml;

public class WebPage implements Take
{

    @Override
    public Response act(Request req) throws Exception {
        return new RsHtml(ThreadedReverseServer.webText);
        // very simple read text from static variable and relay that to the web page
    }
}
