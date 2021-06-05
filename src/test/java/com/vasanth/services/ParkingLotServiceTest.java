package com.vasanth.services;

import com.vasanth.exceptions.ParkingLotException;
import com.vasanth.models.Car;
import com.vasanth.models.ParkingLot;
import com.vasanth.models.Slot;
import com.vasanth.models.parkingStrategy.NaturalOrderingParkingStrategy;
import com.vasanth.models.parkingStrategy.ParkingStrategy;
import com.vasanth.services.ParkingLotService;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ParkingLotServiceTest {
    private ParkingLot parkingLot;
    private ParkingStrategy parkingStrategy;
    private ParkingLotService parkingLotService = new ParkingLotService();

    @Before
    public void setUp() {
        parkingLot = mock(ParkingLot.class);
        parkingStrategy = mock(ParkingStrategy.class);
        parkingLotService.createParkingLot(parkingLot, parkingStrategy);
    }

    @Test(expected = ParkingLotException.class)
    public void testCreateParkingLotWhenAlreadyExist() {
        this.parkingLotService.createParkingLot(new ParkingLot(10), new NaturalOrderingParkingStrategy());
        this.parkingLotService.createParkingLot(new ParkingLot(11), new NaturalOrderingParkingStrategy());
    }

    @Test
    public void testSlotIsRemovedFromTheStrategyAfterParking() {
        final Car car = new Car("TS-12-34", "red");
        when(parkingStrategy.getNextSlot()).thenReturn(1);
        parkingLotService.park(car);
        verify(parkingStrategy).removeSlot(1);

    }

    @Test
    public void testParkingIsDoneInTheParkingLot() {
        final Car car = new Car("AP-12-34", "red");
        when(parkingStrategy.getNextSlot()).thenReturn(1);
        parkingLotService.park(car);
        verify(parkingLot).park(car, 1);
    }

    @Test(expected = ParkingLotException.class)
    public void testParkingCarWithOutCreatingParkingLot() {
        final ParkingLotService parkingLotService = new ParkingLotService();
        final Car car = new Car("AP-12-34", "red");
        parkingLotService.park(car);

    }

    @Test
    public void testFreeingSlot() {
        parkingLotService.makeSlotFree(1);
        verify(parkingLot).makeSlotFree(1);
        verify(parkingStrategy).addSlot(1);
    }

    @Test(expected = ParkingLotException.class)
    public void testFreeingTheSlotWithOutCreatingParkingLot() {
        final ParkingLotService parkingLotService = new ParkingLotService();
        parkingLotService.makeSlotFree(1);
    }

    @Test
    public void testOccupiedSlots() {
        final Map<Integer, Slot> allSlots = new HashMap<>();
        final Slot slot1 = new Slot(1);
        final Slot slot2 = new Slot(2);
        final Slot slot3 = new Slot(3);
        final Slot slot4 = new Slot(4);
        slot1.setParkedCar(new Car("test-car", "red"));
        slot4.setParkedCar(new Car("test-car-1", "purple"));

        allSlots.put(1, slot1);
        allSlots.put(2, slot2);
        allSlots.put(3, slot3);
        allSlots.put(4, slot4);

        when(parkingLot.getSlots()).thenReturn(allSlots);
        when(parkingLot.getCapacity()).thenReturn(10);

        final List<Slot> occupiedSlots = parkingLotService.getAllOccupiedSlots();
        assertEquals(2, occupiedSlots.size());
        assertEquals(slot1, occupiedSlots.get(0));
        assertEquals(slot4, occupiedSlots.get(1));

    }

    @Test
    public void testSlotsForParticularColour() {
        final Map<Integer, Slot> allSlots = new HashMap<>();
        final Slot slot1 = new Slot(1);
        slot1.setParkedCar(new Car("test-car-no1", "blue"));
        final Slot slot2 = new Slot(2);
        slot2.setParkedCar(new Car("test-car-no2", "white"));
        final Slot slot3 = new Slot(3);
        final Slot slot4 = new Slot(4);
        slot4.setParkedCar(new Car("test-car-no3", "white"));

        allSlots.put(1, slot1);
        allSlots.put(2, slot2);
        allSlots.put(3, slot3);
        allSlots.put(4, slot4);

        when(parkingLot.getSlots()).thenReturn(allSlots);
        when(parkingLot.getCapacity()).thenReturn(10);

        final List<Slot> slots = parkingLotService.getAllSlotsParkedWithParticularColour("white");
        assertEquals(2, slots.size());
        assertEquals(slot2, slots.get(0));
        assertEquals(slot4, slots.get(1));
    }

    @Test
    public void testSlotsWhenNoParticularColourMatches() {
        final Map<Integer, Slot> allSlots = new HashMap<>();
        final Slot slot1 = new Slot(1);
        slot1.setParkedCar(new Car("test-car-no1", "blue"));
        final Slot slot2 = new Slot(2);
        final Slot slot3 = new Slot(3);
        slot3.setParkedCar(new Car("test-car-no2", "red"));

        allSlots.put(1, slot1);
        allSlots.put(2, slot2);
        allSlots.put(3, slot3);

        when(parkingLot.getSlots()).thenReturn(allSlots);
        when(parkingLot.getCapacity()).thenReturn(10);

        final List<Slot> slots = parkingLotService.getAllSlotsParkedWithParticularColour("white");
        assertEquals(0, slots.size());
    }













}
