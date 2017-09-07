package airline.repositories;

import airline.model.Carrier;
import airline.model.TravelClass;
import airline.model.CarrierType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gayathri on 05/09/17.
 */
@Repository
public class CarrierDataLoader {
    List <Carrier> carrierList = new ArrayList<>();

    public CarrierDataLoader () {
        createCarrierList();
    }
    public CarrierDataLoader (String dataFileName) {
        populateFromCSVFile(dataFileName);
    }

    public void createCarrierList() {


        final Map<TravelClass, Integer> travelClassMapBoeing = new HashMap<TravelClass, Integer>();
        travelClassMapBoeing.put(TravelClass.FIRST, 8);
        travelClassMapBoeing.put(TravelClass.BUSINESS, 35);
        travelClassMapBoeing.put(TravelClass.ECONOMY,195);


        final Map<TravelClass, Integer> travelClassMap321 = new HashMap<TravelClass, Integer>();
        travelClassMap321.put(TravelClass.BUSINESS, 20);
        travelClassMap321.put(TravelClass.ECONOMY,152);

        final Map<TravelClass, Integer> travelClassMap319v2 = new HashMap<TravelClass, Integer>();
        travelClassMap319v2.put(TravelClass.ECONOMY, 144);

        carrierList.add(new Carrier(CarrierType.BOEING777,travelClassMapBoeing));
        carrierList.add(new Carrier(CarrierType.AIRBUS321,travelClassMap321));
        carrierList.add(new Carrier(CarrierType.AIRBUS319V2,travelClassMap319v2));

    }

    public void populateFromCSVFile(String fileName) {

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
                if (hasFirstClass)
                    seatsPerClass.putIfAbsent(TravelClass.FIRST,seatsForFirst);
                if (hasBusinessClass)
                    seatsPerClass.putIfAbsent(TravelClass.BUSINESS,seatsForBusiness);
                if (hasEconomyClass)
                    seatsPerClass.putIfAbsent(TravelClass.ECONOMY,seatsForEconomy);
                carrierList.add (new Carrier(carrierType,seatsPerClass));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List <Carrier> getCarrierList() {
        return carrierList;
    }

}
