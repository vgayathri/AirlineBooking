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

    public Float getPriceForSeatsInAFlight(Flight flightBeingPriced, SearchCriteria criteria) {

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
        final float basePriceOccupanyRate = 40.0f;
        final float firstMarkupOccupancyRate = 90.0f;
        final float economyFirtSlabMarkUpRate = 1.3f;
        final float economySecondSlabMarkUpRate = 1.6f;

        float occupancyRate = flightBeingPriced.getOccupancyRate(TravelClass.ECONOMY);
        float economyBasePrice = flightBeingPriced.getBasePriceForASeatInClass(TravelClass.ECONOMY);
        if (occupancyRate <= basePriceOccupanyRate)
            return economyBasePrice;
        else if (occupancyRate > basePriceOccupanyRate && occupancyRate <= firstMarkupOccupancyRate)
            return economyBasePrice * economyFirtSlabMarkUpRate;
        else
            return economyBasePrice * economySecondSlabMarkUpRate;
    }

    public float getMarkedUpBusinessClassPrices() {
        final float businesMarkUpRateBasedOnTravelDays = 1.40f;
        float businessBasePrice = flightBeingPriced.getBasePriceForASeatInClass(TravelClass.BUSINESS);
        LocalDate dateOfDepart = flightBeingPriced.getDepartureDate();
        DayOfWeek dayOfDepart = dateOfDepart.getDayOfWeek();
        if (dayOfDepart == DayOfWeek.MONDAY || dayOfDepart==DayOfWeek.FRIDAY || dayOfDepart == DayOfWeek.SUNDAY) {
            return businessBasePrice * businesMarkUpRateBasedOnTravelDays;
        } else {
            return businessBasePrice;
        }
    }

    public float getMarkedUpFirstClassPrices() {
        final int preBookingWindowInDays=10;
        final float firstClassBaseRate = 1.0f;
        //10 percent increase for each elapsed day of booking window
        float percentageIncreasePerElapsedDay = 0.10f;
        float firstClassBasePrice = flightBeingPriced.getBasePriceForASeatInClass(TravelClass.FIRST);
        LocalDate departDate = flightBeingPriced.getDepartureDate();
        LocalDate todaysDate = LocalDate.now();
        Long noOfDaysRemainingInBookingWindow =  ChronoUnit.DAYS.between(todaysDate, departDate);
        Long elapsedDaysSinceBookingOpened = preBookingWindowInDays - noOfDaysRemainingInBookingWindow;
        float firstClassMarkUpRate = firstClassBaseRate + (elapsedDaysSinceBookingOpened*percentageIncreasePerElapsedDay) ;
        return firstClassBasePrice * firstClassMarkUpRate;
    }

}
