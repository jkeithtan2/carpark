package com.gojek.parkinglotassignment.command;

public enum UserInputErrorMsgs {
    NO_COMMAND("Please enter a command"),
    TOO_LITTLE_ARGS("Inadequate number of arguments"),
    INVALID_COMMAND_OPTION("Invalid command option"),
    WRONG_PARAM_PATTERN("Wrong input parameter types"),
    NO_LOT_CREATED("Lot has to created before operations can be done on it");

    private final String message;

    UserInputErrorMsgs(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}