package com.vasanth.commands;

import com.vasanth.Constants;
import com.vasanth.OutputPrinter;
import com.vasanth.models.Car;
import com.vasanth.models.Command;
import com.vasanth.models.Slot;
import com.vasanth.services.ParkingLotService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.mock;


public class StatusCommandExecutorTest {
    private ParkingLotService parkingLotService;
    private OutputPrinter outputPrinter;
    private StatusCommandExecutor statusCommandExecutor;

    @Before
    public void setUp() {
        parkingLotService = mock(ParkingLotService.class);
        outputPrinter = mock(OutputPrinter.class);
        statusCommandExecutor = new StatusCommandExecutor(parkingLotService, outputPrinter);
    }

    @Test
    public void testValidCommand() {
        statusCommandExecutor.validate(new Command(Constants.STATUS));
    }

    @Test
    public void testInvalidCommand() {
        statusCommandExecutor.validate(new Command(Constants.STATUS + " 1"));
        statusCommandExecutor.validate(new Command(Constants.STATUS + " 2 3"));
    }

    @Test
    public void testCommandExecutorWhenParkingLotIsEmpty() {
        List<Slot> occupiedSlots = new ArrayList<>();
        when(parkingLotService.getAllOccupiedSlots()).thenReturn(occupiedSlots);
        statusCommandExecutor.execute(new Command(Constants.STATUS));
        verify(outputPrinter).parkingLotEmpty();
    }

    @Test
    public void testCommandExecutionWhenCarsOccupiedInParkingLot() {
        final Slot slot1 = new Slot(1);
        slot1.setParkedCar(new Car("reg-1", "red"));

        final Slot slot2 = new Slot(2);
        slot2.setParkedCar(new Car("reg-2", "purple"));

        final Slot slot3 = new Slot(3);
        slot3.setParkedCar(new Car("reg-3", "green"));

        when(parkingLotService.getAllOccupiedSlots()).thenReturn(Arrays.asList(slot1, slot2, slot3));

        statusCommandExecutor.execute(new Command(Constants.STATUS));

        verify(parkingLotService).getAllOccupiedSlots();
        verify(outputPrinter).statusHeader();

        verify(outputPrinter).printWithNewLine(1 + "   "  +  "reg-1" + "  " + "red");
        verify(outputPrinter).printWithNewLine(2 + "   "  +  "reg-2" + "  " + "purple");
        verify(outputPrinter).printWithNewLine(3 + "   "  +  "reg-3" + "  " + "green");

    }

}
