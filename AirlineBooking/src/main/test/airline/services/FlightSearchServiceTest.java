package airline.services;

import airline.model.Flight;
import airline.model.SearchCriteria;
import airline.model.TravelClass;
import airline.repositories.CarrierDataLoader;
import airline.repositories.FlightDataLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FlightServiceHandler.class)
public class FlightSearchServiceTest {


    @MockBean
    private FlightDataLoader flightRepository;
    @MockBean
    private CarrierDataLoader carrierRepository;


    @Autowired(required = true)
    private FlightServiceHandler flightSearchService;

    Flight mockFlight = new Flight("123", "src", "dest");
    Flight mockFlight2 = new Flight("124", "src", "dest");
    Flight mockFlight3 = new Flight("125", "src", "dest");


    private List<Flight> listOfMockFlights = new ArrayList<>(Arrays.asList(mockFlight,mockFlight2,mockFlight3));

    @Test
    public void testGetFlightsBetweenCities() {

        Mockito.when(flightRepository.getFlightList())
                .thenReturn(listOfMockFlights);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setSource("src");
        searchCriteria.setDestination("dst");

        List<Flight> listOfFlights = new ArrayList<Flight>();

        try {
            listOfFlights = flightSearchService.searchForFlights(searchCriteria);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ;
        }
        listOfFlights.stream()
                .forEach(System.out::print);
        assertTrue(listOfFlights.size() == 0);
    }
}
