package com.vasanth.modes;

import com.vasanth.OutputPrinter;
import com.vasanth.commands.CommandExecutor;
import com.vasanth.commands.CommandExecutorFactory;
import com.vasanth.exceptions.InvalidCommandException;
import com.vasanth.models.Command;

import java.io.IOException;

public abstract class Mode {
    private CommandExecutorFactory commandExecutorFactory;
    protected OutputPrinter outputPrinter;

    public Mode(final CommandExecutorFactory commandExecutorFactory, final OutputPrinter outputPrinter) {
        this.commandExecutorFactory = commandExecutorFactory;
        this.outputPrinter = outputPrinter;
    }


    /**
     * Helper method to process the command, It uses {@link CommandExecutorFactory} to process the command
     * @param command
     */
    protected void processCommand(Command command) {
        final CommandExecutor commandExecutor = this.commandExecutorFactory.getCommandExecutor(command);
        if(commandExecutor.validate(command)) {
            commandExecutor.execute(command);
        } else {
            throw new InvalidCommandException();
        }
    }

    /**
     * Abstract method, each new mode has to invoke this method
     * @throws IOException
     */

    public abstract void process() throws IOException;




}
