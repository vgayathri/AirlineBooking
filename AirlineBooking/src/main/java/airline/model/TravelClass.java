package airline.model;

/**
 * Created by Gayathri on 05/09/17.
 */

public enum TravelClass {
    FIRST("F"),
    BUSINESS("B"),
    ECONOMY("E");

    private String classLetter;

    private TravelClass(String s) {
        classLetter = s;
    }

    public String getClassLetter() {
        return classLetter;
    }

    public static TravelClass getByName(String letter){
        for(TravelClass travelClass : values()){
            if(travelClass.getClassLetter().equals(letter)){
                return travelClass;
            }
        }

        throw new IllegalArgumentException(letter + " is not a valid PropName");
    }
}
