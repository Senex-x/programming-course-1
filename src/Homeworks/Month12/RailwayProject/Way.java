package Homeworks.Month12.RailwayProject;

public class Way {
    String from;
    String where;
    int distance;

    public Way(String from, String where, int distance) {
        this.from = from;
        this.where = where;
        this.distance = distance;
    }

    boolean isIncluded(Station station) {
        return station.getName().equals(from)
                || station.getName().equals(where);
    }

    boolean isIncluded(String name) {
        return name.equals(from) || name.equals(where);
    }



    @Override
    public String toString() {
        return "Way{" +
                "from='" + from + '\'' +
                ", where='" + where + '\'' +
                ", distance=" + distance +
                '}';
    }
}
