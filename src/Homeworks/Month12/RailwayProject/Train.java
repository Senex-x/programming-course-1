package Homeworks.Month12.RailwayProject;

import static Methods.Methods.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Train {
    private final int id;
    private final String name;
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
    Train(int id, String name, int speed, int capacity, int ticketCost, TrainType trainType, String routeCode) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.capacity = capacity;
        this.ticketCost = ticketCost;
        this.trainType = trainType;
        this.routeCode = routeCode;
    }

    Train(int id, String name, int speed, int capacity, int ticketCost, TrainType trainType, String routeCode, ArrayList<Way> route) {
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
        this.timeHandler = timeHandler;
        if(route.size() == 1) {
            movementHandler.singleWayStart();
        } else {
            movementHandler.start();
        }
    }

    void move() {
        if(route.size() == 1) {
            movementHandler.singleWayMove();
        } else {
            movementHandler.move();
        }
    }

    String calculateNextArrivalTime(Station station) {
        String date = "";
        MovementCalculator movementCalculator = new MovementCalculator(movementHandler.getCurrentWay());
        /*
        Way current = movementHandler.getCurrentWay();
        Way next = movementHandler.getNextWay();
        String nextStationName = movementHandler.getDestination(current, next);
        System.out.println(nextStationName);

*/
        return date;
    }
    
    private class MovementCalculator {
        int currentWayPoint;
        Way currentWay;
        // Station currentStation;
        // Station nextStation;

        public MovementCalculator(Way currentWay) {
            this.currentWay = currentWay;
            currentWayPoint = route.indexOf(currentWay);
            Way nextWay = calculateNextWay();
            System.out.println(nextWay);
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

                System.out.println("current: " + currentWay +
                        "\nnext: " + nextWay);
                currentWay = nextWay;
                nextWay = calculateNextWay();
                timeBeforeArrival = calculateTime();

                destination = getDestination(currentWay, nextWay);
                System.out.println("Next station: " + destination +
                        "\nEstimated time on route: " + timeBeforeArrival + "h.");
            }
        }

        void singleWayStart() {
        }

        public void singleWayMove() {
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
            return Way.includedInBoth(current, next);
        }

        String getDeparture(Way current, Way next) {
            String departure = Way.includedInBoth(current, next);
            return current.getOtherStation(departure);
        }

        int calculateTime() {
            return Math.round((float) currentWay.getDistance() / speed);
        }

        public Way getCurrentWay() {
            return currentWay;
        }

        public Way getNextWay() {
            return nextWay;
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

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getSpeed() {
        return speed;
    }

    int getCapacity() {
        return capacity;
    }

    int getTicketCost() {
        return ticketCost;
    }

    TrainType getTrainType() {
        return trainType;
    }

    String getRouteCode() {
        return routeCode;
    }

    void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    void setRoute(ArrayList<Way> route) {
        this.route = route;
    }
}
