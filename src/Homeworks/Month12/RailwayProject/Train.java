package Homeworks.Month12.RailwayProject;

import java.util.ArrayList;
import java.util.HashMap;

public class Train {
    private int id;
    private String name;
    private int speed;
    private int capacity;
    private int ticketCost;
    private TrainType trainType;
    private String routeCode;
    private ArrayList<Station> route;
    // Passenger's id <--> Passenger's ticket
    private HashMap<String, Ticket> currentTickets;

    public Train(int id, String name, int speed, int capacity, int ticketCost, TrainType trainType, String routeCode) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.capacity = capacity;
        this.ticketCost = ticketCost;
        this.trainType = trainType;
        this.routeCode = routeCode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getTicketCost() {
        return ticketCost;
    }

    public TrainType getTrainType() {
        return trainType;
    }

    public String getRouteCode() {
        return routeCode;
    }
}
