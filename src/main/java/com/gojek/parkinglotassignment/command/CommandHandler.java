package com.gojek.parkinglotassignment.command;

import com.gojek.parkinglotassignment.AppContext;
import com.gojek.parkinglotassignment.exceptions.InvalidUserInputException;
import com.gojek.parkinglotassignment.exceptions.ParkingLotException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandHandler {
    private Map<String, CmdOption> commands = new HashMap<>();
    private AppContext appContext;

    public CommandHandler(AppContext appContext) {
        this.appContext = appContext;
    }

    public void registerOption(String option, int numArgs, Command command) {
        CmdOption cmdOption = new CmdOption(option, numArgs, command);
        commands.put(option, cmdOption);
    }

    public String handler(String input) throws InvalidUserInputException, ParkingLotException {
        if (input == null || input.isEmpty()) {
            throw new InvalidUserInputException(UserInputErrorMsgs.NO_COMMAND);
        }

        String sanitizedInput = input.trim().replaceAll("\\s+", " ");
        String[] splitInput = sanitizedInput.split(" ");
        String commandOption = splitInput[0];
        CmdOption cmdOption = Optional.ofNullable(commands.get(commandOption))
                .orElseThrow(() -> new InvalidUserInputException(UserInputErrorMsgs.INVALID_COMMAND_OPTION));
        if (splitInput.length < cmdOption.getNumArgs() + 1) {
            throw new InvalidUserInputException(UserInputErrorMsgs.TOO_LITTLE_ARGS.getMessage() +
                    ", " + cmdOption.getNumArgs() + " args required");
        }
        String[] optionArgs = Arrays.copyOfRange(splitInput, 1, splitInput.length);
        return cmdOption.getCommand().execute(appContext, optionArgs);
    }

    private class CmdOption {
        String option;
        int numArgs;
        Command command;

        CmdOption(String option, int numArgs, Command command) {
            this.option = option;
            this.numArgs = numArgs;
            this.command = command;
        }

        String getOption() {
            return option;
        }

        int getNumArgs() {
            return numArgs;
        }

        Command getCommand() {
            return command;
        }
    }
}
