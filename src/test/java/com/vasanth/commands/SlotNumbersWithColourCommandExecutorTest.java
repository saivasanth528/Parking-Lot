package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Car;
import com.vasanth.models.Command;
import com.vasanth.models.Slot;
import com.vasanth.services.ParkingLotService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.vasanth.Constants;

import java.util.Arrays;
import java.util.Collections;

public class SlotNumbersWithColourCommandExecutorTest {
    private ParkingLotService parkingLotService;
    private OutputPrinter outputPrinter;
    private SlotNumbersWithColourCommandExecutor slotNumbersWithColourCommandExecutor;

    @Before
    public void setUp() {
        parkingLotService = mock(ParkingLotService.class);
        outputPrinter = mock(OutputPrinter.class);
        slotNumbersWithColourCommandExecutor = new SlotNumbersWithColourCommandExecutor(parkingLotService, outputPrinter);
    }

    @Test
    public void testValidCommand() {
        assertTrue(slotNumbersWithColourCommandExecutor.validate(new Command(Constants.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR + " white")));
    }

    @Test
    public void testInvalidCommand() {
        assertFalse(
                slotNumbersWithColourCommandExecutor.validate(new Command(Constants.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR))
        );

        assertFalse(
                slotNumbersWithColourCommandExecutor.validate(new Command(Constants.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR + " a b"))
        );
    }

    @Test
    public void testWhenNoSlotsFoundWithColour() {
        when(parkingLotService.getAllSlotsParkedWithParticularColour("red")).thenReturn(Collections.emptyList());
        slotNumbersWithColourCommandExecutor.execute(new Command(Constants.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR + " red"));
        verify(outputPrinter).notFound();
    }

    @Test
    public void testCarsWithAColour() {
        final Slot slot1  = new Slot(1);
        slot1.setParkedCar(new Car("AP-12-34", "purple"));
        final Slot slot2 = new Slot(2);
        slot2.setParkedCar(new Car("TS-12-34", "purple"));
        when(parkingLotService.getAllSlotsParkedWithParticularColour("purple")).thenReturn(Arrays.asList(slot1, slot2));
        slotNumbersWithColourCommandExecutor.execute(new Command(Constants.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR + " purple"));
        verify(outputPrinter).printWithNewLine("1, 2");
    }


}
