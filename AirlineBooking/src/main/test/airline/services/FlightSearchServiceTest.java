package airline.services;

import airline.model.*;
import airline.repositories.CarrierDataLoader;
import airline.repositories.FlightDataLoader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static airline.model.TravelClass.ECONOMY;
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

    Flight mockFlight = new Flight("123", "src", "dest", LocalDate.now(), new Carrier());
    Flight mockFlight2 = new Flight("124", "src", "dest",LocalDate.now().plusDays(1), new Carrier());
    Flight mockFlight3 = new Flight("125", "src", "dest",LocalDate.now().plusDays(2), new Carrier());

    private List<Flight> listOfMockFlights = new ArrayList<>(Arrays.asList(mockFlight,mockFlight2,mockFlight3));

    @Test
    public void testGetFlightsBetweenCities() {

        Mockito.when(flightRepository.getFlightList())
                .thenReturn(listOfMockFlights);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setSource("Source1");
        searchCriteria.setDestination("dest1");
        searchCriteria.setTravelClass(ECONOMY);
        List<Flight> listOfFlights = new ArrayList<Flight>();

        try {
            listOfFlights = flightSearchService.searchForFlights(searchCriteria);

        }
        catch (Exception e) {
            e.printStackTrace();
            return ;
        }
        Predicate<Flight> predicate1 = flight -> flight.getSource().equalsIgnoreCase("Source1");
        Predicate<Flight> predicate2 = flight -> flight.getDestination().equalsIgnoreCase("Dest1");

        boolean srcMatch = listOfFlights.stream().allMatch(predicate1);
        boolean dstMatch = listOfFlights.stream().allMatch(predicate2);
        Assert.assertTrue(srcMatch && dstMatch);
        Assert.assertTrue(listOfFlights.size() > 0);
    }
}
