package airline.Controller;

import airline.model.CarrierType;
import airline.model.Flight;
import airline.model.SearchCriteria;
import airline.model.TravelClass;
import airline.services.FlightServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import airline.repositories.*;

/**
 * Modifed by Gayathri on 8/8/17.
 */
@Controller
@SpringBootApplication
public  class FlightController {

    String flightInfoFileName="FlightDetails.txt";

    @Autowired
    protected FlightServiceHandler flightServiceHandler;

    @RequestMapping(value="/")
    public String welcomeMessage(Model newModel) {
        List <String> srcCities = flightServiceHandler.getAllSourceLocations();
        List <String> dstCities = flightServiceHandler.getAllDestLocations();
        newModel.addAttribute("Sources", srcCities);
        newModel.addAttribute("Destinations",dstCities);
        newModel.addAttribute("searchObj", new SearchCriteria());
        newModel.addAttribute("TravelClass", TravelClass.values());
        newModel.addAttribute("CarrierType", CarrierType.values());

        return "flightSearch";
    }

    @PostMapping(value="/searchFlight")
    @DateTimeFormat(pattern="d/MM/yyyy")
    public String searchByPassengerSubmit(@ModelAttribute(value="searchObj")SearchCriteria searchCriteria, BindingResult bindingResult, Model model) {

        boolean showResults = true;
        List<Flight> resultSet = flightServiceHandler.searchForFlights(searchCriteria);
        model.addAttribute("flights",resultSet);
        List <Float> priceList = flightServiceHandler.calculateBasePriceForSeatsForFlightList(resultSet,searchCriteria);
        model.addAttribute("prices",priceList);
        model.addAttribute("showResults",showResults);
        return "flightSearch";
    }
}