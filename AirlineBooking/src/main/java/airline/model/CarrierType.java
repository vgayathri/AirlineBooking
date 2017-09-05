package airline.model;

/**
 * Created by Gayathri on 05/09/17.
 */
public enum CarrierType {
    BOEING777("Boeing-777"),
    AIRBUS321("Airbus 321"),
    AIRBUS319V2("Airbus-319 V2");

    private String carrierTypeStr;

    private CarrierType(String s) {
        carrierTypeStr = s;
    }

    public String getCarrierTypeName() {
        return carrierTypeStr;
    }

}
