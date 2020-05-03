package com.gojek.parkinglotassignment.command;

import com.gojek.parkinglotassignment.AppContext;
import com.gojek.parkinglotassignment.exceptions.InvalidUserInputException;
import com.gojek.parkinglotassignment.exceptions.ParkingLotException;
import com.gojek.parkinglotassignment.parkinglot.ParkingLot;
import com.gojek.parkinglotassignment.vehicle.Car;
import com.gojek.parkinglotassignment.vehicle.Vehicle;

import java.util.Optional;

public class ParkVehicleCommand implements Command {
    @Override
    public String execute(AppContext appContext, String[] inputArgs)
            throws ParkingLotException, InvalidUserInputException {
        ParkingLot parkingLot = Optional
                .ofNullable(appContext.getParkingLot())
                .orElseThrow(() ->  new InvalidUserInputException(UserInputErrorMsgs.NO_LOT_CREATED));
        Vehicle vehicle = new Car(inputArgs[0], inputArgs[1]);
        int slot = parkingLot.parkVehicle(vehicle);
        return "Allocated slot number: " + slot;
    }
}
