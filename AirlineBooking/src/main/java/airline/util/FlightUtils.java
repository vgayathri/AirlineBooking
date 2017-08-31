package airline.util;

import airline.model.Flight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Gayathri on 31/08/17.
 */
public final class FlightUtils {

    public static List <Flight> populateFromCSVFile(String fileName) {
            List <Flight> resultList = new ArrayList<Flight>();
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

                    resultList.add(new Flight(flightID,src,destination));

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return resultList;
        }

}
