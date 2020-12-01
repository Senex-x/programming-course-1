package Homeworks.Month12.RailwayProject;

import java.util.HashMap;

public class Train {
    private int capacity;
    private int speed;
    private int ticketCost;
    private TrainType trainType;
    // Passenger's id <--> Passenger's ticket
    private HashMap<String, Ticket> currentTickets;


}
