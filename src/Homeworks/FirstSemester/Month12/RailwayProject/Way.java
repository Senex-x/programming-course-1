package Homeworks.FirstSemester.Month12.RailwayProject;

public class Way {
    String from;
    String where;
    int distance;

    Way(String from, String where, int distance) {
        this.from = from;
        this.where = where;
        this.distance = distance;
    }

    boolean isIncluded(Station station) {
        return station.getName().equals(from)
                || station.getName().equals(where);
    }

    String getOtherStation(String name) {
        if (from.equals(name)) return where;
        else return from;
    }

    static String includedInSameStationOtherThan(Way sameWay, String excludedString) {
        return sameWay.getDeparture().equals(excludedString) ? sameWay.getDestination() : sameWay.getDeparture();
    }

    static int counter = 0;

    static String includedInBoth(Way first, Way second) {
        String dp1 = first.getDeparture();
        String ds1 = first.getDestination();
        String dp2 = second.getDeparture();
        String ds2 = second.getDestination();
        if (dp1.equals(dp2) || dp1.equals(ds2)) {
            return dp1;
        } else if (ds1.equals(dp2) || ds1.equals(ds2)) {
            return ds1;
        }
        return "null";
    }


    boolean isIncluded(String name) {
        return name.equals(from) || name.equals(where);
    }

    int getDistance() {
        return distance;
    }

    String getDestination() {
        return where;
    }

    String getDeparture() {
        return from;
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
