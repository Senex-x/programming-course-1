package Homeworks.Month12.RailwayProject;

import java.util.ArrayList;

public class Passenger {
    private String id;
    private String name;
    private Ticket currentTicket;
    private ArrayList<Ticket> tripsHistory;

    public Passenger(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
