package com.github.coryrobertson.ThreadedDataServer;

import java.util.ArrayList;

public class Messages
{
    private static ArrayList<Message> messages = new ArrayList<>();

    public static ArrayList<Message> getMessages()
    {
        return messages;
    }

    public static void addMessage(Message message)
    {
        System.out.println("message added to list with ID:" + message.clientid() + " and text: " + message.message());

        messages.add(message);
    }



}
