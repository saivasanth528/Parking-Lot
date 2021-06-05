package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Command;
import com.vasanth.services.ParkingLotService;

public class ExitCommandExecutor extends CommandExecutor{
    public static String COMMAND_NAME = "exit";
    public ExitCommandExecutor(final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public boolean validate(Command command) {
        return command.getParams().isEmpty();
    }

    @Override
    public void execute(Command command) {
        this.outputPrinter.end();
//        System.exit(0); commented for unit testing process
    }
}
