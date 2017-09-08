package airline.model;

import java.util.Base64;
import java.util.Map;

/**
 * Created by Gayathri on 05/09/17.
 */


public class Carrier {

    CarrierType carrierType;
    Map<TravelClass, SeatsInfo> mapOfTravelClassToSeatsInfo;
    Map<TravelClass,Float> mapOfClassToBasePrice;

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
            return mapOfTravelClassToSeatsInfo.get(classType).getNoOfAllocatedSeats();
        else
            return 0;

    }
    public Integer getAvailableSeatsForClass(TravelClass classType) {
        if (mapOfTravelClassToSeatsInfo.containsKey(classType))
            return mapOfTravelClassToSeatsInfo.get(classType).getNoOfSeatsAvlb();
        else
            return 0;

    }

    public Float getBasePriceForTraveClass(TravelClass classType) {
        if (mapOfTravelClassToSeatsInfo.containsKey(classType))
            return mapOfTravelClassToSeatsInfo.get(classType).getBasePricePerSeat();
        else
            return 0.0f;

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
