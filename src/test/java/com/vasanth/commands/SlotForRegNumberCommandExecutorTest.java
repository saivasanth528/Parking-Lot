package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Car;
import com.vasanth.models.Command;
import com.vasanth.models.Slot;
import com.vasanth.services.ParkingLotService;
import org.junit.Before;
import org.junit.Test;
import com.vasanth.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SlotForRegNumberCommandExecutorTest {
    private ParkingLotService parkingLotService;
    private OutputPrinter outputPrinter;
    private SlotForRegNumberCommandExecutor slotForRegNumberCommandExecutor;

    @Before
    public void setUp() {
        parkingLotService = mock(ParkingLotService.class);
        outputPrinter = mock(OutputPrinter.class);
        slotForRegNumberCommandExecutor = new SlotForRegNumberCommandExecutor(parkingLotService, outputPrinter);
    }

    @Test
    public void testValidCommand() {
       assertTrue(slotForRegNumberCommandExecutor.validate(new Command(Constants.SLOT_NUMBER_FOR_REGISTRATION_NUMBER + " AP-12-34")));
    }

    @Test
    public void testInvalidCommand() {
        assertFalse(slotForRegNumberCommandExecutor.validate(new Command(Constants.SLOT_NUMBER_FOR_REGISTRATION_NUMBER)));
        assertFalse(slotForRegNumberCommandExecutor.validate(new Command(Constants.SLOT_NUMBER_FOR_REGISTRATION_NUMBER + " AP-12-23 345")));
    }

    @Test
    public void testCorrectSlotNumberForValidRegistrationNumber() {
        final Slot slot1 = new Slot(1);
        slot1.setParkedCar(new Car("AP-1234", "white"));

        final Slot slot2 = new Slot(2);
        slot2.setParkedCar(new Car("AB-01-CP-1230", "blue"));

        final Slot slot3 = new Slot(3);
        slot3.setParkedCar(new Car("TS-12-345", "blue"));

        when(parkingLotService.getAllOccupiedSlots()).thenReturn(Arrays.asList(slot1, slot2, slot3));

        slotForRegNumberCommandExecutor.execute(new Command(Constants.SLOT_NUMBER_FOR_REGISTRATION_NUMBER + " TS-12-345"));

        verify(outputPrinter).printWithNewLine("3");
    }

    @Test
    public void testCorrectSlotNumberForNotExistingRegistrationNumber() {
        List<Slot> occupiedSlots = new ArrayList<>();
        when(parkingLotService.getAllOccupiedSlots()).thenReturn(occupiedSlots);
        slotForRegNumberCommandExecutor.execute(
                new Command(Constants.SLOT_NUMBER_FOR_REGISTRATION_NUMBER + " TS-12-34")
        );
        verify(outputPrinter).printWithNewLine("Not found");
    }


}
