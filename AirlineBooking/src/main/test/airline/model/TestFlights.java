package airline.model; /**
 * Created by Gayathri on 04/09/17.
 *
 */

import airline.model.Flight;
import airline.model.SearchCriteria;
import airline.services.FlightServiceHandler;
import airline.repositories.FlightDataLoader;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestFlights {

    @Autowired
    private FlightServiceHandler testDataHandler;


    private SearchCriteria testSearchCriteria;

    @Before
    public void setUp() throws Exception {
        testDataHandler = new FlightServiceHandler();
        testDataHandler.populateFlightnCarrierLists();
        testSearchCriteria = new SearchCriteria("Src1", "Dest1",1);
    }

    @Test
    public void testIfAllFlightsByDest() {
        List<String> listAllDestLocations = testDataHandler.getAllDestLocations();
        assertEquals(3,listAllDestLocations.size());

    }

    @Test
    public void testIfAllFlightsBySrc() {
        List<String> listAllSrcLocations = testDataHandler.getAllSourceLocations();
        listAllSrcLocations.stream()
                .forEach(System.out::println);
        assertEquals(3,listAllSrcLocations.size());

    }

    @Test
    public void testIfSearchResultByValidSrcDst() {
        List<Flight> listAllMatchingFlights = testDataHandler.searchForFlights(testSearchCriteria);
        listAllMatchingFlights.stream()
                .forEach(System.out::println);
        assertEquals(2,listAllMatchingFlights.size());
    }

    @Test
    public void testIfSearchResultByInValidSrcDst() {
        testSearchCriteria.setSource("AAA");
        testSearchCriteria.setDestination("AAA");
        List<Flight> listAllMatchingFlights = testDataHandler.searchForFlights(testSearchCriteria);
        listAllMatchingFlights.stream()
                .forEach(System.out::println);
        assertEquals(0,listAllMatchingFlights.size());
    }

}

