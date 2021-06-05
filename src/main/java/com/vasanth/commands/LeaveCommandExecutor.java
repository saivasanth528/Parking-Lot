package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Command;
import com.vasanth.services.ParkingLotService;
import com.vasanth.validator.IntegerValidator;

import java.util.List;

public class LeaveCommandExecutor extends CommandExecutor{
    public static  String COMMAND_NAME = "leave";
    public LeaveCommandExecutor(final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public boolean validate(Command command) {
        final List<String> params = command.getParams();
        if (params.size() !=1) {
            return false;
        }
        return IntegerValidator.isInteger(params.get(0));
    }

    @Override
    public void execute(Command command) {
        final Integer slotNumber = Integer.parseInt(command.getParams().get(0));
        this.parkingLotService.makeSlotFree(slotNumber);
        this.outputPrinter.printWithNewLine("Slot Number " + slotNumber + " is free");
    }
}
