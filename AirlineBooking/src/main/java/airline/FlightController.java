package airline;

import airline.model.Flight;
import airline.model.SearchFlds;
import airline.services.FlightData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import airline.util.*;

/**
 * Created by rajashrk on 8/8/17.
 */
@Controller
@SpringBootApplication
public  class FlightController {

    String flightInfoFileName="/Users/Gayathri/Vapasi/testAirline/AirlineBooking/src/main/resources/FlightDetails.txt";
    private FlightData flightData = new FlightData(FlightUtils.populateFromCSVFile(flightInfoFileName));


    public static void main(String []args) {
        SpringApplication.run(FlightController.class,args);

    }

    @RequestMapping(value="/")
    public String welcomeMessage(Model newModel) {
        List <String> srcCities = flightData.getAllSourceLocations();
        List <String> dstCities = flightData.getAllDestLocations();
        newModel.addAttribute("Sources", srcCities);
        newModel.addAttribute("Destinations",dstCities);
        newModel.addAttribute("searchObj", new SearchFlds());
        return "flightSearch";
    }

/*
    @PostMapping(value="/searchFlight")
    public String searchSubmit(@RequestParam(value = "srcOrder", required = true)String src,@RequestParam(value = "dstOrder", required = true)String dst, Model model) {
        //List<Flight> resultSet = flightData.getBySrcAndDestn(sourceCity,"Hyderabad");
        List<Flight> resultSet = flightData.getBySrcAndDestn(src, dst);
        model.addAttribute("flights",resultSet);
        return "SearchResult";
    }
*/
    @PostMapping(value="/searchFlight")
    public String searchSubmit(@ModelAttribute(value="searchObj")SearchFlds searchFlds, Model model) {
        //List<Flight> resultSet = flightData.getBySrcAndDestn(sourceCity,"Hyderabad");
        System.out.print(searchFlds.getDestination());
        List<Flight> resultSet = flightData.getBySrcAndDestn(searchFlds.getSource(), searchFlds.getDestination());
        model.addAttribute("flights",resultSet);
        return "SearchResult";
    }
}