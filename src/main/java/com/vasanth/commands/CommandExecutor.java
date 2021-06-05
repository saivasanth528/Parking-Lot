package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Command;
import com.vasanth.services.ParkingLotService;

public abstract class CommandExecutor {

    protected ParkingLotService parkingLotService;
    protected OutputPrinter outputPrinter;

    public CommandExecutor(final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
        this.parkingLotService = parkingLotService;
        this.outputPrinter = outputPrinter;

    }


    /**
     * Validates whether the command is valid or not
     * @param command command name to be validated
     * @return Boolean indicating whether the command is valid or not
     */
    public abstract boolean validate(Command command);


    /**
     * Executes the command
     * @param command Command to be executed
     */
    public abstract void execute(Command command);
}
