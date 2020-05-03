package com.gojek.parkinglotassignment.parkinglot;

import com.gojek.parkinglotassignment.exceptions.ParkingLotException;
import com.gojek.parkinglotassignment.vehicle.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingLot {
    private Map<Integer, ParkingSlot> allParkingSlots = new HashMap<>();
    private PriorityQueue<Integer> freeParkingLots = new PriorityQueue<>();
    private Map<String, ParkingSlot> vehicleRegNumToParkingSlot = new HashMap<>();
    private HashMap<String, Set<ParkingSlot>> slotsByVehicleColour = new HashMap<>();

    public ParkingLot(int parkingSpots) {
        createParkingSlots(1, parkingSpots);
    }

    public int parkVehicle(Vehicle vehicle) throws ParkingLotException {
        int freeParkingSlotId = Optional
                .ofNullable(freeParkingLots.poll())
                .orElseThrow(() -> new ParkingLotException(ParkingLotErrorMessages.NO_FREE_SLOTS));
        String vehicleColour = vehicle.getColour();
        String vehicleRegNum = vehicle.getRegistrationNumber();
        ParkingSlot parkingSlot = allParkingSlots.get(freeParkingSlotId);

        vehicleRegNumToParkingSlot.put(vehicleRegNum, parkingSlot);
        Set<ParkingSlot> slotsOfVehicleColour =
                slotsByVehicleColour.getOrDefault(vehicleColour, new LinkedHashSet<>());
        slotsOfVehicleColour.add(parkingSlot);
        slotsByVehicleColour.put(vehicleColour, slotsOfVehicleColour);
        parkingSlot.addVehicle(vehicle);

        return freeParkingSlotId;
    }

    public void vehicleLeaveParkingLot(int parkingSlotId) throws ParkingLotException {
        ParkingSlot parkingSlot = Optional
                .ofNullable(allParkingSlots.get(parkingSlotId))
                .orElseThrow(() -> new ParkingLotException(ParkingLotErrorMessages.NO_SUCH_SLOT));
        Vehicle vehicle = Optional
                .ofNullable(parkingSlot.getOccupyingVehicle())
                .orElseThrow(() -> new ParkingLotException(ParkingLotErrorMessages.NO_VEHICLE_IN_SLOT));
        String vehicleColour = vehicle.getColour();
        String vehicleRegNum = vehicle.getRegistrationNumber();

        vehicleRegNumToParkingSlot.remove(vehicleRegNum);
        if (Optional.ofNullable(slotsByVehicleColour.get(vehicleColour)).isPresent()) {
            slotsByVehicleColour.get(vehicleColour).remove(parkingSlot);
        }
        parkingSlot.removeVehicle();
        freeParkingLots.add(parkingSlotId);
    }

    public Set<String> getRegNumbersForVehicleWithColour(String colour) {
        return slotsByVehicleColour.getOrDefault(colour, new LinkedHashSet<>())
                .stream()
                .map(slot -> slot.getOccupyingVehicle().getRegistrationNumber())
                .collect(Collectors.toSet());
    }

    public Set<Integer> getSlotNumbersForVehicleWithColour(String colour) {
        return slotsByVehicleColour.getOrDefault(colour, new LinkedHashSet<>())
                .stream()
                .map(ParkingSlot::getParkingSlotId)
                .collect(Collectors.toSet());
    }

    public int getSlotNumberForVehicleRegNum(String regNum) throws ParkingLotException {
        ParkingSlot parkingSlot =  Optional
                .ofNullable(vehicleRegNumToParkingSlot.get(regNum))
                .orElseThrow(() -> new ParkingLotException(ParkingLotErrorMessages.REG_NUM_NOT_FOUND));
        return parkingSlot.getParkingSlotId();
    }

    public List<ParkingSlot> getAllOccupiedSlots() {
        return allParkingSlots.values()
                .stream()
                .filter(ps -> !ps.isLotAvalible())
                .collect(Collectors.toList());
    }

    public Map<Integer, ParkingSlot> getParkingSlots() {
        return allParkingSlots;
    }

    private void createParkingSlots(int startingSlotNumber, int numberOfSlots) {
        for (int i = startingSlotNumber; i < numberOfSlots + startingSlotNumber; i++) {
            ParkingSlot parkingSlot = new ParkingSlot(i);
            freeParkingLots.add(i);
            allParkingSlots.put(i, parkingSlot);
        }
    }
}

