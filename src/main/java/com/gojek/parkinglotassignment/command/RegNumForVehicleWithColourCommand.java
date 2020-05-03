package com.gojek.parkinglotassignment.command;

import com.gojek.parkinglotassignment.AppContext;
import com.gojek.parkinglotassignment.exceptions.InvalidUserInputException;
import com.gojek.parkinglotassignment.parkinglot.ParkingLot;

import java.util.Optional;
import java.util.Set;

public class RegNumForVehicleWithColourCommand implements Command {
    @Override
    public String execute(AppContext appContext, String[] inputArgs) throws InvalidUserInputException {
        ParkingLot parkingLot = Optional
                .ofNullable(appContext.getParkingLot())
                .orElseThrow(() ->  new InvalidUserInputException(UserInputErrorMsgs.NO_LOT_CREATED));
        Set<String> regNums = parkingLot.getRegNumbersForVehicleWithColour(inputArgs[0]);
        if (regNums.size() == 0) {
            return "No vehicles of such colour";
        } else {
            return String.join(", ", regNums);
        }
    }
}
