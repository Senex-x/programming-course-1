package Homeworks.FirstSemester.Month12.RailwayProject;

public enum TrainType {
    LUXE(0, "Separate luxurous compartment with great service"),
    COMFORT(1, "Good 4-place compartment"),
    ECONOMY(2, "Enough comfortable for short trips");

    private final int id;
    private final String description;

    TrainType(int id, String s) {
        this.id = id;
        description = s;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
