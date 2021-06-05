package com.vasanth.models;

import com.vasanth.exceptions.InvalidSlotException;
import com.vasanth.exceptions.ParkingLotException;
import com.vasanth.exceptions.SlotAlreadyOccupiedException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
    Model related to parking lot
 */
public class ParkingLot {
    private static int MAX_CAPACITY = 10000;
    @Getter
    private int capacity;
    @Getter
    private Map<Integer, Slot> slots = new HashMap<>();

    public ParkingLot(final int capacity) {
        if(capacity <= 0 || capacity > MAX_CAPACITY) {
            throw new ParkingLotException("Invalid capacity given to parking lot");
        }
        this.capacity = capacity;
        // TODO: we can avoid lazy loading by initializing here
        this.slots = new HashMap<>();
    }

    /**
     * Helper method to get a {@link Slot} object for given slot number, if it does not exist, it will create
     * @param slotNumber
     * @return Slot
     */

    public Slot getSlot(int slotNumber) {
        if(slotNumber <= 0 || slotNumber > this.capacity) {
            throw new InvalidSlotException();
        }
        final Map<Integer, Slot> allSlots = getSlots();
        if(!allSlots.containsKey(slotNumber)) {
            allSlots.put(slotNumber, new Slot(slotNumber));
        }
        return allSlots.get(slotNumber);
    }

    /**
     * Parks the car into given slot number
     * @param car Car to be parked
     * @param slotNumber Slot Number in which it has to be parked
     * @return {@link Slot} if the parking succeeds otherwise {@link SlotAlreadyOccupiedException } is thrown
     */
    public Slot park(final Car car, final Integer slotNumber) {
        final Slot slot = getSlot(slotNumber);
        if(!slot.isSlotFree()) {
            throw new SlotAlreadyOccupiedException();
        }
        slot.setParkedCar(car);
        return slot;
    }

    /**
     * Makes the slot free, since the car has left
     * @param slotNUmber
     * @return Freed {@link Slot}
     */

    public Slot makeSlotFree(final Integer slotNUmber) {
        Slot slot = getSlot(slotNUmber);
        slot.unAssignParkedCar();
        return slot;
    }







}
