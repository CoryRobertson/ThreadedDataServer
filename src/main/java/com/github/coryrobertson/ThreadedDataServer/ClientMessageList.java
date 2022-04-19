package com.github.coryrobertson.ThreadedDataServer;

public class ClientMessageList implements Runnable
{
    int lastKnownSize = -1;

    @Override
    public void run()
    {
        while(true)
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
}
