package airline.models;

import airline.model.Carrier;
import airline.model.CarrierType;
import airline.model.SeatsInfo;
import airline.model.TravelClass;
import airline.repositories.CarrierDataLoader;
import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CarrierTest {

    List<Carrier> testCarriers = new ArrayList<Carrier>();
    Carrier testCarrier;
    SeatsInfo testSeatInfo = new SeatsInfo(100,2000.0f);
    LocalDate today =  LocalDate.now();
    LocalDate date1 = today.plusDays(1);
    LocalDate date2 = today.minusDays(-1);


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
    @Test
    public void testAirBus321HasBasePriceForEconomySeat() {
        Carrier testCarrier = getTestCarrier(CarrierType.AIRBUS321);
        float basePriceForEconomy = testCarrier.getBasePriceForTraveClass(TravelClass.ECONOMY,date1);
         Assert.assertEquals(5000.0f,basePriceForEconomy,0.0);
    }
    @Test
    public void testAirBus321HasBasePriceForBusinessSeat() {
        Carrier testCarrier = getTestCarrier(CarrierType.AIRBUS321);
        float basePriceForBusiness = testCarrier.getBasePriceForTraveClass(TravelClass.BUSINESS,date2);
        Assert.assertEquals(10000.0f,basePriceForBusiness,0.0);
    }
    @Test
    public void testAirBus321BasePriceForBusinessSeatMoreThanForExconomySeat() {
        Carrier testCarrier = getTestCarrier(CarrierType.AIRBUS321);
        float basePriceForBusiness = testCarrier.getBasePriceForTraveClass(TravelClass.BUSINESS,date1);
        float basePriceForEconomy = testCarrier.getBasePriceForTraveClass(TravelClass.ECONOMY,date1);
        Assert.assertTrue(basePriceForBusiness>basePriceForEconomy);
    }

    @Test
    public void testAirBus321DoesNotHaveBasePriceForFirstSeat()
    {
        Carrier testCarrier = getTestCarrier(CarrierType.AIRBUS321);
        float basePriceForFirst = testCarrier.getBasePriceForTraveClass(TravelClass.FIRST, date1);
        Assert.assertEquals(0,basePriceForFirst,0.0);
    }
    @Test
    public void testAirBus19HasBasePriceForEconomySeat() {
        Carrier testCarrier = getTestCarrier(CarrierType.AIRBUS319V2);
        float basePriceForEconomy = testCarrier.getBasePriceForTraveClass(TravelClass.ECONOMY,date2);
        Assert.assertEquals(4000.0f,basePriceForEconomy,0.0);
    }
    @Test
    public void testAirBus319HaNosBasePriceForBusinessSeat() {
        Carrier testCarrier = getTestCarrier(CarrierType.AIRBUS319V2);
        float basePriceForBusiness = testCarrier.getBasePriceForTraveClass(TravelClass.BUSINESS,date1);
        Assert.assertEquals(0.0f,basePriceForBusiness,0.0);
    }
    @Test
    public void testAirBus319DoesNotHaveBasePriceForFirstSeat()
    {
        Carrier testCarrier = getTestCarrier(CarrierType.AIRBUS319V2);
        float basePriceForFirst = testCarrier.getBasePriceForTraveClass(TravelClass.FIRST,today);
        Assert.assertEquals(0,basePriceForFirst,0.0);
    }
    @Test
    public void testBoeingHasBasePriceForEconomySeat() {
        Carrier testCarrier = getTestCarrier(CarrierType.BOEING777);
        float basePriceForEconomy = testCarrier.getBasePriceForTraveClass(TravelClass.ECONOMY,date1);
        Assert.assertEquals(6000.0f,basePriceForEconomy,0.0);
    }
    @Test
    public void testBoeingHaNosBasePriceForBusinessSeat() {
        Carrier testCarrier = getTestCarrier(CarrierType.BOEING777);
        float basePriceForBusiness = testCarrier.getBasePriceForTraveClass(TravelClass.BUSINESS,today);
        Assert.assertEquals(13000.0f,basePriceForBusiness,0.0);
    }
    @Test
    public void testBoeingHasBasePriceForFirstSeat()
    {
        Carrier testCarrier = getTestCarrier(CarrierType.BOEING777);
        float basePriceForFirst = testCarrier.getBasePriceForTraveClass(TravelClass.FIRST,date2);
        Assert.assertEquals(20000.0f,basePriceForFirst,0.0);
    }
    @Test
    public void testBoeingHasBasePricesForHigherClassMoreThanLowerClass()
    {
        Carrier testCarrier = getTestCarrier(CarrierType.BOEING777);
        float basePriceForFirst = testCarrier.getBasePriceForTraveClass(TravelClass.FIRST,date1);
        float basePriceForEconomy = testCarrier.getBasePriceForTraveClass(TravelClass.ECONOMY,date2);
        float basePriceForBusiness = testCarrier.getBasePriceForTraveClass(TravelClass.BUSINESS,today);

        Assert.assertTrue(basePriceForFirst > basePriceForBusiness && basePriceForBusiness > basePriceForEconomy);
    }

    @Test
    public void testSeatsReturnsAllocatedSeats()
    {
        int noOfAllocatedSeats = testSeatInfo.getTotalNoOfSeats();
        Assert.assertTrue(noOfAllocatedSeats > 0);
    }


    @Test
    public void testSeatsReturnsBookedSeats()
    {
        testSeatInfo.setNoOfSeatsBooked(100);
        int noOfSeatsBooked = testSeatInfo.getNoOfSeatsBooked();
        Assert.assertTrue(noOfSeatsBooked == 100);
    }

    @Test
    public void testSeatsReturnsAvailableSeats()
    {
        int noOfAllocatedSeats = testSeatInfo.getTotalNoOfSeats();
        int noOfSeatsBooked = testSeatInfo.getNoOfSeatsBooked();
        int noOfAvlbSeats = testSeatInfo.getNoOfSeatsAvlb();
        Assert.assertEquals(noOfAllocatedSeats-noOfSeatsBooked, noOfAvlbSeats);
    }
    @Test
    public void testAmountForMoreThanOneSeat()
    {
        float basePriceOfSeat = testSeatInfo.getBasePricePerSeat();
        int noOfSeats = 10;
        float calculatedPrice = testSeatInfo.calculatePriceofSeats(10);
        Assert.assertTrue(basePriceOfSeat*10==calculatedPrice);
    }

}