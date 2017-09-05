package airline.util;

import airline.model.Flight;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Gayathri on 31/08/17.
 */
public final class FlightDataLoader {

    public static List <Flight> populateFromCSVFile(String fileName) {
            List <Flight> resultList = new ArrayList<Flight>();
            String line = "";
            String cvsSplitBy = ",";

            try {
                File file = new ClassPathResource(fileName).getFile();
                BufferedReader br = new BufferedReader(new FileReader(file));

                while ((line = br.readLine()) != null) {
                    // use comma as separator
                    String[] flightData = line.split(cvsSplitBy);
                    String flightID = flightData[0];
                    String src = flightData[1];
                    String destination = flightData[2];
                    int totalSeats = Integer.parseInt(flightData[3]);
                    int noOfSeatsTaken = Integer.parseInt(flightData[4]);
                    String strDateFormat = flightData[5];
                    DateTimeFormatter dateFormatter = new DateTimeFormatter.
                    LocalDate departureDate =  LocalDate.parse(strDateFormat,new DateTimeFormatter(""));
                    resultList.add(new Flight(flightID,src,destination,totalSeats,noOfSeatsTaken,departureDate));


                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        return resultList;
        }

}
