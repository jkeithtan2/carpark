package com.gojek.parkinglotassignment.exceptions;

import com.gojek.parkinglotassignment.command.UserInputErrorMsgs;

public class InvalidUserInputException extends Exception {
    public InvalidUserInputException(UserInputErrorMsgs parsingErrorMessages) {
        super(parsingErrorMessages.getMessage());
    }

    public InvalidUserInputException(String message) {
        super(message);
    }
}