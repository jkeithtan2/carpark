package com.gojek.parkinglotassignment;

import com.gojek.parkinglotassignment.command.*;
import com.gojek.parkinglotassignment.exceptions.InvalidUserInputException;
import com.gojek.parkinglotassignment.exceptions.ParkingLotException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ParkingApp {
    private CommandHandler commandHandler;

    ParkingApp() {
        AppContext appContext = new AppContext();

        commandHandler = new CommandHandler(appContext);
        commandHandler.registerOption("create_parking_lot", 1, new CreateParkingLotCommand());
        commandHandler.registerOption("park", 2, new ParkVehicleCommand());
        commandHandler.registerOption("leave", 1, new LeaveVehicleCommand());
        commandHandler.registerOption("status", 0, new StatusCommand());
        commandHandler.registerOption("registration_numbers_for_cars_with_colour", 1, new RegNumForVehicleWithColourCommand());
        commandHandler.registerOption("slot_numbers_for_cars_with_colour", 1, new SlotNumbersForVehicleWithColourCommand());
        commandHandler.registerOption("slot_number_for_registration_number", 1, new SlotNumberForRegNumberCommand());
        commandHandler.registerOption("exit", 0, new ExitCommand());
    }

    public static void main(String... args) {
        ParkingApp parkingApp = new ParkingApp();
        parkingApp.startApplication(args);
    }

    void startApplication(String[] args) {
        if (args.length > 0) {
            fileInputMode(args[0]);
        } else {
            interactiveMode();
        }
    }

    private void fileInputMode(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String input;
            while ((input = reader.readLine()) != null) {
                handleInput(input);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error while reading file " + e.getMessage());
        }
    }

    private void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            handleInput(scanner.nextLine());
        }
    }

    private void handleInput(String input) {
        try {
            System.out.println(commandHandler.handler(input));
        } catch (InvalidUserInputException | ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }
}
