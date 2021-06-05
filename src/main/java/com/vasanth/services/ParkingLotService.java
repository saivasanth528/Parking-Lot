package com.vasanth.services;
import com.vasanth.exceptions.ParkingLotException;
import com.vasanth.models.Car;
import com.vasanth.models.ParkingLot;
import com.vasanth.models.Slot;
import com.vasanth.models.parkingStrategy.ParkingStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * service for parking lot, this is the crux where actual business logic happens
 */
public class ParkingLotService {

    private ParkingLot parkingLot;
    private ParkingStrategy parkingStrategy;


    /**
     * It will assign the parking lot with specific strategy, If there is already parking lot in the service
     * it throws {@link ParkingLotException}
     * @param parkingLot
     * @param parkingStrategy
     */
    public void createParkingLot(final ParkingLot parkingLot, final ParkingStrategy parkingStrategy) {
        if(this.parkingLot != null) {
            throw new ParkingLotException("Parking Lot already exists");
        }

        this.parkingLot = parkingLot;
        this.parkingStrategy = parkingStrategy;

        for(int i = 1; i <= parkingLot.getCapacity(); i++) {
            parkingStrategy.addSlot(i);
        }
    }

    /**
     * Parks the {@link Car} in the available slot in the {@link ParkingLot} , to which slot the car should park is decided by
     * the {@link ParkingStrategy}, it gives the available free slot depends on problem statement
     *
     * @param car
     * @return the slot number which it is assigned
     *
     */

    public Integer park(final Car car) {
        validateParkingLotExists();
        final Integer availableFreeSlot = parkingStrategy.getNextSlot();
        parkingLot.park(car, availableFreeSlot);
        parkingStrategy.removeSlot(availableFreeSlot);
        return availableFreeSlot;
    }

    /**
     * Un parks the car from that particular slot number and that slot number is given back to the {@link ParkingStrategy}, such that it will be
     * available for the next cars.
     * @param slotNumber the slot which we have to free
     */

    public void makeSlotFree(Integer slotNumber) {
        validateParkingLotExists();
        this.parkingLot.makeSlotFree(slotNumber);
        this.parkingStrategy.addSlot(slotNumber);
    }


    /**
     * Get the all available occupied slots
     * @return
     */
    public List<Slot> getAllOccupiedSlots() {
        validateParkingLotExists();
        final Map<Integer, Slot> allSlotsInParkingLot = this.parkingLot.getSlots();
        final List<Slot> occupiedSlotsList = new ArrayList<>();

        for(int i = 1; i <= this.parkingLot.getCapacity(); i++) {
            if(allSlotsInParkingLot.containsKey(i)) {
                final Slot slot = allSlotsInParkingLot.get(i);
                if(!slot.isSlotFree()) {
                    occupiedSlotsList.add(slot);
                }
            }
        }
        return occupiedSlotsList;
    }


    /**
     * Helper method to validate the all available parking slots
     */
    private void validateParkingLotExists() {
        if(this.parkingLot == null) {
            throw new ParkingLotException("Parking Lot does not exist to park");
        }

    }

    /**
     * Gets all the slots where particular colour is parked
     * @param colour Colour to be searched
     * @return All matching slots
     */

    public List<Slot> getAllSlotsParkedWithParticularColour(final String colour) {
        final List<Slot> occupiedSlots = getAllOccupiedSlots();
        return  occupiedSlots.stream()
                .filter(slot -> slot.getParkedCar().getColour().equals(colour))
                .collect(Collectors.toList());
    }


}
