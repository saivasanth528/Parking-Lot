package com.vasanth.models;

import com.vasanth.exceptions.InvalidCommandException;
import com.vasanth.models.Command;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommandTest {

    @Test(expected = InvalidCommandException.class)
    public void testCommandParsingFromInputHavingOnlySpaces() {
        Command command = new Command("    ");
    }

    @Test(expected = InvalidCommandException.class)
    public void testCommandParsingFromInputHavingNoSpaces() {
        Command command = new Command("");
    }

    @Test
    public void testCommandParsingFromInput() {
        validateCommandParsing("my_command 1 2 3", "my_command", Arrays.asList("1", "2", "3"));
        validateCommandParsing("my_command 1 2", "my_command", Arrays.asList("1", "2"));
        validateCommandParsing("my_command      ", "my_command", Collections.emptyList());
        validateCommandParsing("my_command", "my_command", Collections.emptyList());

    }

    /**
     * Helper method to validate the command
     *
     * @param input input line
     * @param expectedCommandName Expected Command from the input
     * @param expectedParams Expected params from the command input
     */

    public void validateCommandParsing(final String input, final String expectedCommandName,
                                       final List expectedParams) {
        Command  command = new Command(input);
        assertNotNull(command);
        assertEquals(expectedCommandName, command.getCommandName());
        assertEquals(expectedParams, command.getParams());

    }


}
