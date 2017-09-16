package airline.repositories;

import airline.model.Carrier;
import airline.model.CarrierType;
import airline.model.SeatsInfo;
import airline.model.TravelClass;
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
    private List <Carrier> carrierList = new ArrayList<>();

    public CarrierDataLoader () {
        createCarrierList();
    }
    public CarrierDataLoader (String dataFileName) {
        populateFromCSVFile(dataFileName);
    }

    public void createCarrierList() {

        /*Boeing 777 has all three classes of travel*/
        Map<TravelClass, SeatsInfo> travelClassMapBoeing = new HashMap<TravelClass, SeatsInfo>();
        travelClassMapBoeing.put(TravelClass.FIRST, new SeatsInfo(8,20000f));
        travelClassMapBoeing.put(TravelClass.BUSINESS, new SeatsInfo(35,13000f));
        travelClassMapBoeing.put(TravelClass.ECONOMY,new SeatsInfo (195,100,6000f));

        /*Airbus 321 does not support first class*/
        Map<TravelClass, SeatsInfo> travelClassMap321 = new HashMap<TravelClass, SeatsInfo>();
        travelClassMap321.put(TravelClass.BUSINESS, new SeatsInfo(20,10000f));
        travelClassMap321.put(TravelClass.ECONOMY,new SeatsInfo(152, 20,5000f));

        /*Airbus 319 supports only economy class*/
         Map<TravelClass, SeatsInfo> travelClassMap319v2 = new HashMap<TravelClass, SeatsInfo>();
        travelClassMap319v2.put(TravelClass.ECONOMY, new SeatsInfo(144,140, 4000f));

        carrierList.add(new Carrier(CarrierType.BOEING777,travelClassMapBoeing));
        carrierList.add(new Carrier(CarrierType.AIRBUS321,travelClassMap321));
        carrierList.add(new Carrier(CarrierType.AIRBUS319V2,travelClassMap319v2));

    }

    /**
     * Read from CSV File being supported when many flights
     * need to be instantiated
     **/

    public void populateFromCSVFile(String fileName) {

        String line = "";
        String cvsSplitBy = ",";

        try {
            File file = new ClassPathResource(fileName).getFile();
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                Map<TravelClass, SeatsInfo> seatsPerClass = new HashMap<>();
                /*
                 * Use comma as separator
                 * IgnoreLinesstarting with ##
                 */

                if (line.startsWith("#"))
                    continue;
                String[] carrierData = line.split(cvsSplitBy);
                CarrierType carrierType = CarrierType.valueOf(carrierData[0]);
                Boolean hasFirstClass = Boolean.parseBoolean(carrierData[1]);
                Boolean hasBusinessClass = Boolean.parseBoolean(carrierData[2]);
                Boolean hasEconomyClass = Boolean.parseBoolean(carrierData[3]);
                seatsPerClass.clear();
                int allocatedSeatsForFirst = Integer.parseInt(carrierData[4]);
                int allocatedSeatsForBusiness = Integer.parseInt(carrierData[5]);
                int allocatedSeatsForEconomy = Integer.parseInt(carrierData[6]);
                Float priceOfFirstSeat = Float.parseFloat(carrierData[7]);
                Float priceOfBusinessSeat = Float.parseFloat(carrierData[8]);
                Float priceOfEconomySeat = Float.parseFloat(carrierData[9]);

                if (hasFirstClass) {
                    seatsPerClass.putIfAbsent(TravelClass.FIRST,
                            new SeatsInfo(allocatedSeatsForFirst, priceOfFirstSeat));
                }
                    if (hasBusinessClass) {
                    seatsPerClass.putIfAbsent(TravelClass.BUSINESS,
                            new SeatsInfo(allocatedSeatsForBusiness, priceOfBusinessSeat));
                }
                if (hasEconomyClass) {
                    seatsPerClass.putIfAbsent(TravelClass.ECONOMY,
                            new SeatsInfo(allocatedSeatsForEconomy, priceOfEconomySeat));
                }
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
