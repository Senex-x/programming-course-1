package Homeworks.Month12.RailwayProject;

import java.util.ArrayList;

class WaysHandler {
    private final ArrayList<Way> ways;
    private ArrayList<Station> stations;

    WaysHandler(ArrayList<Way> ways) {
        this.ways = ways;
    }

    ArrayList<Way> getAllPossibleWays() {
        return ways;
    }

    void displayAllPossibleWays() {
        System.out.println("Total amount of ways: " + ways.size());
        for (Way way : ways) {
            System.out.println(way);
        }
    }

    // finds all ways referring to station with given id
    ArrayList<Way> getWaysForStation(String name) {
        ArrayList<Way> ways = new ArrayList<>();
        for (Way way : this.ways) {
            if (way.isIncluded(name)) {
                ways.add(way);
            }
        }
        return ways;
    }

    Way getWayBetweenStations(String from, String where) {
        for(Way way : ways) {
            if(way.isIncluded(from) && way.isIncluded(where)) return way;
        }
        return new Way("null", "null", -1);
    }

    // checks route code for train and returns proper route of ways
    ArrayList<Way> getRouteForTrain(Train train) {
        return getRouteForTrain(train.getRouteCode());
    }

    //1 2 3 10
    ArrayList<Way> getRouteForTrain(String routeCode) {
        ArrayList<Way> route = new ArrayList<>();
        StringBuilder code = new StringBuilder(routeCode);
        code.append(" ");
        int currentStationId;
        int nextStationId = 0;
        int startStationId = Integer.parseInt(code.substring(0, code.indexOf(" ")));
        while (code.indexOf(" ") != code.lastIndexOf(" ")) {
            //System.out.println(code);
            currentStationId = Integer.parseInt(code.substring(0, code.indexOf(" ")));
            code.delete(0, code.indexOf(" ") + 1);
            nextStationId = Integer.parseInt(code.substring(0, code.indexOf(" ")));
            Station current = stations.get(currentStationId);
            Station next = stations.get(nextStationId);
            route.add(new Way(
                    current.getName(),
                    next.getName(),
                    getWayBetweenStations(current.getName(), next.getName()).getDistance()
            ));
            //System.out.println(currentStationId + " --> " + nextStationId);
        }
        route.add(new Way(
                stations.get(startStationId).getName(),
                stations.get(nextStationId).getName(),
                getWayBetweenStations(
                        stations.get(startStationId).getName(),
                        stations.get(nextStationId).getName()
                ).getDistance()
        ));
        //System.out.println(startStationId + " --> " + nextStationId);
        //System.out.println(code);
        return route;
    }

    void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }
}
