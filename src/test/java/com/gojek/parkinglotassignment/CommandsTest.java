package com.gojek.parkinglotassignment;

import com.gojek.parkinglotassignment.command.*;
import com.gojek.parkinglotassignment.exceptions.InvalidUserInputException;
import com.gojek.parkinglotassignment.exceptions.ParkingLotException;
import com.gojek.parkinglotassignment.parkinglot.ParkingLot;
import com.gojek.parkinglotassignment.parkinglot.ParkingSlot;
import com.gojek.parkinglotassignment.vehicle.Car;
import com.gojek.parkinglotassignment.vehicle.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

class CommandsTest {
    private AppContext appContext;
    private CommandHandler commandHandler;

    @BeforeEach
    void setup() throws ParkingLotException {
        Map<Integer, ParkingSlot> allParkingSpots = new HashMap<>();

        Vehicle parkedCar1 = new Car("regFirst", "White");
        Vehicle parkedCar2 = new Car("regSecond", "White");

        ParkingSlot parkingSlot1 = new ParkingSlot(1);
        ParkingSlot parkingSlot2 = new ParkingSlot(2);
        parkingSlot1.addVehicle(parkedCar1);
        parkingSlot2.addVehicle(parkedCar2);
        allParkingSpots.put(1, parkingSlot1);
        allParkingSpots.put(2, parkingSlot2);

        Set<String> regNumsOfVehiclesThatAreWhite = new HashSet<>();
        regNumsOfVehiclesThatAreWhite.add("regFirst");
        regNumsOfVehiclesThatAreWhite.add("regSecond");

        Set<Integer> slotNumsOfVehiclesThatAreWhite = new HashSet<>();
        slotNumsOfVehiclesThatAreWhite.add(1);
        slotNumsOfVehiclesThatAreWhite.add(2);

        ParkingLot parkingLot = Mockito.mock(ParkingLot.class);
        when(parkingLot.parkVehicle(any(Vehicle.class))).thenReturn(3);
        when(parkingLot.getAllOccupiedSlots()).thenReturn(new ArrayList<>(allParkingSpots.values()));
        when(parkingLot.getRegNumbersForVehicleWithColour("White")).thenReturn(regNumsOfVehiclesThatAreWhite);
        when(parkingLot.getSlotNumbersForVehicleWithColour("White")).thenReturn(slotNumsOfVehiclesThatAreWhite);
        when(parkingLot.getSlotNumberForVehicleRegNum("regFirst")).thenReturn(1);

        appContext = new AppContext();
        appContext.setParkingLot(parkingLot);
    }

    @Test
    void assertCommandArgsParsed() throws InvalidUserInputException, ParkingLotException {
        CommandHandler commandHandler = new CommandHandler(new AppContext());
        commandHandler.registerOption("testOption", 2, (a, list) -> list[0] + " " + list[1]);
        String output = commandHandler.handler("testOption args1 args2");
        assertEquals("args1 args2", output);
    }

    @Test
    void assertCreatedLotOutputWhenParkingLotCreated() throws ParkingLotException, InvalidUserInputException {
        Command createCommand = new CreateParkingLotCommand();
        String output = createCommand.execute(appContext, new String[]{"2"});
        assertEquals("Created a parking lot with 2 slots", output);
    }

    @Test
    void assertAllocatedSlotOutputWhenParkCommandCalled()
            throws ParkingLotException, InvalidUserInputException {
        Command parkCommand = new ParkVehicleCommand();
        String output = parkCommand.execute(appContext, new String[]{"regThird", "Purple"});
        assertEquals("Allocated slot number: 3", output);
    }

    @Test
    void assertSlotNumberFreedOutputWhenLeaveCommandCalled() throws ParkingLotException, InvalidUserInputException {
        Command leaveCommand = new LeaveVehicleCommand();
        String output = leaveCommand.execute(appContext, new String[]{"1"});
        assertEquals("Slot number 1 is free", output);
    }

    @Test
    void assertStatusFormattingWhenStatusCommandCalled() throws ParkingLotException, InvalidUserInputException {
        Command statusCommand = new StatusCommand();
        String output = statusCommand.execute(appContext, new String[]{""});
        assertEquals(output, "Slot No.    Registration No    Colour\n" +
                "1           regFirst           White\n" +
                "2           regSecond          White");
    }

    @Test
    void assertRegNumsOutputWhenRegNumForCarsThatAreWhiteCommandCalled()
            throws ParkingLotException, InvalidUserInputException {
        Command command = new RegNumForVehicleWithColourCommand();
        String output = command.execute(appContext, new String[]{"White"});
        assertEquals("regFirst, regSecond", output);
    }

    @Test
    void assertSlotNumbersOutputWhenSlotNumberForCarsThatAreWhiteCommandCalled()
            throws ParkingLotException, InvalidUserInputException {
        Command command = new SlotNumbersForVehicleWithColourCommand();
        String output = command.execute(appContext, new String[]{"White"});
        assertEquals("1, 2", output);
    }

    @Test
    void assertSlotNumberOutputForRegistrationNumberOfFirstCar() throws ParkingLotException, InvalidUserInputException {
        Command command = new SlotNumberForRegNumberCommand();
        String output = command.execute(appContext, new String[]{"regFirst"});
        assertEquals("1", output);
    }

    @Nested
    class InvalidArgsTest {
        @BeforeEach
        void setUp() {
            commandHandler = new CommandHandler(null);
            commandHandler.registerOption("invalidArgsOption", 1, (ac, l) -> {
                try {
                    Integer.valueOf(l[0]);
                    return null;
                } catch (NumberFormatException nfe) {
                    throw new InvalidUserInputException(UserInputErrorMsgs.WRONG_PARAM_PATTERN);
                }
            });
        }

        @Test
        void noCommandOptionsPassed() {
            Exception exception = assertThrows(InvalidUserInputException.class,
                    () -> commandHandler.handler(""));
            assertEquals("Please enter a command", exception.getMessage());
        }

        @Test
        void throwExceptionWhenTooFewArgs() {
            Exception exception = assertThrows(InvalidUserInputException.class,
                    () -> commandHandler.handler("invalidArgsOption"));
            assertEquals("Inadequate number of arguments, 1 args required", exception.getMessage());
        }

        @Test
        void throwExceptionWrongArgsType() {
            Throwable exception = assertThrows(InvalidUserInputException.class,
                    () -> commandHandler.handler("invalidArgsOption wrong"));
            assertEquals("Wrong input parameter types", exception.getMessage());
        }

        @Test
        void invalidCommand() {
            InvalidUserInputException exception = assertThrows(InvalidUserInputException.class,
                    () -> commandHandler.handler("invalidCommand"));
            assertEquals(UserInputErrorMsgs.INVALID_COMMAND_OPTION.getMessage(), exception.getMessage());
        }

        @Test
        void commandBeforeParkingLotCreated() {
            appContext = new AppContext();
            commandHandler = new CommandHandler(appContext);
            commandHandler.registerOption("park", 2, new ParkVehicleCommand());

            InvalidUserInputException exception = assertThrows(InvalidUserInputException.class,
                    () -> commandHandler.handler("park reg white"));
            assertEquals(UserInputErrorMsgs.NO_LOT_CREATED.getMessage(), exception.getMessage());
        }
    }
}
