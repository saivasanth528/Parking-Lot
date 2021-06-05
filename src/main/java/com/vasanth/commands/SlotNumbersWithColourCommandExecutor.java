package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Command;
import com.vasanth.models.Slot;
import com.vasanth.services.ParkingLotService;

import java.util.List;
import java.util.stream.Collectors;

public class SlotNumbersWithColourCommandExecutor extends CommandExecutor{
    public static String COMMAND_NAME = "slot_numbers_for_cars_with_colour";
    public SlotNumbersWithColourCommandExecutor(final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public boolean validate(Command command) {
        return command.getParams().size() == 1;
    }

    @Override
    public void execute(Command command) {
        final List<Slot> slotsWithColour = this.parkingLotService.getAllSlotsParkedWithParticularColour(command.getParams().get(0));
        if(slotsWithColour.isEmpty()) {
            this.outputPrinter.notFound();
        }
        final String slots = slotsWithColour.stream().map(slot -> slot.getSlotNumber().toString())
                .collect(Collectors.joining(", "));
        outputPrinter.printWithNewLine(slots);

    }
}
