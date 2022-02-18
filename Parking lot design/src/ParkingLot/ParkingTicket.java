package ParkingLot;

public class ParkingTicket {

    private String ticketId;
    private Integer floorId;
    private Integer spotId;

    public ParkingTicket(String ticketId, Integer floorId, Integer spotId){
        this.ticketId = ticketId;
        this.floorId = floorId;
        this.spotId = spotId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public Integer getSpotId() {
        return spotId;
    }
}