package com.github.coryrobertson.ThreadedDataServer.Server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandHandlerTest {

    @Test
    void parseInput()
    {
        CommandHandler commandHandler = new CommandHandler();

        String[] arr = commandHandler.parseInput("servo 0 90", "python", "./main.py");
        String[] compare = {"python","./main.py","servo", "0", "90"};
        for(int i = 0; i < arr.length; i++)
        {
            Assertions.assertEquals(compare[i],arr[i]);
        }
    }
}