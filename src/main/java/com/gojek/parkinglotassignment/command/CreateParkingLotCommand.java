package com.gojek.parkinglotassignment.command;

import com.gojek.parkinglotassignment.AppContext;
import com.gojek.parkinglotassignment.exceptions.InvalidUserInputException;
import com.gojek.parkinglotassignment.parkinglot.ParkingLot;

public class CreateParkingLotCommand implements Command {
    @Override
    public String execute(AppContext appContext, String[] inputArgs) throws InvalidUserInputException {
        try {
            int initParkingSlots = Integer.valueOf(inputArgs[0]);
            appContext.setParkingLot(new ParkingLot(initParkingSlots));
            return "Created a parking lot with " + inputArgs[0] + " slots";
        } catch (NumberFormatException nfe) {
            throw new InvalidUserInputException(UserInputErrorMsgs.WRONG_PARAM_PATTERN);
        }
    }
}
