package Homeworks.Month12.RailwayProject;

import java.util.ArrayList;

public class Passenger {
    private final int id;
    private String name;
    private String password;
    private Ticket currentTicket;
    private HistoryHolder historyHolder;

    public Passenger(int id, String name, String password, ArrayList<Ticket> history) {
        this.id = id;
        this.name = name;
        this.password = password;
        historyHolder = new HistoryHolder(history);
    }

    public Passenger(int id, String name, String password, HistoryHolder history) {
        this.id = id;
        this.name = name;
        this.password = password;
        historyHolder = history;
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

    public HistoryHolder getHistoryHolder() {
        return historyHolder;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", currentTicket=" + currentTicket +
                ", historyHolder=" + historyHolder +
                '}';
    }

    class TicketHandler {
        Ticket currentTicket;


    }

    static class HistoryHolder {
        ArrayList<Ticket> tripsHistory;

        public HistoryHolder(ArrayList<Ticket> tripsHistory) {
            this.tripsHistory = tripsHistory;
        }

        void addToHistory(Ticket ticket) {
            tripsHistory.add(ticket);
        }

        public ArrayList<Ticket> getTripsHistory() {
            return tripsHistory;
        }

        @Override
        public String toString() {
            return "HistoryHolder{" +
                    "tripsHistory=" + tripsHistory +
                    '}';
        }
    }

    /*
    Человек спопобен на многое, но многое, в свою очередь, способно его убить
     */
}
