package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Command;
import com.vasanth.models.Slot;
import com.vasanth.services.ParkingLotService;

import java.util.List;
import java.util.Optional;

public class SlotForRegNumberCommandExecutor extends CommandExecutor{
    public static String COMMAND_NAME = "slot_number_for_registration_number";
    public SlotForRegNumberCommandExecutor(final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    @Override
    public boolean validate(Command command) {
        return command.getParams().size() == 1;
    }

    @Override
    public void execute(Command command) {
        final List<Slot> occupiedSlots = this.parkingLotService.getAllOccupiedSlots();
        final String regNumberToFind = command.getParams().get(0);
        final Optional<Slot> targetSlot = occupiedSlots.stream()
                .filter(slot -> slot.getParkedCar().getRegistrationNumber().equals(regNumberToFind))
                .findFirst();
        if(targetSlot.isPresent()) {
            outputPrinter.printWithNewLine(targetSlot.get().getSlotNumber().toString());
        } else {
            outputPrinter.notFound();
        }
    }
}
