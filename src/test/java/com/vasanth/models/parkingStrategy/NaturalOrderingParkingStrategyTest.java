package com.vasanth.models.parkingStrategy;
import static org.junit.Assert.assertEquals;
import com.vasanth.exceptions.NoFreeSlotAvailableException;
import com.vasanth.models.parkingStrategy.NaturalOrderingParkingStrategy;
import org.junit.Test;

public class NaturalOrderingParkingStrategyTest {
    private NaturalOrderingParkingStrategy naturalOrderingParkingStrategy =
            new NaturalOrderingParkingStrategy();

    @Test(expected = NoFreeSlotAvailableException.class)
    public void testNoSlotInParkingStrategy() {
        Integer nextSlot = naturalOrderingParkingStrategy.getNextSlot();
    }

    @Test
    public void testValidParkingStrategy() {
        naturalOrderingParkingStrategy.addSlot(1);
        naturalOrderingParkingStrategy.addSlot(2);
        naturalOrderingParkingStrategy.addSlot(3);
        assertEquals((Integer)1, naturalOrderingParkingStrategy.getNextSlot());
        naturalOrderingParkingStrategy.removeSlot(1);
        assertEquals((Integer)2, naturalOrderingParkingStrategy.getNextSlot());
        naturalOrderingParkingStrategy.removeSlot(2);
        assertEquals((Integer)3, naturalOrderingParkingStrategy.getNextSlot());
        naturalOrderingParkingStrategy.removeSlot(3);
    }


}
