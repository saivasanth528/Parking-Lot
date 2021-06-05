package com.vasanth.models.parkingStrategy;

public interface ParkingStrategy {


    /**
     *  Add a new slot to the parking strategy , after adding it will be available for future parking
     * @param slotNumber  Slot Number to be added
     */
    public void addSlot(Integer slotNumber);

    /**
     * Removes this slot from the parking strategy
     * @param slotNumber Slot Number to be removed
     */
    public void removeSlot(Integer slotNumber);


    /**
     * Gives the next available slot
     * @return Next free slot number
     */
    public Integer getNextSlot();

}
