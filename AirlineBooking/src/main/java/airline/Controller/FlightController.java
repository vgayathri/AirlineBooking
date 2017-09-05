package airline.Controller;

import airline.model.CarrierType;
import airline.model.Flight;
import airline.model.SearchCriteria;
import airline.model.TravelClass;
import airline.services.FlightDataHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import airline.repositories.*;

/**
 * Modifed by Gayathri on 8/8/17.
 */
@Controller
@SpringBootApplication
public  class FlightController {

    String flightInfoFileName="FlightDetails.txt";
    private FlightDataHandler flightDataHandler = new FlightDataHandler(FlightDataLoader.populateFromCSVFile(flightInfoFileName));

    public static void main(String []args) {
        SpringApplication.run(FlightController.class,args);

    }

    @RequestMapping(value="/")
    public String welcomeMessage(Model newModel) {
        List <String> srcCities = flightDataHandler.getAllSourceLocations();
        List <String> dstCities = flightDataHandler.getAllDestLocations();
        newModel.addAttribute("Sources", srcCities);
        newModel.addAttribute("Destinations",dstCities);
        newModel.addAttribute("searchObj", new SearchCriteria());
        newModel.addAttribute("TravelClass", TravelClass.values());
        newModel.addAttribute("CarrierType", CarrierType.values());

        return "flightSearch";
    }

    @PostMapping(value="/searchFlight")
    public String searchSubmit(@ModelAttribute(value="searchObj")SearchCriteria searchCriteria, Model model) {
        List<Flight> resultSet = flightDataHandler.getBySrcAndDestn(searchCriteria);
        model.addAttribute("flights",resultSet);
        return "SearchResult";
    }


    @PostMapping(value="/searchFlightByPassengers")
    public String searchByPassengerSubmit(@ModelAttribute(value="searchObj")SearchCriteria searchCriteria, BindingResult bindingResult, Model model) {

        try {
            System.out.println(bindingResult.getModel().toString());
            System.out.println(searchCriteria.toString());
           if (searchCriteria.getNoOfPassengers() == 0)
               searchCriteria.setNoOfPassengers(1);
           } catch (NullPointerException e) {
            searchCriteria.setNoOfPassengers(1);
        }
        System.out.println("Search criteria " + searchCriteria.toString());

        List<Flight> resultSet = flightDataHandler.getBySrcAndDestnAndPassengerCount(searchCriteria);
        model.addAttribute("flights",resultSet);
        return "SearchResult";
    }
}