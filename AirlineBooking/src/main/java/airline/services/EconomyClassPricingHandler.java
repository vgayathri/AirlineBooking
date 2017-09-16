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
public class EconomyClassPricingHandler implements PricingStrategy{

    public static final float ECO_NOPREMIUM_UPTO_OCCUPANCY_PERCENT = 40.0f;
    public static final float ECO_SLAB1RATE_UPTO_OCCUPANCY_PERCENT = 90.0f;
    public static final float ECO_MARKUP_SLAB1_RATE = 1.3f;
    public static final float ECO_MARKUP_SLAB2_RATE = 1.6f;


    EconomyClassPricingHandler() {}

    public float calculatePrice(Flight flightBeingPriced, SearchCriteria criteria) {

        if (criteria.getTravelClass() != TravelClass.ECONOMY)
            return -1.0f;

        float markedUpPrice=0.0f;
        float occupancyRate = flightBeingPriced.getOccupancyRate(TravelClass.ECONOMY);
        float economyBasePrice = flightBeingPriced.getBasePriceForASeatInClass(TravelClass.ECONOMY);
        if (occupancyRate <= ECO_NOPREMIUM_UPTO_OCCUPANCY_PERCENT)
            markedUpPrice = economyBasePrice;
        else if (occupancyRate > ECO_NOPREMIUM_UPTO_OCCUPANCY_PERCENT && occupancyRate <= ECO_SLAB1RATE_UPTO_OCCUPANCY_PERCENT)
            markedUpPrice = economyBasePrice * ECO_MARKUP_SLAB1_RATE;
        else
            markedUpPrice = economyBasePrice * ECO_MARKUP_SLAB2_RATE;

        return markedUpPrice * criteria.getNoOfPassengers();
    }

}
