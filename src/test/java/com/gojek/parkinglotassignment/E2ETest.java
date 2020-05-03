package com.gojek.parkinglotassignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class E2ETest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testInteractiveMode() {
        String expectedInteractiveInputE2eOutput = "Created a parking lot with 6 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "Allocated slot number: 3\n" +
                "Allocated slot number: 4\n" +
                "Allocated slot number: 5\n" +
                "Allocated slot number: 6\n" +
                "Slot number 4 is free\n" +
                "Slot No.    Registration No    Colour\n" +
                "1           KA-01-HH-1234      White\n" +
                "2           KA-01-HH-9999      White\n" +
                "3           KA-01-BB-0001      Black\n" +
                "5           KA-01-HH-2701      Blue\n" +
                "6           KA-01-HH-3141      Black\n"+
                "Allocated slot number: 4\n"+
                "Sorry, parking lot is full\n"+
                "KA-01-HH-1234, KA-01-HH-9999, KA-01-P-333\n"+
                "1, 2, 4\n"+
                "6\n"+
                "Not found\n"+
                "Slot number 3 is free\n"+
                "6\n"+
                "No vehicles of such colour\n" +
                "No vehicles of such colour\n";
        ParkingApp parkingApp = new ParkingApp();
        System.setIn(new ByteArrayInputStream((
                "create_parking_lot 6\n" +
                        "park KA-01-HH-1234 White\n" +
                        "park KA-01-HH-9999 White\n" +
                        "park KA-01-BB-0001 Black\n" +
                        "park KA-01-HH-7777 Red\n" +
                        "park KA-01-HH-2701 Blue\n" +
                        "park KA-01-HH-3141 Black\n" +
                        "leave 4\n"+
                        "status\n"+
                        "park KA-01-P-333 White\n"+
                        "park DL-12-AA-9999 White\n"+
                        "registration_numbers_for_cars_with_colour White\n"+
                        "slot_numbers_for_cars_with_colour White\n"+
                        "slot_number_for_registration_number KA-01-HH-3141\n"+
                        "slot_number_for_registration_number MH-04-AY-1111\n"+
                        "leave 3\n" +
                        "slot_numbers_for_cars_with_colour Black\n" +
                        "slot_numbers_for_cars_with_colour Purple\n"+
                        "registration_numbers_for_cars_with_colour Purple"
        ).getBytes()));
        parkingApp.startApplication(new String[0]);
        String actualOutput = outContent.toString();
        assertEquals(expectedInteractiveInputE2eOutput, actualOutput);
    }
}
