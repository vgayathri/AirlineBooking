package airline.services;

import airline.model.Carrier;
import airline.model.Flight;
import airline.model.SearchCriteria;
import airline.model.TravelClass;
import airline.repositories.CarrierDataLoader;
import airline.repositories.FlightDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * Created by Gayathri on 30/08/17.
 */
@Service
public class FlightServiceHandler {

    private List<Flight> listOfAllFlights = new ArrayList<Flight>();
    private List<Carrier> listOfAllCarriers = new ArrayList<Carrier>();
    @Autowired
    private CarrierDataLoader carrierListLoader;
    @Autowired
    private FlightDataLoader flightListLoader;

    /*Loads from HardCoded list*/
    public FlightServiceHandler() {
        carrierListLoader =  new CarrierDataLoader();
        flightListLoader =  new FlightDataLoader();
        populateFlightnCarrierLists();
    }

    /**
     * Reads from CSV files, files names passed as
     * @param carrierDataFileName
     * @param flightDataFileName
     */
    public FlightServiceHandler(String carrierDataFileName, String flightDataFileName) {
        carrierListLoader =  new CarrierDataLoader(carrierDataFileName);
        flightListLoader  =  new FlightDataLoader(flightDataFileName);
        populateFlightnCarrierLists();
    }

    private void setCarrierInfoForAllFlight() {
        int carrierListSize = listOfAllCarriers.size();
        for(int index=0;index <listOfAllFlights.size();index++) {
            Carrier carrierToSet = listOfAllCarriers.get(index%carrierListSize);
            listOfAllFlights.get(index).setCarrierDetails(carrierToSet);
        }
    }

    public void populateFlightnCarrierLists() {
        listOfAllCarriers = carrierListLoader.getCarrierList();
        listOfAllFlights = flightListLoader.getFlightList();
        setCarrierInfoForAllFlight();
    }

    public  Predicate<Flight> doesFlightRouteMatch(SearchCriteria filterCriteria) {
        return flight -> flight.isFlightBetweenSrcAndDestination(filterCriteria.getSource(),filterCriteria.getDestination());
    }

    public  Predicate<Flight> doesDepartureDateMatch(SearchCriteria filterCriteria) {
        return flight -> (null == filterCriteria.getDepartureDate()  ||
                flight.getDepartureDate().equals(filterCriteria.getDepartureDate()));
    }


    public  Predicate<Flight> doesFlightHaveSeatsInTravelClass(SearchCriteria filterCriteria) {

        return flight -> (null==filterCriteria.getTravelClass() ||
                flight.isTravelClassAvailbleinFlight(filterCriteria.getTravelClass()) &&
                flight.getNoOfSeatsForTravelClass(filterCriteria.getTravelClass()) >= filterCriteria.getNoOfPassengers());
    }


    public List<String> getAllSourceLocations() {
        List <String> listofSrcLocations =  listOfAllFlights.stream()
                .map(flight->flight.getSource())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        return listofSrcLocations;

    }

    public List<String> getAllDestLocations() {
        List <String> listofDestLocations = listOfAllFlights.stream()
                .map(flight->flight.getDestination())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        return listofDestLocations;

    }

    public List<Flight> searchForFlights(SearchCriteria searchFld) {

        List<Flight> listOfFlightsbySrcNDestByAvlb = new ArrayList<Flight>();

        if (null == searchFld.getNoOfPassengers() || searchFld.getNoOfPassengers() <= 0)
            searchFld.setNoOfPassengers(1);

        listOfFlightsbySrcNDestByAvlb =  listOfAllFlights.stream()
                .filter(doesFlightRouteMatch(searchFld))
                .filter(doesDepartureDateMatch(searchFld))
                .filter(doesFlightHaveSeatsInTravelClass(searchFld))
                .collect(Collectors.toList());

        return listOfFlightsbySrcNDestByAvlb;

    }

    public List<Float> calculateBasePriceForSeatsForFlightList(List<Flight> filteredFlights, SearchCriteria searchCriteria) {
        int noOfPassengers = searchCriteria.getNoOfPassengers();
        TravelClass classOfTravel = searchCriteria.getTravelClass();
        List <Float> costOfSeatsList =  new ArrayList<Float>();
        for (Flight flight:filteredFlights
             ) {
            Float costOfSeats = flight.getBasePriceForATravelClass(classOfTravel) * noOfPassengers;
            costOfSeatsList.add(Float.parseFloat(String.format("%.2f",costOfSeats)));
        }
        return costOfSeatsList;
    }

}
