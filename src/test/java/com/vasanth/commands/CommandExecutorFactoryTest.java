package com.vasanth.commands;

import com.vasanth.exceptions.InvalidCommandException;
import com.vasanth.models.Command;
import com.vasanth.services.ParkingLotService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;

public class CommandExecutorFactoryTest {
    private CommandExecutorFactory factory;

    @Before
    public void setUp() throws Exception {
        final ParkingLotService parkingLotService = mock(ParkingLotService.class);
        factory = new CommandExecutorFactory(parkingLotService);
    }

    @Test
    public void testCommandExecutorForValidCommand() {
        final CommandExecutor commandExecutor = factory.getCommandExecutor(new Command("leave 5"));
        assertNotNull(commandExecutor);
        assertTrue(commandExecutor instanceof LeaveCommandExecutor);

    }

    @Test(expected = InvalidCommandException.class)
    public void testInvalidCommand() {
        factory.getCommandExecutor(new Command("sdveve vhe ehvek"));
    }



}
