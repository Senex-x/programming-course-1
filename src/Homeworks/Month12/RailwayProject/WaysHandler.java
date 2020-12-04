package Homeworks.Month12.RailwayProject;

import java.util.ArrayList;

public class WaysHandler {
    ArrayList<Way> ways;

    public WaysHandler(ArrayList<Way> ways) {
        this.ways = ways;
    }

    // finds all ways referring to station with given id
    ArrayList<Way> getWaysFor(String name) {
        ArrayList<Way> ways = new ArrayList<>();
        for(Way way : this.ways) {
            if(way.isIncluded(name)) {
                ways.add(way);
            }
        }
        return ways;
    }
}
