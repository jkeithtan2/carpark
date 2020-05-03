package com.gojek.parkinglotassignment.command;

import com.gojek.parkinglotassignment.AppContext;
import com.gojek.parkinglotassignment.exceptions.InvalidUserInputException;
import com.gojek.parkinglotassignment.exceptions.ParkingLotException;

public interface Command {
    String execute(AppContext appContext, String[] inputArgs) throws InvalidUserInputException, ParkingLotException;
}