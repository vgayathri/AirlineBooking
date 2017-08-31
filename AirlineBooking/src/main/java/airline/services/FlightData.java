package airline.services;

import airline.model.Flight;

import java.io.*;
import java.util.*;
import airline.util.*;

/**
 * Created by Gayathri on 30/08/17.
 */
public class FlightData {
    private List <Flight> listOfAllFlights;

    public FlightData(String flightInfoFileName) {
        listOfAllFlights = new ArrayList<Flight>();

    }
    public FlightData(List <Flight> inputList) {
        listOfAllFlights = inputList;
    }


    public void loadFlightDataFromFile(String fileName) {
        String line = "";
        String cvsSplitBy = ",";

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] flightData = line.split(cvsSplitBy);
                String flightID = flightData[0];
                String src = flightData[1];
                String destination = flightData[2];

                listOfAllFlights.add(new Flight(flightID,src,destination));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

/*
    public List <Flight> getFlightsBySrc( String srcLocation) {
        List <Flight> listOfFlightsbySrc = new ArrayList<Flight>();
        for( int index =0; index<listOfAllFlights.size();index++ ) {
            if (srcLocation.equalsIgnoreCase(listOfAllFlights.get(index).getSource())) {
                listOfFlightsbySrc.add(listOfAllFlights.get(index));
            }
        }
        return listOfFlightsbySrc;

    }

    public List <Flight> getFlightsByDest( String destLocation) {
        List <Flight> listOfFlightsbyDest = new ArrayList<Flight>();
        for( int index =0; index<listOfAllFlights.size();index++ ) {
            if (destLocation.equalsIgnoreCase(listOfAllFlights.get(index).getDestination())) {
                listOfFlightsbyDest.add(listOfAllFlights.get(index));
            }
        }
        return listOfFlightsbyDest;

    }
*/
    public List <String> getAllSourceLocations() {
        List <String> listOfSourceLocations = new ArrayList<String>();
        for( int index =0; index<listOfAllFlights.size();index++ ) {
            String srcLoc = listOfAllFlights.get(index).getSource();
            if (!listOfSourceLocations.contains(srcLoc))
                listOfSourceLocations.add(srcLoc);
        }
        return listOfSourceLocations;
    }

    public List <String> getAllDestLocations() {
        List <String> listOfDestLocations = new ArrayList<String>();
        for( int index =0; index<listOfAllFlights.size();index++ ) {
            String destLoc = listOfAllFlights.get(index).getDestination();
            if (!listOfDestLocations.contains(destLoc))
                listOfDestLocations.add(destLoc);
        }
        return listOfDestLocations;
    }

    public List <Flight> getBySrcAndDestn(String srcLocation, String destLocation) {
        List <Flight> listOfFlightsbySrcNDest = new ArrayList<Flight>();
        for( int index =0; index<listOfAllFlights.size();index++ ) {
            Flight flightObj = listOfAllFlights.get(index);
            if (destLocation.equalsIgnoreCase(flightObj.getDestination()) &&
                    srcLocation.equalsIgnoreCase(flightObj.getSource())) {
                listOfFlightsbySrcNDest.add(listOfAllFlights.get(index));
            }
        }
        return listOfFlightsbySrcNDest;
    }

}
