package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.exceptions.NoFreeSlotAvailableException;
import com.vasanth.models.Car;
import com.vasanth.models.Command;
import com.vasanth.services.ParkingLotService;

public class ParkCommandExecutor extends  CommandExecutor{

    public static  String COMMAND_NAME = "park";
    public ParkCommandExecutor(final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public boolean validate(Command command) {

        return command.getParams().size() == 2;
    }

    @Override
    public void execute(Command command) {
        final Car car = new Car(command.getParams().get(0), command.getParams().get(1));
        try {
           final Integer slotNumber =  this.parkingLotService.park(car);
           this.outputPrinter.printWithNewLine("Allocated slot number: " + slotNumber);
        } catch (NoFreeSlotAvailableException exception) {
            this.outputPrinter.parkingLotFUll();
        }
    }
}
