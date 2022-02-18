package ParkingLot;

import ParkingLot.ParkingLot;
import ParkingLot.ParkingSpot;
import ParkingLot.Vehicle;

public interface ParkingStrategy {

    ParkingSpot getAvailableSpot(ParkingLot parkingLot, Vehicle vehicle);
}