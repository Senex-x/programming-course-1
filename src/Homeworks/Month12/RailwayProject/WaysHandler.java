package Homeworks.Month12.RailwayProject;

import java.util.ArrayList;

public class WaysHandler {
    ArrayList<Way> ways;

    public WaysHandler(ArrayList<Way> ways) {
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

    // checks route code for train and returns proper route of ways
    ArrayList<Way> getRouteForTrain(Train train) {
        return getRouteForTrain(train.getRouteCode());
    }

    ArrayList<Way> getRouteForTrain(String routeCode) {
        ArrayList<Way> route = new ArrayList<>();
        for (int i = 0; i < routeCode.length() - 1; i++) {
            int currentStationId = routeCode.charAt(i) - 48;
            int nextStationId = routeCode.charAt(i + 1) - 48;

            System.out.println(currentStationId + " to " + nextStationId);
        }
        return route;
    }
}
