package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Command;
import com.vasanth.models.ParkingLot;
import com.vasanth.models.parkingStrategy.NaturalOrderingParkingStrategy;
import com.vasanth.services.ParkingLotService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;

public class CreateParkingLotCommandExecutorTest {
    private ParkingLotService parkingLotService;
    private OutputPrinter outputPrinter;
    private CreateParkingLotCommandExecutor createParkingLotCommandExecutor;

    @Before
    public void setUp() throws Exception {
        parkingLotService = mock(ParkingLotService.class);
        outputPrinter = mock(OutputPrinter.class);
        createParkingLotCommandExecutor = new
                CreateParkingLotCommandExecutor(parkingLotService, outputPrinter);

    }

    @Test
    public void testValidCommand() {
        assertTrue(createParkingLotCommandExecutor.validate(new Command("create_parking_lot 6")));
    }

    @Test
    public void testInvalidCommand() {
        assertFalse((createParkingLotCommandExecutor.validate(new Command("create_parking_lot"))));
        assertFalse(createParkingLotCommandExecutor.validate(new Command("create_parking_lot bcd")));
    }

    @Test
    public void testCommandExecution() {
        createParkingLotCommandExecutor.execute(new Command("create_parking_lot 6"));
        final ArgumentCaptor<ParkingLot>  argument = ArgumentCaptor.forClass(ParkingLot.class);
        verify(parkingLotService).createParkingLot(argument.capture(), any(NaturalOrderingParkingStrategy.class));
        assertEquals(6, argument.getValue().getCapacity());
        verify(outputPrinter).printWithNewLine("Parking Lot created with " + 6 + " slots");

    }



}
