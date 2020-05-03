package com.gojek.parkinglotassignment.command;

import com.gojek.parkinglotassignment.AppContext;
import com.gojek.parkinglotassignment.parkinglot.ParkingSlot;
import com.gojek.parkinglotassignment.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class StatusCommand implements Command {
    @Override
    public String execute(AppContext appContext, String[] inputArgs) {
        List<String> statusList = new ArrayList<>();
        statusList.add("Slot No.    Registration No    Colour");
        if (appContext.getParkingLot() != null) {
            List<ParkingSlot> allParkingSlots = appContext.getParkingLot().getAllOccupiedSlots();
            for (ParkingSlot ps : allParkingSlots) {
                Vehicle vehicle = ps.getOccupyingVehicle();
                String formattedVehStatus = String.format("%-8d    %-15s    %s",
                        ps.getParkingSlotId(), vehicle.getRegistrationNumber(), vehicle.getColour());
                statusList.add(formattedVehStatus);
            }
        }
        return String.join("\n", statusList);
    }
}
