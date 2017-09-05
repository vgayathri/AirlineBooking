package airline.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Gayathri on 30/08/17.
 */
public class Flight {
    String flightID;
    String source;
    String destination;
    CarrierType typeOfCarrier;
    @DateTimeFormat(pattern = "d/MM/yyyy")
    LocalDate departureDate;

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public Flight(String flightID, String source, String destination) {
        this.flightID = flightID;
        this.source = source;
        this.destination = destination;

    }

    public Flight(String flightID, String source, String destination, LocalDate departureDate) {
        this.flightID = flightID;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
    }

    public Flight(String flightID, String source,
                  String destination, LocalDate departureDate, CarrierType carrierType) {
        this.flightID = flightID;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.typeOfCarrier = carrierType;
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

    public CarrierType getTypeOfCarrier() {
        return typeOfCarrier;
    }

    public void setTypeOfCarrier(CarrierType typeOfCarrier) {
        this.typeOfCarrier = typeOfCarrier;
    }
}
