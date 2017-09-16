package airline.services;

import airline.model.Flight;
import airline.model.PricingStrategy;
import airline.model.SearchCriteria;
import airline.model.TravelClass;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by Gayathri on 16/09/17.
 */
public class FirstClassPricingHandler implements PricingStrategy {

    public static final int PREBOOKING_WINDOW_IN_DAYS=10;
    public static final float FIRST_BASE_RATE = 1.0f;
    //10 percent increase for each elapsed day of booking window
    public static final float PERCENT_INC_PER_ELAPSEDDAY = 0.10f;
    FirstClassPricingHandler() {}

    public float calculatePrice(Flight flightObj, SearchCriteria criteria) {

        if (criteria.getTravelClass() != TravelClass.FIRST)
            return -1.0f;
        float markedUpPrice = 0.0f;
        float firstClassBasePrice = flightObj.getBasePriceForASeatInClass(TravelClass.FIRST);
        LocalDate departDate = flightObj.getDepartureDate();
        LocalDate todaysDate = LocalDate.now();
        Long noOfDaysRemainingInBookingWindow =  ChronoUnit.DAYS.between(todaysDate, departDate);
        Long elapsedDaysSinceBookingOpened = PREBOOKING_WINDOW_IN_DAYS - noOfDaysRemainingInBookingWindow;
        if (noOfDaysRemainingInBookingWindow > PREBOOKING_WINDOW_IN_DAYS) {
            return -1.0f;
        }
        float firstClassMarkUpRate = FIRST_BASE_RATE + (elapsedDaysSinceBookingOpened*PERCENT_INC_PER_ELAPSEDDAY) ;
        markedUpPrice = firstClassBasePrice * firstClassMarkUpRate;
        return markedUpPrice * criteria.getNoOfPassengers();
    }

}

