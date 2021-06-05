package com.vasanth.models;

import com.vasanth.exceptions.InvalidSlotException;
import com.vasanth.exceptions.ParkingLotException;
import com.vasanth.exceptions.SlotAlreadyOccupiedException;
import com.vasanth.models.Car;
import com.vasanth.models.ParkingLot;
import com.vasanth.models.Slot;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParkingLotTest {

    @Test(expected = ParkingLotException.class)
    public void testNegativeCapacity() {
        new ParkingLot(-1);
    }

    @Test(expected = ParkingLotException.class)
    public void testZeroCapacity() {
        new ParkingLot(0);
    }

    @Test(expected = ParkingLotException.class)
    public void testMaxCapacity() {
        new ParkingLot(1000001);
    }

    @Test
    public void validCapacity() {
        new ParkingLot(100);
    }

    @Test
    public void testParkingCar() {
        Car car = new Car("AP-123-445", "purple");
        ParkingLot parkingLot = new ParkingLot(100);
       final Slot slot = parkingLot.park(car, 1);
       assertEquals(slot.getParkedCar(), car);
    }

    @Test(expected = SlotAlreadyOccupiedException.class)
    public void testParkingOnAlreadyOccupiedSlot() {
        final Car car1 = new Car("AP-1234", "purple");
        final Car car2 = new Car("TS-12-34", "white");
        ParkingLot parkingLot = new ParkingLot(10);
        parkingLot.park(car1, 1);
        parkingLot.park(car2, 1);

    }

    @Test(expected = InvalidSlotException.class)
    public void testParkingTheCarAtHigherCapacity() {
        ParkingLot parkingLot = new ParkingLot(10);
        final Car car = new Car("AP-123-3", "red");
        parkingLot.park(car, 11);
    }

    @Test(expected = InvalidSlotException.class)
    public void testParkingAtInvalidSlot() {
        ParkingLot parkingLot = new ParkingLot(100);
        final Car car = new Car("AP-1234", "purple");
        parkingLot.park(car, 0);
    }

    @Test
    public void testMakeSlotFree() {
        ParkingLot parkingLot = new ParkingLot(100);
        final  Car car = new Car("AP-12-32", "violet");
        final Slot parkedSlot = parkingLot.park(car, 1);
        assertFalse(parkedSlot.isSlotFree());
        parkingLot.makeSlotFree(1);
        assertTrue(parkedSlot.isSlotFree());
    }


    @Test(expected = InvalidSlotException.class)
    public void testMakeSlotFreeHigherThanCapacity() {
        ParkingLot parkingLot = new ParkingLot(100);
        parkingLot.makeSlotFree(101);
    }

    @Test(expected = InvalidSlotException.class)
    public void testInvalidSlotFree() {
        ParkingLot parkingLot = new ParkingLot(100);
        parkingLot.makeSlotFree(-1);
    }




}
