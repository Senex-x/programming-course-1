package Homeworks.Month12.RailwayProject;

import static Methods.Methods.*;

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

    private MovementHandler movementHandler;
    private TimeHandler timeHandler;

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

    void start(TimeHandler timeHandler) {
        movementHandler.start();
        this.timeHandler = timeHandler;
    }

    void move() {
        movementHandler.move();
    }

    private class MovementHandler {
        private int currentWayPoint = 0;
        private final ArrayList<Way> route;
        private Way currentWay;
        private Way nextWay;
        private int timeBeforeArrival;

        public MovementHandler(ArrayList<Way> route, Way currentWay) {
            this.route = route;
            this.currentWay = currentWay;
        }

        void start() {
            currentWay = route.get(currentWayPoint);
            timeBeforeArrival = calculateTime();
            line("-");
            nextWay = calculateNextWay();

            String departure = getDeparture(currentWay, nextWay);
            String destination = getDestination(currentWay, nextWay);
            //String departure =  currentWay.getOtherStation(Way.includedInBoth(currentWay));

            System.out.println(Train.this.getInfo() +
                    "\nStarts from: " + departure +
                    "\nTo: " + destination);


        }

        void move() {
            if (--timeBeforeArrival == 0) { // arrived
                line("-");
                String destination = getDestination(currentWay, nextWay);
                System.out.println("Arrived train: " + Train.this.getInfo() +
                        "\nCurrent date: " + timeHandler +
                        "\nOn station: " + destination);
                /*
                System.out.println("Arrived train: " + Train.this.getInfo() +
                                "\nCurrent date: " + timeHandler +
                                "\nOn station: " + currentWay.getDestination());
                */

                currentWay = nextWay;
                nextWay = calculateNextWay();
                timeBeforeArrival = calculateTime();


                destination = getDestination(currentWay, nextWay);
                System.out.println("Next station: " + destination +
                        "\nEstimated time on route: " + timeBeforeArrival + "h.");

                /*
                System.out.println("Next station: " + currentWay.getDestination() +
                                "\nEstimated time on route: " + timeBeforeArrival + "h.");
            */
            }

        }

        Way calculateNextWay() {
            Way nextWay;
            if (currentWayPoint + 1 < route.size()) { // next
                nextWay = route.get(++currentWayPoint);
            } else { // circle
                currentWayPoint = 0;
                nextWay = route.get(currentWayPoint);
            }
            return nextWay;
        }

        String getDestination(Way current, Way next) {
            String departure = Way.includedInBoth(current, next);
            return departure;
        }

        String getDeparture(Way current, Way next) {
            String departure = Way.includedInBoth(current, next);
            String destination = current.getOtherStation(departure);
            return destination;
        }

        int calculateTime() {
            return Math.round((float) currentWay.getDistance() / speed);
        }
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

    String getInfo() {
        return name + " (ID: " + id + ")";
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
