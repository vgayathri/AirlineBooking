package airline.model;

import java.util.Date;

/**
 * Created by Gayathri on 30/08/17.
 */
public class Flight {
    String flightID;
    String source;
    String destination;
    int totalNoSeats;
    int noOfSeatsTaken;
    Date departureDate;

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Flight(String flightID, String source, String destination, int totalSeats, int noOfSeatsTaken) {
        this.flightID = flightID;
        this.source = source;
        this.destination = destination;
        this.totalNoSeats = totalSeats;
        this.noOfSeatsTaken = noOfSeatsTaken;
    }
    public Flight(String flightID, String source, String destination, int totalSeats, int noOfSeatsTaken, Date departureDate) {
        this.flightID = flightID;
        this.source = source;
        this.destination = destination;
        this.totalNoSeats = totalSeats;
        this.noOfSeatsTaken = noOfSeatsTaken;
        this.departureDate = departureDate;
    }

    public int getNoOfSeatsAvailable() {
        int noOfSeatsAvlb = totalNoSeats - noOfSeatsTaken;
        System.out.print(noOfSeatsAvlb);
        return noOfSeatsAvlb;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public String getSource() {
        return source;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        flightID = flightID;
    }

    public int getTotalNoSeats() {
        return totalNoSeats;
    }

    public void setTotalNoSeats(int totalNoSeats) {
        this.totalNoSeats = totalNoSeats;
    }

    public int getNoOfSeatsTaken() {
        return noOfSeatsTaken;
    }

    public void setNoOfSeatsTaken(int noOfSeatsTaken) {
        this.noOfSeatsTaken = noOfSeatsTaken;
    }
}
