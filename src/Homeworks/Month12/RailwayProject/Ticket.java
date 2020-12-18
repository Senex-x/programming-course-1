package Homeworks.Month12.RailwayProject;

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

    @Override
    public String toString() {
        return "Ticket{" +
                "passengerId=" + passengerId +
                ", cost=" + cost +
                ", trainId=" + trainId +
                ", dateOfDeparture='" + dateOfDeparture +
                ", departure=" + departure +
                ", destination=" + destination +
                '}';
    }
}
