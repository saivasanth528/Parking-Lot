package com.vasanth.modes;

import com.vasanth.OutputPrinter;
import com.vasanth.commands.CommandExecutorFactory;
import com.vasanth.models.Command;

import java.io.*;

public class FileMode extends Mode{
    private String fileName;
    public FileMode(CommandExecutorFactory commandExecutorFactory, OutputPrinter outputPrinter, final String fileName) {
        super(commandExecutorFactory, outputPrinter);
        this.fileName = fileName;
    }

    @Override
    public void process() throws IOException {
        final File file = new File(fileName);
        final BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException fe) {
            outputPrinter.invalidFile();
            return;
        }

        String input = reader.readLine();
        while(input != null) {
            final Command command = new Command(input);
            processCommand(command);
            input = reader.readLine();
        }
    }
}
