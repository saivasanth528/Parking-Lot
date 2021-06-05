package com.vasanth.commands;

import com.vasanth.OutputPrinter;
import com.vasanth.models.Command;
import com.vasanth.services.ParkingLotService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static com.vasanth.Constants.EXIT;

public class ExitCommandExecutorTest {
    private ParkingLotService parkingLotService;
    private OutputPrinter outputPrinter;
    private ExitCommandExecutor exitCommandExecutor;

    @Before
    public void setUp() throws Exception {
        parkingLotService = mock(ParkingLotService.class);
        outputPrinter = mock(OutputPrinter.class);
        exitCommandExecutor = new ExitCommandExecutor(parkingLotService, outputPrinter);

    }

    @Test
    public void testExitValidCommand() {
        assertTrue(exitCommandExecutor.validate(new Command(EXIT)));
    }

    @Test
    public void testExitInvalidCommand() {
        assertFalse(exitCommandExecutor.validate(new Command("exit 1")));
        assertFalse(exitCommandExecutor.validate(new Command("exit 2 3")));
        assertFalse(exitCommandExecutor.validate(new Command("exit s")));
    }

    @Test
    public void testExitCommandExecution() {
        exitCommandExecutor.execute(new Command(EXIT));
        verify(outputPrinter).end();
    }
}
