package com.github.coryrobertson.ThreadedDataServer;

import java.io.Serializable;

public record Message(int clientid, String message) implements Serializable
{
    public Message
    {
        if(clientid < 0)
        {
            throw new IllegalArgumentException("Message ID can not be lower than 0");
        }
    }
}
