package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Car;
import com.vasanth.models.Command;
import com.vasanth.models.Slot;
import com.vasanth.services.ParkingLotService;
import org.junit.Before;
import org.junit.Test;
import  com.vasanth.Constants;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class RegNumbersWithColourCommandExecutorTest {
    private ParkingLotService parkingLotService;
    private OutputPrinter outputPrinter;
    private RegNumbersWithColourCommandExecutor regNumbersWithColourCommandExecutor;

    @Before
    public void setUp() {
        parkingLotService = mock(ParkingLotService.class);
        outputPrinter = mock(OutputPrinter.class);
        regNumbersWithColourCommandExecutor = new RegNumbersWithColourCommandExecutor(parkingLotService, outputPrinter);
    }

    @Test
    public void testValidCommand() {
        assertTrue(
        regNumbersWithColourCommandExecutor.validate(new Command(Constants.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR + " white")));
    }

    @Test
    public void testInvalidCommand() {
        assertFalse(
                regNumbersWithColourCommandExecutor.validate(new Command(Constants.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR))
        );

        assertFalse(
                regNumbersWithColourCommandExecutor.validate(new Command(Constants.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR + " a b"))
        );

    }

    @Test
    public void testWhenNoCarsFoundWithColour() {
        when(parkingLotService.getAllSlotsParkedWithParticularColour("purple")).thenReturn(Collections.emptyList());
        regNumbersWithColourCommandExecutor.execute(new Command(Constants.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR + " purple"));
        verify(outputPrinter).notFound();
    }

    @Test
    public void testCarsWithAColour() {
        final Slot slot1 = new Slot(1);
        slot1.setParkedCar(new Car("AP12345", "red"));
        final Slot slot2 = new Slot(2);
        slot2.setParkedCar(new Car("AP5678", "red"));

        when(parkingLotService.getAllSlotsParkedWithParticularColour("red")).thenReturn(Arrays.asList(slot1, slot2));

        regNumbersWithColourCommandExecutor.execute(new Command(Constants.REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR + " red"));
        verify(outputPrinter).printWithNewLine("AP12345, AP5678");



    }

}
