package airline.model;

import org.apache.tomcat.jni.Local;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by Gayathri on 05/09/17.
 */


public class Carrier {

    CarrierType carrierType;
    Map<TravelClass, SeatsInfo> mapOfTravelClassToSeatsInfo;

    public Carrier( )
    {}


    public Carrier(CarrierType carrierType,
                   Map<TravelClass, SeatsInfo> mapOfSeatsPerClass )
    {
        this.carrierType = carrierType;
        this.mapOfTravelClassToSeatsInfo = mapOfSeatsPerClass;
    }


    public Map<TravelClass,SeatsInfo> getMapOfClassToSeats() {
        return mapOfTravelClassToSeatsInfo;
    }

    public Integer getAllocatedSeatsForClass(TravelClass classType) {
        if (mapOfTravelClassToSeatsInfo.containsKey(classType))
            return mapOfTravelClassToSeatsInfo.get(classType).getTotalNoOfSeats();
        else
            return 0;

    }
    public Integer getAvailableSeatsForClass(TravelClass classType) {
        if (mapOfTravelClassToSeatsInfo.containsKey(classType))
            return mapOfTravelClassToSeatsInfo.get(classType).getNoOfSeatsAvlb();
        else
            return 0;

    }


    public Float getBasePriceForTraveClass(TravelClass classType, LocalDate departureDate) {
        if (!mapOfTravelClassToSeatsInfo.containsKey(classType))
            return 0.0f;
        float basePriceForClass = mapOfTravelClassToSeatsInfo.get(classType).getBasePricePerSeat();
        float markedUpPrice=0.0f;
        switch(classType) {
            case ECONOMY:
                markedUpPrice = getMarkedUpEconomyClassPrices(basePriceForClass);
                break;
            case BUSINESS:
                markedUpPrice = getMarkedUpBusinessClassPrices(basePriceForClass,departureDate);
                break;
            case FIRST:
                markedUpPrice = getMarkedUpFirstClassPrices(basePriceForClass,departureDate);
                break;
            default:
                markedUpPrice = basePriceForClass;
                break;
        }
        return markedUpPrice;
    }

    /* add a premium on economyClassPrices across all carriers*/
    public float getMarkedUpEconomyClassPrices(float economyBasePrice) {
        float occupancyRate = mapOfTravelClassToSeatsInfo.get(TravelClass.ECONOMY).getPercentageOfSeatsOccuped();
        System.out.println("Occupancy rate " + occupancyRate);
        if (occupancyRate <= 40.0f)
            return economyBasePrice;
        else if (occupancyRate > 40.0f && occupancyRate <= 90.0f)
            return economyBasePrice * 1.3f;
        else
            return economyBasePrice * 1.6f;
    }

    public float getMarkedUpBusinessClassPrices(float businessBasePrice, LocalDate dateOfDepart) {
        DayOfWeek dayOfDepart = dateOfDepart.getDayOfWeek();
        if (dayOfDepart == DayOfWeek.MONDAY || dayOfDepart==DayOfWeek.FRIDAY || dayOfDepart == DayOfWeek.SUNDAY) {
            return businessBasePrice * 1.40f;
        } else {
            return businessBasePrice;
        }
    }

    public float getMarkedUpFirstClassPrices(float firstClassBasePrice, LocalDate departDate) {
        final int preBookingWindowInDays=10;
        LocalDate todaysDate = LocalDate.now();
        int differenceInDates = departDate.compareTo(todaysDate);
        System.out.println(differenceInDates);
        float markUpRate = 1.0f+(float)(preBookingWindowInDays - differenceInDates)/preBookingWindowInDays;
        System.out.println("BusinessClass markupRate" + markUpRate);
        return firstClassBasePrice * markUpRate;
    }



    public CarrierType getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(CarrierType carrierType) {
        this.carrierType = carrierType;
    }

    public boolean doesCarrierHaveClass(TravelClass travelClass) {
        return mapOfTravelClassToSeatsInfo.containsKey(travelClass);
    }

    public void setMapOfSeatsPerClass(Map<TravelClass, SeatsInfo> mapOfSeatsPerClass) {
        this.mapOfTravelClassToSeatsInfo = mapOfSeatsPerClass;
    }

    public boolean isCarrierTypeEquals(CarrierType carrierType) {
        if (getCarrierType().equals(carrierType))
            return true;
        else
            return false;
    }


}
