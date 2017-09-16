package airline.model; /**
 * Created by Gayathri on 04/09/17.
 *
 */

import airline.services.FlightServiceHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestFlights {

    private Flight testFlight;
    private final SeatsInfo testSeats = new SeatsInfo(1, 1, 1000.0f);
    private Carrier testCarrier = new Carrier();
    private SearchCriteria testSearchCriteria = new SearchCriteria();
    @Autowired
    private FlightServiceHandler testServiceHandler;
    final LocalDate todaysDate = LocalDate.now();
    LocalDate anotherDate = todaysDate.plusDays(10);

    @Before
    public void setUp() throws Exception {
        testServiceHandler = new FlightServiceHandler();
        testServiceHandler.populateFlightnCarrierLists();
        testCarrier.setCarrierType(CarrierType.BOEING777);
        Map<TravelClass, SeatsInfo> testMap = new HashMap<TravelClass, SeatsInfo>(1);
        testMap.put(TravelClass.ECONOMY, testSeats);
        testCarrier.setMapOfSeatsPerClass(testMap);
        testFlight = new Flight("FL101", "Source1", "Dest1", todaysDate);
        testFlight.setCarrierDetails(testCarrier);
    }

    @Test
    public void testFlightHasSrc() {
        Assert.assertNotNull(testFlight.getSource());
    }

    @Test
    public void testFlightHasDestination() {
        Assert.assertNotNull(testFlight.getDestination());
    }

    @Test
    public void testFlightHasCarrier() {
        Assert.assertNotNull(testFlight.getCarrierDetails());
    }

    @Test
    public void testFlightHasDepartureDate() {
        Assert.assertNotNull(testFlight.getDepartureDate());
    }

    @Test
    public void testFlightMatchesARoute() {
        Assert.assertTrue(testFlight.isFlightBetweenSrcAndDestination("Source1", "dest1"));
    }

    @Test
    public void testFlightDoesNotMatchesARoute() {
        Assert.assertFalse(testFlight.isFlightBetweenSrcAndDestination("S1", "d1"));
    }

    @Test
    public void testFlightSearchIsCaseLess() {
        Assert.assertTrue(testFlight.isFlightBetweenSrcAndDestination("SOURCE1", "DEST1"));
    }

    @Test
    public void testFlightSearchMatchesDepartDate() {
        Assert.assertTrue(testFlight.isDepartDateEquals(todaysDate));
    }

    @Test
    public void testFlightSearchDoesMatchesDepartDate() {
        Assert.assertFalse(testFlight.isDepartDateEquals(anotherDate));
    }

    @Test
    public void testFlightSearchDoesMatcheEmptyDepartDate() {
        Assert.assertFalse(testFlight.isDepartDateEquals(null));
    }

    @Test
    public void testFlightHasANotNullCarrier() {
        Assert.assertNotNull(testFlight.getCarrierDetails());
    }

    @Test
    public void testFlightHasAtleastOneTravelClass() {
        Assert.assertNotNull(testFlight.getCarrierDetails().
                getMapOfClassToSeats().size() != 0);
    }

    @Test
    public void testFlightHasAValidCarrier() {
        Assert.assertTrue(testFlight.getCarrierDetails().
                isCarrierTypeEquals(CarrierType.BOEING777));
    }

    @Test
    public void testFlightHasAValidTravelClass() {
        Assert.assertTrue(testFlight.getCarrierDetails().
                isCarrierTypeEquals(CarrierType.BOEING777));
    }

    @Test
    public void testFlightHasSeats() {
        TravelClass travelClass = TravelClass.ECONOMY;
        Assert.assertTrue(testFlight.getTotalNoOfSeatsInTravelClass(travelClass) != 0);
    }

    @Test
    public void testIfAllBySrcAreReturned() {

        List<String> listAllSrcLocations = testServiceHandler.getAllSourceLocations();
        listAllSrcLocations.stream()
                .forEach(System.out::println);
        assertEquals(5, listAllSrcLocations.size());

    }

    @Test
    public void testIfAllDestinationAreReturned() {
        List<String> listAllDstLocations = testServiceHandler.getAllDestLocations();
        listAllDstLocations.stream()
                .forEach(System.out::println);
        assertEquals(5, listAllDstLocations.size());
    }

    @Test
    public void testIfSearchResultBySrcButNoDst() {
        testSearchCriteria.setSource("Source1");
        List<Flight> listAllMatchingFlights = testServiceHandler.
                searchForFlights(testSearchCriteria);
        listAllMatchingFlights.stream()
                .forEach(System.out::println);
        assertEquals(2, listAllMatchingFlights.size());
    }

    @Test
    public void testIfSearchReturnsForValidSrcDst() {
        testSearchCriteria.setSource("Source1");
        testSearchCriteria.setDestination("Dest1");
        List<Flight> listAllMatchingFlights = testServiceHandler.
                searchForFlights(testSearchCriteria);
        listAllMatchingFlights.stream()
                .forEach(System.out::println);
        Assert.assertTrue(listAllMatchingFlights.size() != 0);
    }

    @Test
    public void testIfSearchReturnsForValidSrcDstIgnoreCase() {
        testSearchCriteria.setSource("SOuRCe1");
        testSearchCriteria.setDestination("DeST1");
        List<Flight> listAllMatchingFlights = testServiceHandler.
                searchForFlights(testSearchCriteria);
        listAllMatchingFlights.stream()
                .forEach(System.out::println);
        Assert.assertTrue(listAllMatchingFlights.size() != 0);
    }

    @Test
    public void testIfSearchReturnsForValidSrcDstAndDate() {
        testSearchCriteria.setSource("Source1");
        testSearchCriteria.setDestination("Dest1");
        testSearchCriteria.setDepartureDate(todaysDate);
        List<Flight> listAllMatchingFlights = testServiceHandler.
                searchForFlights(testSearchCriteria);
        listAllMatchingFlights.stream()
                .forEach(System.out::println);
        Assert.assertTrue(listAllMatchingFlights.size() != 0);
    }

    @Test
    public void testIfSearchReturnsNoResultsForValidSrcDstAndInvalidDate() {
        testSearchCriteria.setSource("Source1");
        testSearchCriteria.setDestination("Dest1");
        testSearchCriteria.setDepartureDate(anotherDate);
        List<Flight> listAllMatchingFlights = testServiceHandler.
                searchForFlights(testSearchCriteria);
        listAllMatchingFlights.stream()
                .forEach(System.out::println);
        Assert.assertTrue(listAllMatchingFlights.size() == 0);
    }

    @Test
    public void testIfSearchReturnsNoResultsForNoOfPassengersNClass() {
        testSearchCriteria.setSource("Source1");
        testSearchCriteria.setDestination("Dest1");
        testSearchCriteria.setNoOfPassengers(5);
        testSearchCriteria.setTravelClass(TravelClass.FIRST);
        List<Flight> listAllMatchingFlights = testServiceHandler.
                searchForFlights(testSearchCriteria);
        listAllMatchingFlights.stream()
                .forEach(System.out::println);
        Assert.assertTrue(listAllMatchingFlights.size() == 1);
    }

    @Test
    public void testIfSearchReturnsNoPassengersWhenAllSeatsTaken() {
        testSearchCriteria.setSource("Source1");
        testSearchCriteria.setDestination("Dest1");
        testSearchCriteria.setTravelClass(TravelClass.FIRST);
        testSearchCriteria.setNoOfPassengers(20);
        List<Flight> listAllMatchingFlights = testServiceHandler.
                searchForFlights(testSearchCriteria);
        assertEquals(0, listAllMatchingFlights.size());
    }

    @Test
    public void testIfSearchReturnsPriceForNPassengersOfEcoClass() {
        testSearchCriteria.setSource("Source1");
        testSearchCriteria.setDestination("Dest1");
        testSearchCriteria.setNoOfPassengers(8);
        testSearchCriteria.setTravelClass(TravelClass.FIRST);
        List<Flight> listAllMatchingFlights = testServiceHandler.
                searchForFlights(testSearchCriteria);
        Map<String, Float> mapOfPricesToFlightIds = testServiceHandler.calculateBasePriceForSeatsForFlightList(listAllMatchingFlights, testSearchCriteria);
        assertNotNull(listAllMatchingFlights);
        Assert.assertTrue(listAllMatchingFlights.size() == mapOfPricesToFlightIds.size());
        Assert.assertEquals(mapOfPricesToFlightIds.keySet(), listAllMatchingFlights.stream()
                .map(flight -> flight.getFlightID())
                .sorted()
                .collect(Collectors.toSet()));
    }
}