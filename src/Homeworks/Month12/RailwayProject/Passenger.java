package Homeworks.Month12.RailwayProject;

import java.util.ArrayList;

public class Passenger {
    private final int id;
    private String name;
    private String password;
    private Ticket currentTicket;
    private ArrayList<Ticket> tripsHistory;

    public Passenger(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    void buyTicket(Ticket ticket) {

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

        class HistoryHolder {
            ArrayList<Ticket> tripsHistory;
        }
    }

    /*
    Человек спопобен на многое, но многое, в свою очередь, способно его убить
     */
}
