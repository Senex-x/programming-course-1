package Homeworks.Month12.RailwayProject;

public class Ticket {
    private int passengerId;
    private int cost;
    private int trainId;
    private String dateOfDeparture;
    private String dateOfDestination;
    private Station departure;
    private Station destination;

    public Ticket(int passengerId, int cost) {
        this.passengerId = passengerId;
        this.cost = cost;
    }
}
