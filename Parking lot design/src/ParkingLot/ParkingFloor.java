package ParkingLot;

import java.util.HashMap;
import java.util.Map;

public class ParkingFloor {

    private Integer floorId;
    private Map<ParkingSpotType, Map<Integer, ParkingSpot>> availableSpots;
    private Map<ParkingSpotType, Map<Integer, ParkingSpot>> occupiedSpots;
    private Map<Integer, ParkingSpot> spotMap;

    public ParkingFloor(Integer floorId){
        this.floorId = floorId;
        spotMap = new HashMap<>();
        availableSpots = new HashMap<>();
        availableSpots.put(ParkingSpotType.BIKE, new HashMap<>());
        availableSpots.put(ParkingSpotType.CAR, new HashMap<>());
        availableSpots.put(ParkingSpotType.TRUCK, new HashMap<>());

        occupiedSpots = new HashMap<>();
        occupiedSpots.put(ParkingSpotType.BIKE, new HashMap<>());
        occupiedSpots.put(ParkingSpotType.CAR, new HashMap<>());
        occupiedSpots.put(ParkingSpotType.TRUCK, new HashMap<>());
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void addParkingSpot(ParkingSpot parkingSpot){
        Map<Integer, ParkingSpot> map = availableSpots.get(parkingSpot.getType());
        map.put(parkingSpot.getSpotId(), parkingSpot);
        availableSpots.put(parkingSpot.getType(), map);
        spotMap.put(parkingSpot.getSpotId(), parkingSpot);
    }

    public ParkingSpot getParkingSpot(Integer parkingSpotId){
        return spotMap.get(parkingSpotId);
    }

    public ParkingSpotType getParkingSpotTypeForVehicle(VehicleType vehicleType){
        switch (vehicleType){
            case BIKE:
                return ParkingSpotType.BIKE;
            case TRUCK:
                return ParkingSpotType.TRUCK;
            default:
                return ParkingSpotType.CAR;
        }
    }

    public boolean canPark(Vehicle vehicle){
        ParkingSpotType parkingSpotType = getParkingSpotTypeForVehicle(vehicle.getType());
        return availableSpots.get(parkingSpotType).size() > 0;
    }

    public ParkingSpot getParkingSpot(Vehicle vehicle){
        ParkingSpotType parkingSpotType = getParkingSpotTypeForVehicle(vehicle.getType());
        for(Map.Entry<Integer, ParkingSpot> entry: availableSpots.get(parkingSpotType).entrySet()){
            return entry.getValue();
        }
        return null;
    }

    public void moveSpotFromAvailableMapToOccupiedMap(ParkingSpot parkingSpot){
        availableSpots.get(parkingSpot.getType()).remove(parkingSpot.getSpotId());
        occupiedSpots.get(parkingSpot.getType()).put(parkingSpot.getSpotId(), parkingSpot);
    }

    public void moveSpotFromOccupiedMapToAvailableMap(ParkingSpot parkingSpot){
        occupiedSpots.get(parkingSpot.getType()).remove(parkingSpot.getSpotId());
        availableSpots.get(parkingSpot.getType()).put(parkingSpot.getSpotId(), parkingSpot);
    }

    public void displayFloorBoard(String displayType, VehicleType vehicleType){
        switch (displayType){
            case "free_count":
                int count = availableSpots.get(getParkingSpotTypeForVehicle(vehicleType)).size();
                System.out.println("No. of free slots for " + vehicleType.toString() + " on Floor "+ floorId +": "+ count);
                break;
            case "free_slots":
                System.out.print("Free slots for "+ vehicleType.toString() + " on Floor "+ floorId +": ");
                for(Map.Entry<Integer, ParkingSpot> entry:
                        availableSpots.get(getParkingSpotTypeForVehicle(vehicleType)).entrySet()){
                    System.out.print(entry.getKey()+",");
                }
                System.out.println();
                break;
            case "occupied_slots":
                System.out.print("Occupied slots for "+ vehicleType.toString() + " on Floor "+ floorId +": ");
                for(Map.Entry<Integer, ParkingSpot> entry:
                        occupiedSpots.get(getParkingSpotTypeForVehicle(vehicleType)).entrySet()){
                    System.out.print(entry.getKey()+",");
                }
                System.out.println();
                break;
        }
    }
}