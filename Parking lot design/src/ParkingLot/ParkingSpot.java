package ParkingLot;

public class ParkingSpot {

    private Integer spotId;
    private Integer floorId;
    private ParkingSpotType type;
    private Vehicle vehicle;
    private Boolean isSpotFree;

    ParkingSpot(Integer floorId, Integer spotId){
        this.floorId = floorId;
        this.spotId = spotId;
        isSpotFree = true;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setType(ParkingSpotType type) {
        this.type = type;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public ParkingSpotType getType() {
        return type;
    }

    public Integer getSpotId() {
        return spotId;
    }

    public Boolean getIsSpotFree() {
        return isSpotFree;
    }

    public void assignVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
        isSpotFree = false;
    }

    public void vacateVehicle(){
        this.vehicle = null;
        isSpotFree = true;
    }
}