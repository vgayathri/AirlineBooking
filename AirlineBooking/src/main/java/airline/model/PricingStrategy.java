package airline.model;

/**
 * Created by Gayathri on 16/09/17.
 */
public interface PricingStrategy {

    float calculatePrice(Flight flightObj, SearchCriteria criteria);
}
