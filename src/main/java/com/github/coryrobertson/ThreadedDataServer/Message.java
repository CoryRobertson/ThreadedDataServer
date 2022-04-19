package com.github.coryrobertson.ThreadedDataServer;

public record Message(int id, String message)
{
    public Message
    {
        if(id < 0)
        {
            throw new IllegalArgumentException("Message ID can not be lower than 0");
        }
    }
}
