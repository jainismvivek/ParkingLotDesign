package ParkingLot;

import ParkingLot.ParkingStrategy.NearestAvailableSlotStrategy;
import ParkingLot.ParkingStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {

    private final String parkingLotId;
    private final Integer noOfFloors;
    private final Integer noOfSpotsPerFloor;

    private Map<Integer, ParkingFloor> floorMap;
    private Map<String, ParkingTicket> ticketMap;

    private final ParkingStrategy parkingStrategy;

    public static final Integer RESERVED_SLOT_FOR_TRUCK = 1;
    public static final List<Integer> RESERVED_SLOT_FOR_BIKE = List.of(2, 3);

    public ParkingLot(String parkingLotId, String noOfFloors, String noOfSpotsPerFloor){
        this.parkingLotId = parkingLotId;
        this.noOfFloors = Integer.parseInt(noOfFloors);
        this.noOfSpotsPerFloor = Integer.parseInt(noOfSpotsPerFloor);
        floorMap = new HashMap<>();
        ticketMap = new HashMap<>();
        parkingStrategy = new NearestAvailableSlotStrategy();
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public Map<String, ParkingTicket> getTicketMap() {
        return ticketMap;
    }

    public Map<Integer, ParkingFloor> getFloorMap() {
        return floorMap;
    }

    public void initializeParkingLot(){
        for(int floorNum = 1; floorNum <= noOfFloors; floorNum++){
            ParkingFloor floor = new ParkingFloor(floorNum);
            for(int spotNum = 1; spotNum <= noOfSpotsPerFloor; spotNum++){
                ParkingSpot parkingSpot = new ParkingSpot(floorNum, spotNum);
                if(spotNum == RESERVED_SLOT_FOR_TRUCK) {
                    parkingSpot.setType(ParkingSpotType.TRUCK);
                }
                else if(RESERVED_SLOT_FOR_BIKE.contains(spotNum)) {
                    parkingSpot.setType(ParkingSpotType.BIKE);
                }
                else {
                    parkingSpot.setType(ParkingSpotType.CAR);
                }
                floor.addParkingSpot(parkingSpot);
            }
            floorMap.put(floorNum, floor);
        }
        System.out.println("Created parking lot with "+ noOfFloors +" floors and "+ noOfSpotsPerFloor + " slots per floor");
    }

    public void addFloor(ParkingFloor floor){
        floorMap.put(floor.getFloorId(), floor);
    }

    public void addSpot(String floorId, ParkingSpot parkingSpot){
        ParkingFloor floor = floorMap.get(floorId);
        floor.addParkingSpot(parkingSpot);
    }

    public ParkingTicket generateParkingTicket(String parkingLotId, Integer floorId, Integer spotId){
        String id = parkingLotId + "_" + floorId + "_" + spotId;
        ParkingTicket ticket = new ParkingTicket(id, floorId, spotId);
        return ticket;
    }

    public void parkVehicle(Vehicle vehicle){
        ParkingSpot parkingSpot = parkingStrategy.getAvailableSpot(this, vehicle);
        if(parkingSpot == null){
            System.out.println("Parking Lot full");
            return;
        }
        ParkingTicket ticket = this.generateParkingTicket(this.getParkingLotId(), parkingSpot.getFloorId(), parkingSpot.getSpotId());
        parkingSpot.assignVehicle(vehicle);
        vehicle.assignTicket(ticket);
        ParkingFloor floor = floorMap.get(parkingSpot.getFloorId());
        floor.moveSpotFromAvailableMapToOccupiedMap(parkingSpot);
        ticketMap.put(ticket.getTicketId(), ticket);
        return;
    }

    public void vacateVehicle(String ticketId){
        if(!ticketMap.containsKey(ticketId)) {
            System.out.println("Invalid Ticket");
            return;
        }
        ParkingTicket parkingTicket = ticketMap.get(ticketId);
        ParkingFloor parkingFloor = floorMap.get(parkingTicket.getFloorId());
        ParkingSpot parkingSpot = parkingFloor.getParkingSpot(parkingTicket.getSpotId());
        Vehicle vehicle = parkingSpot.getVehicle();
        parkingSpot.vacateVehicle();
        ParkingFloor floor = floorMap.get(parkingSpot.getFloorId());
        floor.moveSpotFromOccupiedMapToAvailableMap(parkingSpot);
        System.out.println("Unparked vehicle with Registration Number: "+
                vehicle.getRegistrationNumber() +" and Color: "+ vehicle.getColor());
        //this.scanAndPay()
        ticketMap.remove(ticketId);
    }

    public void displayBoard(String displayType, String vehicleType){
        for(ParkingFloor parkingFloor: floorMap.values()){
            switch (vehicleType){
                case "CAR":
                    parkingFloor.displayFloorBoard(displayType, VehicleType.CAR);
                    break;
                case "BIKE":
                    parkingFloor.displayFloorBoard(displayType, VehicleType.BIKE);
                    break;
                case "TRUCK":
                    parkingFloor.displayFloorBoard(displayType, VehicleType.TRUCK);
                    break;
            }
        }
    }
}