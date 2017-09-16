package airline.services;

import airline.model.Flight;
import airline.model.PricingStrategy;
import airline.model.SearchCriteria;
import airline.model.TravelClass;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Created by Gayathri on 16/09/17.
 */
public class BusinessClassPricingHandler implements PricingStrategy{
    public final float BUSINESS_PREMIUM_RATE = 1.40f;

    BusinessClassPricingHandler(){}

    public float calculatePrice(Flight flightObj, SearchCriteria criteria) {
        if (criteria.getTravelClass() != TravelClass.BUSINESS)
            return -1.0f;
        float businessBasePrice = flightObj.getBasePriceForASeatInClass(TravelClass.BUSINESS);
        float markedUpPrice = 0.0f;
        LocalDate dateOfDepart = flightObj.getDepartureDate();
        DayOfWeek dayOfDepart = dateOfDepart.getDayOfWeek();
        if (dayOfDepart == DayOfWeek.MONDAY ||
                dayOfDepart==DayOfWeek.FRIDAY ||
                dayOfDepart == DayOfWeek.SUNDAY) {
            markedUpPrice = businessBasePrice * BUSINESS_PREMIUM_RATE;
        } else {
            markedUpPrice = businessBasePrice;
        }
        return markedUpPrice * criteria.getNoOfPassengers();
    }

}
