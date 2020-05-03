package com.gojek.parkinglotassignment.parkinglot;

public enum ParkingLotErrorMessages {
    NO_SUCH_SLOT("No such parking slot number"),
    NO_FREE_SLOTS("Sorry, parking lot is full"),
    NO_VEHICLE_IN_SLOT("No vehicle parked in this slot"),
    REG_NUM_NOT_FOUND("Not found");

    private final String message;

    ParkingLotErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
