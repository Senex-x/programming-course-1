package Homeworks.Month12.RailwayProject;

import java.util.ArrayList;

public class Ticket {
    private int passengerId;
    private int cost;
    private int trainId;
    private String dateOfDeparture;
    private Station departure;
    private Station destination;

    public Ticket(int passengerId, int cost, int trainId, String dateOfDeparture, Station departure, Station destination) {
        this.passengerId = passengerId;
        this.cost = cost;
        this.trainId = trainId;
        this.dateOfDeparture = dateOfDeparture;
        this.departure = departure;
        this.destination = destination;
    }

    public Ticket(int passengerId, int cost) {
        this.passengerId = passengerId;
        this.cost = cost;
    }

    String getInfo(ArrayList<Passenger> passengers, ArrayList<Station> stations) {
        return "Ticket: Passenger: " + Passenger.getPassengerById(passengerId, passengers) +
                "\nCost: " + cost +
                ",\nDeparture: " + departure + ", Destination: " + destination;
    }

    @Override
    public String toString() {
        return "Ticket:" +
                "passengerId=" + passengerId +
                ", cost=" + cost +
                ", trainId=" + trainId +
                ", dateOfDeparture='" + dateOfDeparture +
                ", departure=" + departure +
                ", destination=" + destination +
                '}';
    }
}
