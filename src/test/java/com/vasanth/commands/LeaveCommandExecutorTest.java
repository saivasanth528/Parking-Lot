package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Command;
import com.vasanth.services.ParkingLotService;
import org.junit.Before;
import org.junit.Test;
import static com.vasanth.Constants.LEAVE;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LeaveCommandExecutorTest {
    private ParkingLotService parkingLotService;
    private OutputPrinter outputPrinter;
    private LeaveCommandExecutor leaveCommandExecutor;

    @Before
    public void setup() {
        parkingLotService = mock(ParkingLotService.class);
        outputPrinter = mock(OutputPrinter.class);
        leaveCommandExecutor = new LeaveCommandExecutor(parkingLotService, outputPrinter);
    }

    @Test
    public void testValidCommand() {
        assertTrue(leaveCommandExecutor.validate(new Command(LEAVE + " 1")));
    }

    @Test
    public void testInvalidCommand() {
        assertFalse(leaveCommandExecutor.validate(new Command(LEAVE)));
        assertFalse(leaveCommandExecutor.validate(new Command("leave 1 2")));
        assertFalse(leaveCommandExecutor.validate(new Command("leave 1 a")));
        assertFalse(leaveCommandExecutor.validate(new Command("leave abcd")));
    }

    @Test
    public void testLeaveCommand() {
        leaveCommandExecutor.execute(new Command(LEAVE +  " 1"));
        verify(parkingLotService).makeSlotFree(1);
        verify(outputPrinter).printWithNewLine("Slot Number " + 1 + " is free");
    }
}
