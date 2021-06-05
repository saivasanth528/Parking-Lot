package com.vasanth;

import com.vasanth.commands.CommandExecutorFactory;
import com.vasanth.exceptions.InvalidModeException;
import com.vasanth.modes.FileMode;
import com.vasanth.modes.InteractiveMode;
import com.vasanth.services.ParkingLotService;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        final OutputPrinter outputPrinter = new OutputPrinter();
        final ParkingLotService parkingLotService = new ParkingLotService();
        final CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory(parkingLotService);
        if(isInteractiveMode(args)) {
            new InteractiveMode(commandExecutorFactory, outputPrinter).process();
        } else if(isFileInputMode(args)) {
            new FileMode(commandExecutorFactory, outputPrinter, args[0]).process();
        } else {
            throw new InvalidModeException();
        }
    }

    /**
     * Checks whether the program is running in file input mode or not
     * @param args Command line arguments
     * @return Boolean indicating whether is is file input mode or not
     */
    private static boolean isFileInputMode(final String[] args) {
        return args.length == 1;
    }

    /**
     * Checks whether the program is running in interactive shell mode
     * @param args
     * @return
     */
    private static boolean isInteractiveMode(final String[] args) {
        return args.length == 0;
    }
}
