package airline.model;

import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Gayathri on 31/08/17.
 */
public class SearchCriteria {
    String source;
    String destination;
    Integer noOfPassengers;
    @DateTimeFormat(pattern="dd/mm/yyyy")
    Date departureDate;


    public SearchCriteria(String source, String destination, Integer noOfPassengers, Date departureDate) {
        this.source = source;
        this.destination = destination;
        this.noOfPassengers = noOfPassengers;
        this.departureDate = departureDate;
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

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {

        this.departureDate = departureDate;
    }

}
