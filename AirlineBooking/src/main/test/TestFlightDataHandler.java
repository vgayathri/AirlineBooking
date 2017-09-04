/**
 * Created by Gayathri on 04/09/17.
 *
 */

package airline;

import airline.model.Flight;
import airline.services.*;
import airline.util.*;
import airline.model.SearchCriteria;


import org.junit.*;

import java.util.List;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;


public class TestFlightDataHandler {

    private FlightDataHandler testDataHandler;
    private SearchCriteria testSearchCriteria;

    @Before
    protected void setUp() throws Exception {
        testDataHandler = new FlightDataHandler(FlightDataLoader.populateFromCSVFile("TestFlightDetails.txt"));
        testSearchCriteria = new SearchCriteria("Chennai", "Hyderabad");
    }

    @Test
    public void testIfAllFlightsByDest() {
        List<String> listAllDestLocations = testDataHandler.getAllDestLocations();
        assertEquals(4,listAllDestLocations.size());

    }

    @Test
    public void testIfAllFlightsBySrc() {
        List<String> listAllSrcLocations = testDataHandler.getAllSrcLocations();
        listAllSrcLocations.stream()
                .forEach(System.out::println);
        assertEquals(4,listAllSrcLocations.size());

    }

    @Test
    public void testIfSearchResultByValidSrcDst() {
        List<Flight> listAllMatchingFlights = testDataHandler.getBySrcAndDestn(testSearchCriteria);
        listAllMatchingFlights.stream()
                .forEach(System.out::println);
        assertEquals(1,listAllMatchingFlights.size());
    }

    @Test
    public void testIfSearchResultByInValidSrcDst() {
        testSearchCriteria.setSource("AAA");
        testSearchCriteria.setDestination("AAA");
        List<Flight> listAllMatchingFlights = testDataHandler.getBySrcAndDestn(testSearchCriteria);
        listAllMatchingFlights.stream()
                .forEach(System.out::println);
        assertEquals(0,listAllMatchingFlights.size());
    }

    @Test
    public void testSearchResultBySrcDestPassengerSeatAvlbIsYes() {
        testSearchCriteria.setSource("Chennai");
        testSearchCriteria.setDestination("Hyderabad");
        testSearchCriteria.setNoOfPassengers(2);
        List<Flight> listAllMatchingFlights = testDataHandler.getBySrcAndDestnAndPassengerCount(testSearchCriteria);
        assertEquals(1,listAllMatchingFlights.size());
    }

    @Test
    public void testSearchResultBySrcDestPassengerSeatAvlbNo() {
        testSearchCriteria.setSource("Leh");
        testSearchCriteria.setDestination("Chennai");
        testSearchCriteria.setNoOfPassengers(2);
        List<Flight> listAllMatchingFlights = testDataHandler.getBySrcAndDestnAndPassengerCount(testSearchCriteria);

        assertEquals(0,listAllMatchingFlights.size());
    }





}

