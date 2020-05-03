package com.gojek.parkinglotassignment;

import com.gojek.parkinglotassignment.parkinglot.ParkingLot;

public class AppContext {
    private ParkingLot parkingLot;

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
