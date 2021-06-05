package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Command;
import com.vasanth.models.ParkingLot;
import com.vasanth.models.parkingStrategy.NaturalOrderingParkingStrategy;
import com.vasanth.services.ParkingLotService;
import com.vasanth.validator.IntegerValidator;

import java.util.List;

public class CreateParkingLotCommandExecutor extends CommandExecutor{

    public static String COMMAND_NAME = "create_parking_lot";

    public CreateParkingLotCommandExecutor(final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public boolean validate(Command command) {
        final List<String> params = command.getParams();
        if(params.size() != 1) {
            return false;
        }
        return IntegerValidator.isInteger(params.get(0));
    }

    @Override
    public void execute(Command command) {
        final Integer parkingLotCapacity = Integer.parseInt(command.getParams().get(0));
        final ParkingLot parkingLot = new ParkingLot(parkingLotCapacity);
        this.parkingLotService.createParkingLot(parkingLot, new NaturalOrderingParkingStrategy());
        this.outputPrinter.printWithNewLine("Parking Lot created with " + parkingLot.getCapacity() + " slots");
    }
}
