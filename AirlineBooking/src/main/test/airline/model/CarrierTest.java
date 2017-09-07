package airline.models;

import airline.model.Carrier;
import airline.model.CarrierType;
import airline.model.TravelClass;
import airline.repositories.CarrierDataLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class CarrierTest {

    List<Carrier> testCarriers = new ArrayList<Carrier>();
    Carrier testCarrier;

    public Carrier getTestCarrier(CarrierType testCarrierType) {

        for (Carrier carrier : testCarriers
                ) {
            if (carrier.isCarrierTypeEquals(testCarrierType))
                return carrier;
        }
        return null;
    }

    @Before
    public void setUp() throws Exception {
        testCarriers = new CarrierDataLoader("CarrierDetails.txt").getCarrierList();
        System.out.println(testCarriers.toString());
    }

    @Test
    public void testValidityOfAirBus321() {
        testCarrier = getTestCarrier(CarrierType.AIRBUS321);
        boolean validCarrierType = testCarrier.isCarrierTypeEquals(CarrierType.AIRBUS321);
        Assert.assertEquals(true, validCarrierType);
    }

    @Test
    public void testValidityOfAirBus319V2() {
        testCarrier = getTestCarrier(CarrierType.AIRBUS319V2);
        boolean validCarrierType = testCarrier.isCarrierTypeEquals(CarrierType.AIRBUS319V2);
        Assert.assertEquals(true, validCarrierType);
    }

    @Test
    public void testValidityOfBoeing777() {
        testCarrier = getTestCarrier(CarrierType.BOEING777);
        boolean validCarrierType = testCarrier.isCarrierTypeEquals(CarrierType.BOEING777);
        Assert.assertEquals(true, validCarrierType);
    }

    @Test
    public void testNotValidCarrierType() {
        testCarrier = getTestCarrier(CarrierType.BOEING777);
        if (testCarrier != null)
            System.out.print("Did not get an object");
        boolean validCarrierType = testCarrier.isCarrierTypeEquals(CarrierType.AIRBUS321);
        Assert.assertEquals(false, validCarrierType);
    }

    @Test
    public void testBoeingHasSeatsInEconomy() {
        Carrier boeingCarrier = getTestCarrier(CarrierType.BOEING777);
        boolean hasFirstClass = boeingCarrier.doesCarrierHaveClass(TravelClass.FIRST);
        Assert.assertEquals(true, hasFirstClass);
    }

    @Test
    public void testAirBus319V2HasOnlyEconomyClass() {
        Carrier testCarrier = getTestCarrier(CarrierType.AIRBUS319V2);
        boolean hasFirstClass = testCarrier.doesCarrierHaveClass(TravelClass.FIRST);
        boolean hasBusinessClass = testCarrier.doesCarrierHaveClass(TravelClass.BUSINESS);
        Assert.assertEquals(false, hasFirstClass || hasBusinessClass);
    }

    @Test
    public void testAirBus321HasNoFirstClass() {
        Carrier testCarrier = getTestCarrier(CarrierType.AIRBUS321);
        boolean hasFirstClass = testCarrier.doesCarrierHaveClass(TravelClass.FIRST);
        Assert.assertFalse(hasFirstClass);
    }

    @Test
    public void testAirBus321HasEconomyAndBusinessClass() {
        Carrier testCarrier = getTestCarrier(CarrierType.AIRBUS321);
        boolean hasBusinessClass = testCarrier.doesCarrierHaveClass(TravelClass.BUSINESS);
        boolean hasEconomyClass = testCarrier.doesCarrierHaveClass(TravelClass.ECONOMY);

        Assert.assertTrue(hasBusinessClass && hasEconomyClass);
    }
}