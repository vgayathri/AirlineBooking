package airline.services;

import airline.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.time.LocalDate;
import java.util.HashMap;


import static airline.model.TravelClass.*;

/**
 * Created by Gayathri on 16/09/17.
 */
public class TravelClassPricingHandlerTest {

    private SeatsInfo testSeat1 = new SeatsInfo(10,0,1000.0f);
    private SeatsInfo testSeat2 = new SeatsInfo(50,5,10.0f);
    private SeatsInfo testSeat3 = new SeatsInfo(100,30,100.0f);
    public Map<TravelClass, SeatsInfo> travelClassToSeatMap = new HashMap<>(3);
    private Carrier testCarrier;
    private SearchCriteria criteria;
    private LocalDate today =  LocalDate.now();
    private Flight flight1;

    public void setTravelClassToSeatMap() {
        this.travelClassToSeatMap.put(TravelClass.BUSINESS,testSeat1);
        this.travelClassToSeatMap.put(TravelClass.FIRST,testSeat2);
        this.travelClassToSeatMap.put(TravelClass.ECONOMY,testSeat3);
    }

    public TravelClassPricingHandlerTest()
    {
        setTravelClassToSeatMap();
        testCarrier = new Carrier(CarrierType.BOEING777,this.travelClassToSeatMap );
        criteria = new SearchCriteria("to","from",1);
        flight1 = new Flight("FL", "from",
                "To",today,testCarrier);
    }

    @Test
    public void testEconomyNoPremiumRateForLessThanEq40Occupancy() {
        criteria.setTravelClass(TravelClass.ECONOMY);
        PricingStrategy ecoPriceHandler = new EconomyClassPricingHandler();
        float ecoBasePrice = flight1.getBasePriceForASeatInClass(ECONOMY);
        float ecoPriceFromHandler = ecoPriceHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue(ecoPriceFromHandler == ecoBasePrice);
    }

    @Test
    public void testEcoNoPremiumRateForLessThanEq40OccupancyNPassengers() {
        criteria.setTravelClass(TravelClass.ECONOMY);
        PricingStrategy ecoPriceHandler = new EconomyClassPricingHandler();
        float ecoBasePrice = flight1.getBasePriceForASeatInClass(ECONOMY);
        float ecoPriceFromHandler = ecoPriceHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue(ecoPriceFromHandler == ecoBasePrice);
    }

    @Test
    public void testEconomySlabRate1ForLessThan90Occupancy() {
        criteria.setTravelClass(TravelClass.ECONOMY);
        flight1.getCarrierDetails().setSeatInfoForTravelClass(ECONOMY,new SeatsInfo(100,50,1000.0f));
        EconomyClassPricingHandler ecoPriceHandler = new EconomyClassPricingHandler();
        float ecoBasePrice = flight1.getBasePriceForASeatInClass(ECONOMY);
        float ecoPriceFromHandler = ecoPriceHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue(ecoPriceFromHandler == (1.3f *ecoBasePrice));
    }

    @Test
    public void testEconomySlabRate2FoMoreThan90Occupancy() {
        criteria.setTravelClass(TravelClass.ECONOMY);
        flight1.getCarrierDetails().setSeatInfoForTravelClass(ECONOMY,
                new SeatsInfo(100,95,1000.0f));
        EconomyClassPricingHandler ecoPriceHandler = new EconomyClassPricingHandler();
        float ecoBasePrice = flight1.getBasePriceForASeatInClass(ECONOMY);
        float ecoPriceFromHandler = ecoPriceHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue(ecoPriceFromHandler == (1.6f * ecoBasePrice));

    }
    @Test
    public void testEconomyNoPremiumRateFoEq40Occupancy() {
        criteria.setTravelClass(TravelClass.ECONOMY);
        flight1.getCarrierDetails().setSeatInfoForTravelClass(ECONOMY,
                new SeatsInfo(100,40,1000.0f));
        EconomyClassPricingHandler ecoPriceHandler = new EconomyClassPricingHandler();
        float ecoBasePrice = flight1.getBasePriceForASeatInClass(ECONOMY);
        float ecoPriceFromHandler = ecoPriceHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue(ecoPriceFromHandler == ecoBasePrice);
    }

    @Test
    public void testEconomyNoPremiumRateForEq90Occupancy() {
        criteria.setTravelClass(TravelClass.ECONOMY);
        flight1.getCarrierDetails().setSeatInfoForTravelClass(ECONOMY,
                new SeatsInfo(100,90,1000.0f));
        EconomyClassPricingHandler ecoPriceHandler = new EconomyClassPricingHandler();
        float ecoBasePrice = flight1.getBasePriceForASeatInClass(ECONOMY);
        float ecoPriceFromHandler = ecoPriceHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue(ecoPriceFromHandler == (1.3 *ecoBasePrice));
    }

    @Test
    public void testBusinessClassNoMarkupForTuesDayTravel() {
        criteria.setTravelClass(TravelClass.BUSINESS);
        LocalDate tuesDate = LocalDate.of(2017,9, 19); // 19th sep is a tues
        flight1.setDepartureDate(tuesDate);
        BusinessClassPricingHandler businessClassPricingHandler = new BusinessClassPricingHandler();
        float busBasePrice = flight1.getBasePriceForASeatInClass(BUSINESS);
        float busPriceFromHandler = businessClassPricingHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue(busPriceFromHandler == busBasePrice);

    }
    @Test
    public void testBusinessClassNoMarkupForTueWedThurDayTravel() {
        criteria.setTravelClass(TravelClass.BUSINESS);
        LocalDate tueWedThusDate;
        // TEST for tues,wed and thur
        for (int date=19; date < 22; date ++) {
            tueWedThusDate = LocalDate.of(2017, 9, date);
            flight1.setDepartureDate(tueWedThusDate);
            BusinessClassPricingHandler businessClassPricingHandler = new BusinessClassPricingHandler();
            float busBasePrice = flight1.getBasePriceForASeatInClass(BUSINESS);
            float busPriceFromHandler = businessClassPricingHandler.calculatePrice(flight1, criteria);
            Assert.assertTrue(busPriceFromHandler == busBasePrice);
        }

    }

    @Test
    public void testBusinessClassNoMarkupForSatDayTravel() {
        criteria.setTravelClass(TravelClass.BUSINESS);
        LocalDate satDay = LocalDate.of(2017, 9, 23);
        flight1.setDepartureDate(satDay);
        BusinessClassPricingHandler businessClassPricingHandler = new BusinessClassPricingHandler();
        float busBasePrice = flight1.getBasePriceForASeatInClass(BUSINESS);
        float busPriceFromHandler = businessClassPricingHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue(busPriceFromHandler == busBasePrice);

    }

    @Test
    public void testBusinessClassMarkupForFriDayTravel() {
        criteria.setTravelClass(TravelClass.BUSINESS);
        LocalDate friDay = LocalDate.of(2017, 9, 22);
        flight1.setDepartureDate(friDay);
        BusinessClassPricingHandler businessClassPricingHandler = new BusinessClassPricingHandler();
        float busBasePrice = flight1.getBasePriceForASeatInClass(BUSINESS);
        float busPriceFromHandler = businessClassPricingHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue(busPriceFromHandler == (1.4f * busBasePrice));

    }
    @Test
    public void testBusinessClassMarkupForSunMonDayTravel() {
        criteria.setTravelClass(TravelClass.BUSINESS);
        LocalDate sunDay = LocalDate.of(2017, 9, 24);
        flight1.setDepartureDate(sunDay);
        BusinessClassPricingHandler businessClassPricingHandler = new BusinessClassPricingHandler();
        float busBasePrice = flight1.getBasePriceForASeatInClass(BUSINESS);
        float busPriceFromHandler = businessClassPricingHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue(busPriceFromHandler == (1.4 * busBasePrice));
        LocalDate monDay = LocalDate.of(2017, 9, 25);
        flight1.setDepartureDate(monDay);
        float busPriceFromHandler2 = businessClassPricingHandler.calculatePrice(flight1, criteria);
        Assert.assertEquals(busPriceFromHandler, busPriceFromHandler2,0.0);
    }

    @Test
    public void testFirstClassMarkupForDepartDayMoreThan10Days() {
        criteria.setTravelClass(TravelClass.FIRST);
        LocalDate beyondWindowDate = LocalDate.of(2017, 9, 27);
        flight1.setDepartureDate(beyondWindowDate);
        float baseFirstClassPrice = flight1.getBasePriceForASeatInClass(FIRST);
        PricingStrategy firstClassPriceHandler = new FirstClassPricingHandler();
        float markedUpFirsClassPrice = firstClassPriceHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue(-1.0 == markedUpFirsClassPrice);
        System.out.println(markedUpFirsClassPrice);
    }

    @Test
    public void testFirstClassMarkupForDepartEqToday() {
        criteria.setTravelClass(TravelClass.FIRST);
        LocalDate today = LocalDate.now();
        flight1.setDepartureDate(today);
        float baseFirstClassPrice = flight1.getBasePriceForASeatInClass(FIRST);
        PricingStrategy firstClassPriceHandler = new FirstClassPricingHandler();
        float markedUpFirsClassPrice = firstClassPriceHandler.calculatePrice(flight1, criteria);
        System.out.println(markedUpFirsClassPrice);
        Assert.assertTrue(baseFirstClassPrice * 2.0 == markedUpFirsClassPrice);
    }
    @Test
    public void testFirstClassMarkupForDepartEq10DaysFromNow() {
        criteria.setTravelClass(TravelClass.FIRST);
        LocalDate tenDaysFromtoday = LocalDate.now().plusDays(10);
        flight1.setDepartureDate(tenDaysFromtoday);
        float baseFirstClassPrice = flight1.getBasePriceForASeatInClass(FIRST);
        PricingStrategy firstClassPriceHandler = new FirstClassPricingHandler();
        float markedUpFirsClassPrice = firstClassPriceHandler.calculatePrice(flight1, criteria);
        System.out.println(markedUpFirsClassPrice);
        Assert.assertTrue(baseFirstClassPrice * 1.0 == markedUpFirsClassPrice);
    }

    @Test
    public void testMarkupForFirstClassMarkupDeparDate1to9() {
        criteria.setTravelClass(TravelClass.FIRST);
        LocalDate today = LocalDate.now();
        flight1.setDepartureDate(today);
        float baseFirstClassPrice = flight1.getBasePriceForASeatInClass(FIRST);
        PricingStrategy firstClassPriceHandler = new FirstClassPricingHandler();
        float rate = 1.1f;
        for (int index = 9; index > 0; index--, rate += 0.1f) {
            flight1.setDepartureDate(today.plusDays(index));
            float markedUpFirsClassPrice = firstClassPriceHandler.
                    calculatePrice(flight1, criteria);
            System.out.println(markedUpFirsClassPrice + " " + baseFirstClassPrice * (rate));
            Assert.assertEquals((baseFirstClassPrice * rate), markedUpFirsClassPrice, 0.001f);
        }
    }

    @Test
    public void testNegativePriceForInvalidClass() {
        criteria.setTravelClass(FIRST);
        float baseFirstClassPrice = flight1.getBasePriceForASeatInClass(FIRST);
        PricingStrategy ecoPriceHandler = new EconomyClassPricingHandler();
        float markedUpFirsClassPrice = ecoPriceHandler.calculatePrice(flight1, criteria);
        System.out.println(markedUpFirsClassPrice);
        Assert.assertTrue(-1.0f == markedUpFirsClassPrice);
    }
    @Test
    public void testPriceForNPassengersForEcoClass() {
        criteria.setTravelClass(ECONOMY);
        criteria.setNoOfPassengers(5);
        flight1.getCarrierDetails().setSeatInfoForTravelClass(ECONOMY,
                new SeatsInfo(100,40,1000f));
        float baseEcoClassPrice = flight1.getBasePriceForASeatInClass(ECONOMY);
        PricingStrategy ecoPriceHandler = new EconomyClassPricingHandler();
        float markedUpEcoPrice = ecoPriceHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue((baseEcoClassPrice * criteria.getNoOfPassengers()) == markedUpEcoPrice);
    }

    @Test
    public void testPriceForNPassengersForBusClass() {
        criteria.setTravelClass(BUSINESS);
        criteria.setNoOfPassengers(5);
        flight1.setDepartureDate(LocalDate.of(2017,9,16));
        float baseEcoClassPrice = flight1.getBasePriceForASeatInClass(BUSINESS);
        PricingStrategy busPriceHandler = new BusinessClassPricingHandler();
        float markedUpEcoPrice = busPriceHandler.calculatePrice(flight1, criteria);
        Assert.assertTrue((baseEcoClassPrice * criteria.getNoOfPassengers()) == markedUpEcoPrice);
    }

    @Test
    public void testPriceForNPassengersForFirstClass() {
        criteria.setTravelClass(FIRST);
        criteria.setNoOfPassengers(5);
        flight1.setDepartureDate(LocalDate.of(2017,9,16));
        float baseEcoClassPrice = flight1.getBasePriceForASeatInClass(FIRST);
        PricingStrategy busPriceHandler = new FirstClassPricingHandler();
        float markedUpEcoPrice = busPriceHandler.calculatePrice(flight1, criteria);
        Assert.assertEquals((baseEcoClassPrice * 2.0 * criteria.getNoOfPassengers()),markedUpEcoPrice, 0.001);
    }

}