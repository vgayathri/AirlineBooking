package airline.model;

import org.apache.tomcat.jni.Local;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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

    public Integer getTotalAllocatedSeatsForClass(TravelClass classType) {
        if (doesCarrierHaveClass(classType))
            return mapOfTravelClassToSeatsInfo.get(classType).getTotalNoOfSeats();
        else
            return 0;

    }

    public Integer getAvailableSeatsForClass(TravelClass classType) {
        if (doesCarrierHaveClass(classType))
            return mapOfTravelClassToSeatsInfo.get(classType).getNoOfSeatsAvlb();
        else
            return 0;

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
    public void setSeatInfoForTravelClass(TravelClass travelClass, SeatsInfo newSeatInfo){
        if (doesCarrierHaveClass(travelClass)) {
            this.mapOfTravelClassToSeatsInfo.replace(travelClass, newSeatInfo);
        }
        return;
    }


    public boolean isCarrierTypeEquals(CarrierType carrierType) {
        if (getCarrierType().equals(carrierType))
            return true;
        else
            return false;
    }

    public float getBasePriceForASeatInClass(TravelClass travelClass) {
        if (doesCarrierHaveClass(travelClass))
            return mapOfTravelClassToSeatsInfo.get(travelClass).getBasePricePerSeat();
        else
            return 0.0f;
    }

    public float getOccupancyRate(TravelClass travelClass) {
        if (doesCarrierHaveClass(travelClass))
            return mapOfTravelClassToSeatsInfo.get(travelClass).getPercentageOfSeatsOccuped();
        else
            return 0.0f;
    }


}
