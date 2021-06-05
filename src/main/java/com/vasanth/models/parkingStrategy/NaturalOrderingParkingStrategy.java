package com.vasanth.models.parkingStrategy;

import com.vasanth.exceptions.NoFreeSlotAvailableException;

import java.util.TreeSet;

public class NaturalOrderingParkingStrategy implements ParkingStrategy{
    TreeSet<Integer> slotTreeSet;

    /**
     * As per the problem statement, the car should be given the slot nearer to the entry gate, so we are following natural ordering
     * When we assign the slot, we should keep track of which slots we assigned right, that is the reason we are maintaining this class
     */
    public NaturalOrderingParkingStrategy() {
        this.slotTreeSet = new TreeSet<>();
    }

    @Override
    public void addSlot(Integer slotNumber) {
        this.slotTreeSet.add(slotNumber);
    }

    @Override
    public void removeSlot(Integer slotNumber) {
        this.slotTreeSet.remove(slotNumber);
    }

    @Override
    public Integer getNextSlot() {
        if(this.slotTreeSet.isEmpty()) {
            throw new NoFreeSlotAvailableException();
        }
        return this.slotTreeSet.first();
    }
}
