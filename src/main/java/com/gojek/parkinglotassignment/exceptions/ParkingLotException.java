package com.gojek.parkinglotassignment.exceptions;

import com.gojek.parkinglotassignment.parkinglot.ParkingLotErrorMessages;

public class ParkingLotException extends Exception {
    public ParkingLotException(ParkingLotErrorMessages plem) {
        super(plem.getMessage());
    }
}