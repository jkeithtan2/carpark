package com.gojek.parkinglotassignment;

import com.gojek.parkinglotassignment.exceptions.ParkingLotException;
import com.gojek.parkinglotassignment.parkinglot.ParkingLot;
import com.gojek.parkinglotassignment.parkinglot.ParkingLotErrorMessages;
import com.gojek.parkinglotassignment.parkinglot.ParkingSlot;
import com.gojek.parkinglotassignment.vehicle.Car;
import com.gojek.parkinglotassignment.vehicle.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {

    private ParkingLot parkingLot;
    private Vehicle car1 = new Car("KA-01-HH-1234", "White");
    private Vehicle car2 = new Car("KA-01-HH-9999", "White");
    private Vehicle car3 = new Car("KA-01-BB-0001", "Black");
    private Vehicle car4 = new Car("KA-01-HH-7777", "Red");
    private Vehicle car5 = new Car("KA-01-HH-2701", "Blue");
    private Vehicle car6 = new Car("KA-01-HH-3141", "Black");
    private Vehicle car7 = new Car("KA-01-P-333", "White");

    @BeforeEach
    void setUp() throws ParkingLotException {
        parkingLot = new ParkingLot(6);
        parkingLot.parkVehicle(car1);
        parkingLot.parkVehicle(car2);
        parkingLot.parkVehicle(car3);
        parkingLot.parkVehicle(car4);
        parkingLot.parkVehicle(car5);
        parkingLot.parkVehicle(car6);
    }

    @Test
    void assertParkingLotSlotsCorrectlyFilled() {
        Map<Integer, ParkingSlot> parkingLotStatus = parkingLot.getParkingSlots();

        Vehicle firstSlotVehicle = parkingLotStatus.get(1).getOccupyingVehicle();
        Vehicle secondSlotVehicle = parkingLotStatus.get(2).getOccupyingVehicle();
        Vehicle thirdSlotVehicle = parkingLotStatus.get(3).getOccupyingVehicle();
        Vehicle fourthSlotVehicle = parkingLotStatus.get(4).getOccupyingVehicle();
        Vehicle fifthSlotVehicle = parkingLotStatus.get(5).getOccupyingVehicle();
        Vehicle sixthSlotVehicle = parkingLotStatus.get(6).getOccupyingVehicle();


        assertEquals(6, parkingLotStatus.size());

        assertEquals("KA-01-HH-1234", firstSlotVehicle.getRegistrationNumber());
        assertEquals("White", firstSlotVehicle.getColour());

        assertEquals("KA-01-HH-9999", secondSlotVehicle.getRegistrationNumber());
        assertEquals("White", secondSlotVehicle.getColour());

        assertEquals("KA-01-BB-0001", thirdSlotVehicle.getRegistrationNumber());
        assertEquals("Black", thirdSlotVehicle.getColour());

        assertEquals("KA-01-HH-7777", fourthSlotVehicle.getRegistrationNumber());
        assertEquals("Red", fourthSlotVehicle.getColour());

        assertEquals("KA-01-HH-2701", fifthSlotVehicle.getRegistrationNumber());
        assertEquals("Blue", fifthSlotVehicle.getColour());

        assertEquals("KA-01-HH-3141", sixthSlotVehicle.getRegistrationNumber());
        assertEquals("Black", sixthSlotVehicle.getColour());
    }

    @Test
    void assertLotVacatedAfterCarLeaves() throws ParkingLotException {
        parkingLot.vehicleLeaveParkingLot(4);
        ParkingSlot emptyParkingLot = parkingLot.getParkingSlots().get(4);
        assertTrue(emptyParkingLot.isLotAvalible());
    }

    @Test
    void assertVehicleParkedInNearestLotAfterCarLeft() throws ParkingLotException {
        parkingLot.vehicleLeaveParkingLot(4);
        int car7SlotNum = parkingLot.parkVehicle(car7);

        ParkingSlot car7Slot = parkingLot.getParkingSlots().get(car7SlotNum);

        assertEquals(car7.getRegistrationNumber(),
                car7Slot.getOccupyingVehicle().getRegistrationNumber());
        assertEquals(car7.getColour(),
                car7Slot.getOccupyingVehicle().getColour());
        assertEquals(4, car7SlotNum);
    }

    @Test
    void assertRegNumsOfWhiteVehicles() throws ParkingLotException {
        parkingLot.vehicleLeaveParkingLot(4);
        parkingLot.parkVehicle(car7);

        Set<String> regNums = parkingLot.getRegNumbersForVehicleWithColour("White");
        String[] expectedRegNums = new String[]{"KA-01-HH-1234", "KA-01-HH-9999", "KA-01-P-333"};
        assertArrayEquals(expectedRegNums, regNums.toArray());
    }

    @Test
    void assertVehicleRegNumRemovedFromVehiclesByColourMapAfterLeavings() throws ParkingLotException {
        Set<String> regNumsOfRedCars = parkingLot.getRegNumbersForVehicleWithColour("Red");
        assertEquals(1, regNumsOfRedCars.size());
        assertTrue(regNumsOfRedCars.contains("KA-01-HH-7777"));

        parkingLot.vehicleLeaveParkingLot(4);

        regNumsOfRedCars = parkingLot.getRegNumbersForVehicleWithColour("Red");
        assertEquals(0, regNumsOfRedCars.size());
    }

    @Test
    void assertNoVehicleRegNumsReturnedWhenInvalidColour() {
        Set<String> regNums = parkingLot.getRegNumbersForVehicleWithColour("green");
        assertEquals(0, regNums.size() );
    }

    @Test
    void assertSlotNumsOfWhiteVehicles() throws ParkingLotException {
        parkingLot.vehicleLeaveParkingLot(4);
        parkingLot.parkVehicle(car7);

        Set<Integer> slotNumbers = parkingLot.getSlotNumbersForVehicleWithColour("White");
        Integer[] expectedRegNums = new Integer[]{1, 2, 4};
        assertArrayEquals(expectedRegNums, slotNumbers.toArray());
    }

    @Test
    void assertSlotNumberByRegNum() throws ParkingLotException {
        int parkingSlot = parkingLot.getSlotNumberForVehicleRegNum("KA-01-HH-3141");
        assertEquals(6, parkingSlot);
    }

    @Nested
    class ParkingErrors {
        @Test
        void exceptionWhenParkingLotFull() {
            Throwable ParkingException = assertThrows(
                    ParkingLotException.class,
                    () -> parkingLot.parkVehicle(car7)
            );
            assertEquals(ParkingLotErrorMessages.NO_FREE_SLOTS.getMessage(), ParkingException.getMessage());
        }

        @Test
        void exceptionWhenRegNumDoesNotHaveAParkingSlot() {
            Throwable ParkingException = assertThrows(
                    ParkingLotException.class,
                    () -> parkingLot.getSlotNumberForVehicleRegNum("MH-04-AY-1111")
            );
            assertEquals(ParkingLotErrorMessages.REG_NUM_NOT_FOUND.getMessage(), ParkingException.getMessage());
        }

        @Test
        void exceptionWhenLeavingInvalidParkingSlot() {
            Throwable ParkingException = assertThrows (
                    ParkingLotException.class,
                    () -> parkingLot.vehicleLeaveParkingLot(15)
            );
            assertEquals(ParkingLotErrorMessages.NO_SUCH_SLOT.getMessage(), ParkingException.getMessage());
        }

        @Test
        void exceptionWhenLeavingNoVehicleInSlot() throws ParkingLotException {
            parkingLot.vehicleLeaveParkingLot(3);
            Throwable ParkingException = assertThrows(
                    ParkingLotException.class,
                    () -> parkingLot.vehicleLeaveParkingLot(3)
            );
            assertEquals(ParkingLotErrorMessages.NO_VEHICLE_IN_SLOT.getMessage(), ParkingException.getMessage());
        }
    }
}
