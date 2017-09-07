package airline.model;

import java.util.Base64;
import java.util.Map;

/**
 * Created by Gayathri on 05/09/17.
 */


public class Carrier {

    CarrierType carrierType;
    Map<TravelClass, Integer> mapOfSeatsPerClass;
    Map<TravelClass,Float> mapOfBasePriceToClass;

    public Carrier(CarrierType carrierType,
                   Map<TravelClass, Integer> mapOfSeatsPerClass )
    {
        this.carrierType = carrierType;
        this.mapOfSeatsPerClass = mapOfSeatsPerClass;
    }

    public Map<TravelClass,Integer> getMapOfClassToSeats() {
        return mapOfSeatsPerClass;
    }

    public Integer getSeatsForClass(TravelClass classType) {
        if (mapOfSeatsPerClass.containsKey(classType))
            return mapOfSeatsPerClass.get(classType);
        else
            return 0;

    }

    public void printSeatsForAllClasses() {
        mapOfSeatsPerClass.forEach((k,v)->System.out.println("Class : " + k + " Seats : " + v));

    }

    public CarrierType getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(CarrierType carrierType) {
        this.carrierType = carrierType;
    }

    public boolean doesCarrierHaveClass(TravelClass travelClass) {
        return mapOfSeatsPerClass.containsKey(travelClass);
    }

    public void setMapOfSeatsPerClass(Map<TravelClass, Integer> mapOfSeatsPerClass) {
        this.mapOfSeatsPerClass = mapOfSeatsPerClass;
    }
    public Integer getNoOfSeatsPerClassForCarrier(CarrierType typeOfCarrier,
                                                  TravelClass travelClass) {
        if (carrierType == typeOfCarrier) {
            return mapOfSeatsPerClass.get(travelClass);
        }
        return 0;
    }

    public boolean isCarrierTypeEquals(CarrierType carrierType) {
        if (getCarrierType().equals(carrierType))
            return true;
        else
            return false;
    }

}
