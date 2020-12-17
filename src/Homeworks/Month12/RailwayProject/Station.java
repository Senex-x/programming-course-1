package Homeworks.Month12.RailwayProject;

import Methods.Methods;

import java.util.ArrayList;

public class Station {
    private int id;
    private String name;
    ArrayList<Way> ways;

    public Station(int id, String name, ArrayList<Way> ways) {
        this.id = id;
        this.name = name;
        this.ways = ways;
    }

    static Station getStationByName(ArrayList<Station> stations, String stationName) {
        for (Station station : stations) {
            if (station.getName().equals(stationName)) {
                return station;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Station: " + name + " (ID: " + id + ")";
    }
}
