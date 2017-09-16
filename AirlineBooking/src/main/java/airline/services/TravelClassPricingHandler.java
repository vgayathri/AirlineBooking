package airline.services;

import airline.model.Flight;
import airline.model.PricingStrategy;
import airline.model.SearchCriteria;
import airline.model.TravelClass;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by Gayathri on 16/09/17.
 */
public class TravelClassPricingHandler implements PricingStrategy{

    Flight flightBeingPriced;
    SearchCriteria criteriaForPricing;

    TravelClassPricingHandler(Flight flightObj, SearchCriteria criteria) {
        this.flightBeingPriced = flightObj;
        this.criteriaForPricing = criteria;
    }

    TravelClassPricingHandler() {}

    public Float calculatePrice(Flight flightBeingPriced, SearchCriteria criteria) {

        this.flightBeingPriced = flightBeingPriced;
        this.criteriaForPricing = criteria;
        LocalDate departureDate = flightBeingPriced.getDepartureDate();
        TravelClass classOfTravel = criteria.getTravelClass();
        float basePriceForClass = flightBeingPriced.getBasePriceForASeatInClass(classOfTravel);
        float markedUpPrice=0.0f;
        switch(classOfTravel) {
            case ECONOMY:
                markedUpPrice = getMarkedUpEconomyClassPrices();
                break;
            case BUSINESS:
                markedUpPrice = getMarkedUpBusinessClassPrices();
                break;
            case FIRST:
                markedUpPrice = getMarkedUpFirstClassPrices();
                break;
            default:
                markedUpPrice = basePriceForClass;
                break;
        }
        return markedUpPrice * criteria.getNoOfPassengers();
    }


    /* add a premium on economyClassPrices across all carriers*/
    public float getMarkedUpEconomyClassPrices() {
        final float ECO_NOPREMIUM_UPTO_OCCUPANCY_PERCENT = 40.0f;
        final float ECO_SLAB1RATE_UPTO_OCCUPANCY_PERCENT = 90.0f;
        final float ECO_MARKUP_SLAB1_RATE = 1.3f;
        final float ECO_MARKUP_SLAB2_RATE = 1.6f;

        float occupancyRate = flightBeingPriced.getOccupancyRate(TravelClass.ECONOMY);
        float economyBasePrice = flightBeingPriced.getBasePriceForASeatInClass(TravelClass.ECONOMY);
        if (occupancyRate <= ECO_NOPREMIUM_UPTO_OCCUPANCY_PERCENT)
            return economyBasePrice;
        else if (occupancyRate > ECO_NOPREMIUM_UPTO_OCCUPANCY_PERCENT && occupancyRate <= ECO_SLAB1RATE_UPTO_OCCUPANCY_PERCENT)
            return economyBasePrice * ECO_MARKUP_SLAB1_RATE;
        else
            return economyBasePrice * ECO_MARKUP_SLAB2_RATE;
    }

    public float getMarkedUpBusinessClassPrices() {
        final float BUSINESS_PREMIUM_RATE = 1.40f;
        float businessBasePrice = flightBeingPriced.getBasePriceForASeatInClass(TravelClass.BUSINESS);
        LocalDate dateOfDepart = flightBeingPriced.getDepartureDate();
        DayOfWeek dayOfDepart = dateOfDepart.getDayOfWeek();
        if (dayOfDepart == DayOfWeek.MONDAY ||
                dayOfDepart==DayOfWeek.FRIDAY ||
                dayOfDepart == DayOfWeek.SUNDAY) {
            return businessBasePrice * BUSINESS_PREMIUM_RATE;
        } else {
            return businessBasePrice;
        }
    }

    public float getMarkedUpFirstClassPrices() {
        final int PREBOOKING_WINDOW_IN_DAYS=10;
        final float FIRST_BASE_RATE = 1.0f;
        //10 percent increase for each elapsed day of booking window
        final float PERCENT_INC_PER_ELAPSEDDAY = 0.10f;
        float firstClassBasePrice = flightBeingPriced.getBasePriceForASeatInClass(TravelClass.FIRST);
        LocalDate departDate = flightBeingPriced.getDepartureDate();
        LocalDate todaysDate = LocalDate.now();
        Long noOfDaysRemainingInBookingWindow =  ChronoUnit.DAYS.between(todaysDate, departDate);
        Long elapsedDaysSinceBookingOpened = PREBOOKING_WINDOW_IN_DAYS - noOfDaysRemainingInBookingWindow;
        float firstClassMarkUpRate = FIRST_BASE_RATE + (elapsedDaysSinceBookingOpened*PERCENT_INC_PER_ELAPSEDDAY) ;
        return firstClassBasePrice * firstClassMarkUpRate;
    }

}
