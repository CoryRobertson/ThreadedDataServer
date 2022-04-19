package com.github.coryrobertson.ThreadedDataServer;

import java.io.Serializable;

public record Message(int id, String message) implements Serializable
{
    public Message
    {
        if(id < 0)
        {
            throw new IllegalArgumentException("Message ID can not be lower than 0");
        }
    }
}
