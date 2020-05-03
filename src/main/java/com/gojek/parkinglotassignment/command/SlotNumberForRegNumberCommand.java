package com.gojek.parkinglotassignment.command;

import com.gojek.parkinglotassignment.AppContext;
import com.gojek.parkinglotassignment.exceptions.InvalidUserInputException;
import com.gojek.parkinglotassignment.exceptions.ParkingLotException;
import com.gojek.parkinglotassignment.parkinglot.ParkingLot;

import java.util.Optional;

public class SlotNumberForRegNumberCommand implements Command {
    @Override
    public String execute(AppContext appContext, String[] inputArgs)
            throws ParkingLotException, InvalidUserInputException {
        ParkingLot parkingLot = Optional
                .ofNullable(appContext.getParkingLot())
                .orElseThrow(() ->  new InvalidUserInputException(UserInputErrorMsgs.NO_LOT_CREATED));
        int slot = parkingLot.getSlotNumberForVehicleRegNum(inputArgs[0]);
        return String.valueOf(slot);
    }
}
