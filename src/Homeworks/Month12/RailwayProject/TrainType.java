package Homeworks.Month12.RailwayProject;

public enum TrainType {
    LUXE("Separate luxurous compartment with great service"),
    COMFORT("Good 4-place compartment"),
    ECONOMY("Enough comfortable for short trips");

    private final String description;

    TrainType(String s) {
        description = s;
    }

    public String getDescription() {
        return description;
    }
}
