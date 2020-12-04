package Homeworks.Month12.RailwayProject;

public class Way {
    Station from;
    Station where;
    int distance;

    public Way(Station from, Station where, int distance) {
        this.from = from;
        this.where = where;
        this.distance = distance;
    }

    boolean isIncluded(Station station) {
        return station.getName().equals(from.getName())
                || station.getName().equals(where.getName());
    }
}
