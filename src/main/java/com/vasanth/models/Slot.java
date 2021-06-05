package com.vasanth.models;

import lombok.Getter;
import lombok.Setter;

public class Slot {
    @Getter
    @Setter
    private Car  parkedCar;
    @Getter
    private Integer slotNumber;

    public Slot(final Integer slotNumber) {
        this.slotNumber = slotNumber;
    }

    public boolean isSlotFree() {
        return this.parkedCar == null;
    }

    public void unAssignParkedCar() {
        this.parkedCar = null;
    }



}
