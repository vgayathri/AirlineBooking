package airline.repositories;

import airline.model.CarrierType;
import airline.model.Flight;
import org.apache.tomcat.jni.Local;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Gayathri on 31/08/17.
 */
@Repository
public class FlightDataLoader {

    List <Flight> listOfFlights = new ArrayList<Flight>();
    public FlightDataLoader () {
        createFlightList();
    }

    public FlightDataLoader (String flightDataFileName) {
        populateFromCSVFile(flightDataFileName);
    }

    public void createFlightList() {

        LocalDate todaysDate = LocalDate.now();
        LocalDate nextDate = todaysDate.plusDays(1);
        listOfFlights.add(new Flight("FL101","Source1","Dest1",todaysDate));
        listOfFlights.add(new Flight("FL102","Source1","Dest1",nextDate));
        listOfFlights.add(new Flight("FL203","Source2","Dest2",todaysDate));
        listOfFlights.add(new Flight("FL204","Source2","Dest2",nextDate));
        listOfFlights.add(new Flight("FL305","Source3","Dest3",todaysDate));
        listOfFlights.add(new Flight("FL306","Source3","Dest3",nextDate));

    }

    public void populateFromCSVFile(String fileName) {
        String line = "";
        String cvsSplitBy = ",";

        try {
            File file = new ClassPathResource(fileName).getFile();
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                // use comma as separator

                String[] flightData = line.split(cvsSplitBy);
                if (line.startsWith("##"))
                    continue;
                String flightID = flightData[0];
                String src = flightData[1];
                String destination = flightData[2];
                String strDateFormat = flightData[3];
                CarrierType flightCarrierType = CarrierType.valueOf(flightData[4]);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                LocalDate departureDate = LocalDate.parse(strDateFormat, formatter);
                listOfFlights.add(new Flight(flightID,src,destination,departureDate));
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        return;
    }

    public List <Flight> getFlightList() {
        return listOfFlights;

    }

}
