package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.exceptions.NoFreeSlotAvailableException;
import com.vasanth.models.Car;
import com.vasanth.models.Command;
import com.vasanth.services.ParkingLotService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;

public class ParkCommandExecutorTest {
    private ParkingLotService parkingLotService;
    private OutputPrinter outputPrinter;
    private ParkCommandExecutor parkCommandExecutor;

    @Before
    public void setUp() {
        parkingLotService = mock(ParkingLotService.class);
        outputPrinter = mock(OutputPrinter.class);
        parkCommandExecutor = new ParkCommandExecutor(parkingLotService, outputPrinter);
    }

    @Test
    public void testValidCommand() {
        assertTrue(parkCommandExecutor.validate(new Command("park test-command white")));
    }


    @Test
    public void testInvalidCommand() {
        assertFalse(parkCommandExecutor.validate(new Command("park")));
        assertFalse(parkCommandExecutor.validate(new Command("park test-car-number")));
        assertFalse(parkCommandExecutor.validate(new Command("park test-car-number white abcd")));
    }

    @Test
    public void testCommandExecutionWhenParkingSucceeds() {
        when(parkingLotService.park(any())).thenReturn(1);
        parkCommandExecutor.execute(new Command("park test-car-number purple"));
        final ArgumentCaptor<Car> argument = ArgumentCaptor.forClass(Car.class);
        verify(parkingLotService).park(argument.capture());
        assertEquals("test-car-number", argument.getValue().getRegistrationNumber());
        assertEquals("purple", argument.getValue().getColour());
        verify(outputPrinter).printWithNewLine("Allocated slot number " + 1);
    }

    @Test
    public void testCommandExecutionWhenParkingFails() {
        when(parkingLotService.park(any())).thenThrow(new NoFreeSlotAvailableException());
        parkCommandExecutor.execute(new Command("park test-car-number red"));
        final ArgumentCaptor<Car> argument = ArgumentCaptor.forClass(Car.class);
        verify(parkingLotService).park(argument.capture());
        assertEquals("test-car-number", argument.getValue().getRegistrationNumber());
        assertEquals("red", argument.getValue().getColour());
        verify(outputPrinter).parkingLotFUll();
    }
}
