package airline.repositories;

import airline.model.CarrierDetails;
import airline.model.TravelClass;
import airline.model.CarrierType;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gayathri on 05/09/17.
 */
public class CarrierDataLoader {

    public static Map<CarrierType,CarrierDetails> populateFromCSVFile(String fileName) {
        Map <CarrierType, CarrierDetails> resultList = new HashMap<>();

        String line = "";
        String cvsSplitBy = ",";

        try {
            File file = new ClassPathResource(fileName).getFile();
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                Map <TravelClass, Integer> seatsPerClass = new HashMap<>();
                // use comma as separator
                //IgnoreLinesstarting with ##
                if (line.startsWith("#"))
                    continue;
                String[] carrierData = line.split(cvsSplitBy);
                CarrierType carrierType = CarrierType.valueOf(carrierData[0]);
                Boolean hasFirstClass = Boolean.parseBoolean(carrierData[1]);
                Boolean hasBusinessClass = Boolean.parseBoolean(carrierData[2]);
                Boolean hasEconomyClass = Boolean.parseBoolean(carrierData[3]);
                seatsPerClass.clear();
                int seatsForFirst = Integer.parseInt(carrierData[4]);
                int seatsForBusiness = Integer.parseInt(carrierData[5]);
                int seatsForEconomy = Integer.parseInt(carrierData[6]);
                seatsPerClass.putIfAbsent(TravelClass.FIRST,seatsForFirst);
                seatsPerClass.putIfAbsent(TravelClass.BUSINESS,seatsForBusiness);
                seatsPerClass.putIfAbsent(TravelClass.ECONOMY,seatsForEconomy);
                System.out.print(seatsPerClass.toString());
                resultList.put(carrierType,new CarrierDetails(carrierType,
                        hasFirstClass,hasBusinessClass,hasEconomyClass,seatsPerClass));

            }
            System.out.println("Carrier type added is " + resultList.toString() );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

}
