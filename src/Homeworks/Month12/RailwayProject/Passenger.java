package Homeworks.Month12.RailwayProject;

import java.util.ArrayList;

public class Passenger {
    private final int id;
    private String name;
    private String password;
    private Ticket currentTicket;
    private HistoryHolder historyHolder;

    public Passenger(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        historyHolder = new HistoryHolder();
    }

    void buyTicket(Ticket ticket) {
        historyHolder.addToHistory(ticket);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Ticket> getTripsHistory() {
        return historyHolder.getTripsHistory();
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    class TicketHandler {
        Ticket currentTicket;


    }

    static class HistoryHolder {
        ArrayList<Ticket> tripsHistory = new ArrayList<>();

        void addToHistory(Ticket ticket) {
            tripsHistory.add(ticket);
        }

        public ArrayList<Ticket> getTripsHistory() {
            return tripsHistory;
        }
    }

    /*
    Человек спопобен на многое, но многое, в свою очередь, способно его убить
     */
}
