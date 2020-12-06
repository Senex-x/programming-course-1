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
    private ArrayList<Way> route;
    // Passenger's id <--> Passenger's ticket
    private HashMap<String, Ticket> currentTickets;
    private Way currentWay;
    private MovementHandler movementHandler;

    // only used in trains.txt parser for initial input
    public Train(int id, String name, int speed, int capacity, int ticketCost, TrainType trainType, String routeCode) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.capacity = capacity;
        this.ticketCost = ticketCost;
        this.trainType = trainType;
        this.routeCode = routeCode;
    }

    public Train(int id, String name, int speed, int capacity, int ticketCost, TrainType trainType, String routeCode, ArrayList<Way> route) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.capacity = capacity;
        this.ticketCost = ticketCost;
        this.trainType = trainType;
        this.routeCode = routeCode;
        this.route = route;
        movementHandler = new MovementHandler(route, route.get(0));
    }

    void start() {
        movementHandler.start();
    }

    void move() {
        movementHandler.move();
    }

    private class MovementHandler {
        private int currentWayPoint = 0;
        private final ArrayList<Way> route;
        private Way currentWay;
        private int timeBeforeArrival;

        public MovementHandler(ArrayList<Way> route, Way currentWay) {
            this.route = route;
            this.currentWay = currentWay;
        }

        void start() {
            currentWay = route.get(currentWayPoint++);
            timeBeforeArrival = calculateTime();
            System.out.println("On start: currentWay" + currentWay + ", timeBeforeArrival: " + timeBeforeArrival);
        }

        void move() {
            if(--timeBeforeArrival == 0) { // arrived
                if(currentWayPoint + 1 < route.size()) {
                    currentWay = route.get(currentWayPoint++);
                } else {
                    currentWayPoint = 0;
                    currentWay = route.get(0);
                }
                timeBeforeArrival = calculateTime();
            }
            System.out.println("On move: currentWay" + currentWay + ", timeBeforeArrival: " + timeBeforeArrival);
        }

        int calculateTime() {
            return Math.round((float)currentWay.getDistance() / speed);
        }
    }

    Way getCurrentWay() {
        return currentWay;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", speed=" + speed +
                ", capacity=" + capacity +
                ", ticketCost=" + ticketCost +
                ", trainType=" + trainType +
                ", routeCode='" + routeCode + '\'' +
                ", route=" + route +
                '}';
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

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public void setRoute(ArrayList<Way> route) {
        this.route = route;
    }
}
