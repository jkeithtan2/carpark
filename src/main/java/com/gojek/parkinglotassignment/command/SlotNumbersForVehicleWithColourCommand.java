package com.gojek.parkinglotassignment.command;

import com.gojek.parkinglotassignment.AppContext;
import com.gojek.parkinglotassignment.exceptions.InvalidUserInputException;
import com.gojek.parkinglotassignment.parkinglot.ParkingLot;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SlotNumbersForVehicleWithColourCommand implements Command {
    @Override
    public String execute(AppContext appContext, String[] inputArgs) throws InvalidUserInputException {
        ParkingLot parkingLot = Optional
                .ofNullable(appContext.getParkingLot())
                .orElseThrow(() ->  new InvalidUserInputException(UserInputErrorMsgs.NO_LOT_CREATED));
        Set<Integer> slotNumbers = parkingLot.getSlotNumbersForVehicleWithColour(inputArgs[0]);
        if (slotNumbers.size() == 0) {
            return "No vehicles of such colour";
        } else {
            return slotNumbers
                    .stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
        }
    }
}
