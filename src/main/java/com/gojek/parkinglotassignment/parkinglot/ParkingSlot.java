package com.gojek.parkinglotassignment.parkinglot;

import com.gojek.parkinglotassignment.vehicle.Vehicle;

public class ParkingSlot {
    private int parkingSlotId;
    private Vehicle occupyingVehicle;

    public ParkingSlot(int parkingSlotId) {
        this.parkingSlotId = parkingSlotId;
    }

    public int getParkingSlotId() {
        return parkingSlotId;
    }

    public Vehicle getOccupyingVehicle() {
        return occupyingVehicle;
    }

    public void addVehicle(Vehicle vehicle) {
        occupyingVehicle = vehicle;
    }

    void removeVehicle() {
        occupyingVehicle = null;
    }

    public boolean isLotAvalible() {
        return occupyingVehicle == null;
    }
}