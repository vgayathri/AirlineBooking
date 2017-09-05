package airline.services;

import airline.model.Flight;

import java.util.*;
import java.util.stream.Collectors;

import airline.model.SearchCriteria;


/**
 * Created by Gayathri on 30/08/17.
 */
public class FlightDataHandler {
    private List<Flight> listOfAllFlights;

    public FlightDataHandler(String flightInfoFileName) {
        listOfAllFlights = new ArrayList<Flight>();

    }

    public FlightDataHandler(List<Flight> inputList) {
        listOfAllFlights = inputList;
    }

    public List<String> getAllSourceLocations() {

        List<String> listOfSourceLocations = new ArrayList<String>();

        for (int index = 0; index < listOfAllFlights.size(); index++) {
            String srcLoc = listOfAllFlights.get(index).getSource();
            if (!listOfSourceLocations.contains(srcLoc))
                listOfSourceLocations.add(srcLoc);
        }

        Collections.sort(listOfSourceLocations);
        return listOfSourceLocations;
    }

    public List<String> getAllDestLocations() {
        List<String> listOfDestLocations = new ArrayList<String>();
        for (int index = 0; index < listOfAllFlights.size(); index++) {
            String destLoc = listOfAllFlights.get(index).getDestination();
            if (!listOfDestLocations.contains(destLoc))
                listOfDestLocations.add(destLoc);
        }
        Collections.sort(listOfDestLocations);
        return listOfDestLocations;
    }

    public List<Flight> getBySrcAndDestn(SearchCriteria searchfld) {
        List<Flight> listOfFlightsbySrcNDest = new ArrayList<Flight>();
        for (int index = 0; index < listOfAllFlights.size(); index++) {
            Flight flightObj = listOfAllFlights.get(index);
            if (searchfld.getDestination().equalsIgnoreCase(flightObj.getDestination()) &&
                    searchfld.getSource().equalsIgnoreCase(flightObj.getSource())) {
                listOfFlightsbySrcNDest.add(listOfAllFlights.get(index));
            }
        }
        return listOfFlightsbySrcNDest;
    }

    public List<Flight> getBySrcAndDestnAndPassengerCount(SearchCriteria searchFld) {

        List<Flight> listOfFlightsbySrcNDestByAvlb = new ArrayList<Flight>();
        if (searchFld.getNoOfPassengers() <= 0)
            searchFld.setNoOfPassengers(1);
        listOfFlightsbySrcNDestByAvlb =  listOfAllFlights.stream()
                .filter(x->x.getSource().equalsIgnoreCase(searchFld.getSource()))
                .filter(x->x.getDestination().equalsIgnoreCase(searchFld.getDestination()))
                .filter(x->x.getNoOfSeatsAvailable()>=searchFld.getNoOfPassengers())
                .collect(Collectors.toList());

        if (searchFld.getDepartureDate() != null) {
            return listOfFlightsbySrcNDestByAvlb.stream()
            .filter(x->x.getDepartureDate().equals(searchFld.getDepartureDate()))
            .collect(Collectors.toList());
        }
        return listOfFlightsbySrcNDestByAvlb;

    }


}