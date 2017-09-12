package airline.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Created by Gayathri on 31/08/17.
 */
public class SearchCriteria {
    String source;
    String destination;
    Integer noOfPassengers;
    String departureDateStr;
    @DateTimeFormat(pattern="d/MM/yyyy")
    LocalDate departureDate;
    @NotEmpty(message = "{class of travel required}")
    TravelClass travelClass;

    public SearchCriteria(String source, String destination, Integer noOfPassengers, LocalDate departureDate) {
        this.source = source;
        this.destination = destination;
        this.noOfPassengers = noOfPassengers;
        this.departureDate = departureDate;
    }

    public SearchCriteria(String source, String destination,
                          Integer noOfPassengers, LocalDate departureDate, TravelClass travelClass ) {
        this.source = source;
        this.destination = destination;
        this.noOfPassengers = noOfPassengers;
        this.departureDate = departureDate;
        this.travelClass = travelClass;
    }

    public SearchCriteria(String source, String destination, Integer noOfPassengers) {
        this.source = source;
        this.destination = destination;
        this.noOfPassengers = noOfPassengers;
    }

    public SearchCriteria() {}

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(Integer noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {

        this.departureDate = departureDate;
    }


    public String getDepartureDateStr() {
        return departureDateStr;
    }

    public void setDepartureDateStr(String departDateStr) {
        this.departureDateStr = departDateStr;
        if (departDateStr.isEmpty()) {
            this.departureDate = null;
            return;
        }
        try {
            setDepartureDate(LocalDate.parse(getDepartureDateStr()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setTravelClass(TravelClass travelClass) {
        this.travelClass = travelClass;
    }

    public TravelClass getTravelClass() {
        return travelClass;
    }

}
