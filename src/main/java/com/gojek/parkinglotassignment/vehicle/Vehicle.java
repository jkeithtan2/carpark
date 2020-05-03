package com.gojek.parkinglotassignment.vehicle;

public abstract class Vehicle {
    private final VehicleType vehicleType;
    private String registrationNumber;
    private String colour;

    Vehicle(VehicleType vehicleType, String registrationNumber, String colour) {
        this.vehicleType = vehicleType;
        this.registrationNumber = registrationNumber;
        this.colour = colour;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColour() {
        return colour;
    }
}
