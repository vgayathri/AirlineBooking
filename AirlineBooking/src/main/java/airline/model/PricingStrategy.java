package airline.model;

/**
 * Created by Gayathri on 16/09/17.
 */
public interface PricingStrategy {

    default Float getPriceForASeat(Flight flightObj, SearchCriteria criteria) {
        return null;
    }
}
