package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Car;
import com.vasanth.models.Command;
import com.vasanth.models.Slot;
import com.vasanth.services.ParkingLotService;

import java.util.List;

public class StatusCommandExecutor extends CommandExecutor{
    public static String COMMAND_NAME = "status";
    public StatusCommandExecutor(final ParkingLotService parkingLotService,final OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public boolean validate(Command command) {

        return command.getParams().isEmpty();
    }

    @Override
    public void execute(Command command) {
        final List<Slot> occupiedSlots = this.parkingLotService.getAllOccupiedSlots();
        if(occupiedSlots.isEmpty()) {
            outputPrinter.parkingLotEmpty();
            return;
        }
        this.outputPrinter.statusHeader();
        for(Slot slot: occupiedSlots) {
            final Car parkedCar = slot.getParkedCar();
            final Integer slotNumber = slot.getSlotNumber();
            this.outputPrinter.printWithNewLine(slotNumber + "   "  +  parkedCar.getRegistrationNumber() + "   " + parkedCar.getColour());

        }
    }
}
