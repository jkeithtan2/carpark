package com.gojek.parkinglotassignment.command;

import com.gojek.parkinglotassignment.AppContext;
import com.gojek.parkinglotassignment.exceptions.InvalidUserInputException;
import com.gojek.parkinglotassignment.exceptions.ParkingLotException;
import com.gojek.parkinglotassignment.parkinglot.ParkingLot;

import java.util.Optional;

public class LeaveVehicleCommand implements Command {

    @Override
    public String execute(AppContext appContext, String[] inputArgs)
            throws InvalidUserInputException, ParkingLotException {
        try {
            ParkingLot parkingLot = Optional
                    .ofNullable(appContext.getParkingLot())
                    .orElseThrow(() ->  new InvalidUserInputException(UserInputErrorMsgs.NO_LOT_CREATED));
            int parkingSlot = Integer.valueOf(inputArgs[0]);
            parkingLot.vehicleLeaveParkingLot(parkingSlot);
            return "Slot number " + parkingSlot + " is free";
        } catch (NumberFormatException nfe) {
            throw new InvalidUserInputException(UserInputErrorMsgs.WRONG_PARAM_PATTERN);
        }
    }
}
