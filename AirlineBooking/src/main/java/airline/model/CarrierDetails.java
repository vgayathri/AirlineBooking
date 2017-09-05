package airline.model;

import java.util.Base64;
import java.util.Map;

/**
 * Created by Gayathri on 05/09/17.
 */

public class CarrierDetails {

    CarrierType carrierType;
    Boolean hasEconomyClass;
    Boolean hasBusinessClass;
    Boolean hasFirstClass;
    Map<TravelClass, Integer> mapOfSeatsPerClass;

    public CarrierDetails(CarrierType carrierType, Boolean hasEconomy,
                          Boolean hasBusiness, Boolean hasFirstClass, Map<TravelClass, Integer> mapOfSeatsPerClass )
    {
        this.carrierType = carrierType;
        this.hasEconomyClass = hasEconomy;
        this.hasBusinessClass = hasBusiness;
        this.hasFirstClass = hasFirstClass;
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

    public boolean isHasEconomy() {
        return hasEconomyClass;
    }

    public void setHasEconomy(boolean hasEconomy) {
        this.hasEconomyClass = hasEconomy;
    }

    public boolean isHasBusiness() {
        return hasBusinessClass;
    }

    public void setHasBusiness(boolean hasBusiness) {
        this.hasBusinessClass = hasBusiness;
    }

    public boolean isHasFirstClass() {
        return hasFirstClass;
    }

    public void setHasFirstClass(boolean hasFirstClass) {
        this.hasFirstClass = hasFirstClass;
    }

    public Map<TravelClass, Integer> getMapOfSeatsPerClass() {
        return mapOfSeatsPerClass;
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

}
