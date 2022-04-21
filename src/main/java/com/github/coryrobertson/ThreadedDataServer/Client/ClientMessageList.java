package com.github.coryrobertson.ThreadedDataServer.Client;

import com.github.coryrobertson.ThreadedDataServer.Client.ThreadedDataServerClient;
import com.github.coryrobertson.ThreadedDataServer.Message;

public class ClientMessageList extends Thread
{
    //we extend thread here instead of implementing runnable so we can reference the functions within this class from other scopes.
    int lastKnownSize = -1;

    private boolean running;

    public ClientMessageList() {}

    @Override
    public void run()
    {
        running = true;
        while(running)
        {


            if (ThreadedDataServerClient.messages.size() > lastKnownSize)
            {
                lastKnownSize = ThreadedDataServerClient.messages.size();

                System.out.println("-----------==1==----------");

                for (Message msg : ThreadedDataServerClient.messages)
                {
                    System.out.println(msg);
                }

                System.out.println("-----------==2==----------");
            }
        }
    }

    public void stopThread()
    {
        running = false;
    }
}
