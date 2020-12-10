package Homeworks.Month12.RailwayProject;

import java.util.ArrayList;

public class Route {
    ArrayList<Station> route;
    ArrayList<Station> stations;

    Route(ArrayList<Station> stations, ArrayList<Way> wayMap) {
        this.stations = stations;

        wayMap.add(wayMap.get(0));
        for (int i = 0; i < wayMap.size() - 1; i++) {
            String destination = Way.includedInBoth(wayMap.get(i), wayMap.get(i + 1));
            String departure = wayMap.get(i).getOtherStation(destination);
            route.add(Station.getStationByName(stations, departure));
            if (i == wayMap.size() - 2) {
                route.add(Station.getStationByName(stations, destination));
            }
        }


    }

    @Override
    public String toString() {
        return "Route{" +
                "route=" + route +
                '}';
    }
}
