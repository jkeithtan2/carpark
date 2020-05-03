package com.gojek.parkinglotassignment.vehicle;

public class Car extends Vehicle {
    public Car(String registrationNumber, String colour) {
        super(VehicleType.CAR, registrationNumber, colour);
    }
}
