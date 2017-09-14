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
import java.util.Map;

import airline.repositories.*;

import javax.annotation.PostConstruct;

/**
 * Modifed by Gayathri on 8/8/17.
 */
@Controller
@SpringBootApplication
public  class FlightController {

    @Autowired
    protected FlightServiceHandler flightServiceHandler;

    List <String> srcCities = new ArrayList<>();
    List <String> dstCities = new ArrayList<>();

    @PostConstruct
    public void postContructor() {
        srcCities = flightServiceHandler.getAllSourceLocations();
        dstCities = flightServiceHandler.getAllDestLocations();
    }

    @RequestMapping(value="/")
    public String welcomeMessage(Model newModel) {

        newModel.addAttribute("Sources", srcCities);
        newModel.addAttribute("Destinations",dstCities);
        newModel.addAttribute("TravelClass", TravelClass.values());
        newModel.addAttribute("searchObj", new SearchCriteria());

        return "flightSearch";
    }

    @PostMapping(value="/searchFlight")
    @DateTimeFormat(pattern="d/MM/yyyy")
    public String searchByPassengerSubmit(@ModelAttribute(value="searchObj")SearchCriteria searchCriteria, BindingResult bindingResult, Model model) {
        model.addAttribute("Sources", srcCities);
        model.addAttribute("Destinations",dstCities);
        model.addAttribute("TravelClass", TravelClass.values());
        boolean showResults = true;
        List<Flight> resultSet = flightServiceHandler.searchForFlights(searchCriteria);
        model.addAttribute("flights",resultSet);
        Map<String, Float> flightToPriceMap = flightServiceHandler.calculateBasePriceForSeatsForFlightList(resultSet,searchCriteria);
        model.addAttribute("prices",flightToPriceMap);
        model.addAttribute("showResults",showResults);
        return "flightSearch";
    }
}