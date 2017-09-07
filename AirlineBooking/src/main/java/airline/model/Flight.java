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
    Carrier carrierDetails;
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

    public String getFlightID() {
        return flightID;
    }

    public Flight(String flightID, String source,
                  String destination, LocalDate departureDate, Carrier flightCarrier) {
        this.flightID = flightID;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.carrierDetails = flightCarrier;
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

    public CarrierType getTypeOfCarrier() {
        return carrierDetails.getCarrierType();
    }

    public Boolean isFlightBetweenSrcAndDestination(String src, String destination) {
        return (getSource().equalsIgnoreCase(src) && getDestination().equalsIgnoreCase(destination));
    }

    public boolean isCarrierTypeEquals(CarrierType carrierType) {
        return carrierDetails.isCarrierTypeEquals(carrierType);
    }

    public boolean isDepartDateEquals(LocalDate giveDate) {
        return getDepartureDate().equals(giveDate);
    }

    public void setCarrierDetails(Carrier carrierModel) {
        this.carrierDetails = carrierModel;
    }

    public boolean isTravelClassAvailbleinFlight(TravelClass classOfTravel) {
        return this.carrierDetails.doesCarrierHaveClass(classOfTravel);
    }

    public int getNoOfSeatsForTravelClass(TravelClass travelClass) {
        return carrierDetails.getSeatsForClass(travelClass);
    }

}
