package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Command;
import com.vasanth.models.Slot;
import com.vasanth.services.ParkingLotService;

import java.util.List;
import java.util.stream.Collectors;

public class RegNumbersWithColourCommandExecutor extends CommandExecutor{
    public static String COMMAND_NAME = "registration_numbers_for_cars_with_colour";
    public RegNumbersWithColourCommandExecutor(final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public boolean validate(Command command) {
        return command.getParams().size() == 1;
    }

    @Override
    public void execute(Command command) {
        final List<Slot> slotsForColour = parkingLotService.getAllSlotsParkedWithParticularColour(command.getParams().get(0));
        if(slotsForColour.isEmpty()) {
            outputPrinter.notFound();
            return;
        }
        final String result = slotsForColour.stream().map(slot -> slot.getParkedCar().getRegistrationNumber())
                .collect(Collectors.joining(", "));
        this.outputPrinter.printWithNewLine(result);


    }
}
