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
    private ArrayList<Station> stations;

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
        stations = DatabaseHandler.getStations();
    }

    void start(TimeHandler timeHandler) {
        this.timeHandler = timeHandler;
        movementHandler = new MovementHandler(route.get(0), timeHandler);
        if (route.size() == 1) {
            movementHandler.singleWayStart();
        } else {
            movementHandler.start();
        }
    }

    void move() {
        if (route.size() == 1) {
            movementHandler.singleWayMove();
        } else {
            movementHandler.move();
        }
    }

    // Returns the date of the first meet with desired station
    String calculateNextArrivalTimeAt(Station station) {
        int currentWayPointCopy = movementHandler.getCurrentWayPoint();
        Way currentWayCopy = movementHandler.getCurrentWay();
        Way nextWayCopy = movementHandler.getNextWay();
        int timeBeforeArrivalCopy = movementHandler.getTimeBeforeArrival();

        MovementCalculator movementCalculatorOld = new MovementCalculator(
                currentWayCopy, nextWayCopy, currentWayPointCopy, timeBeforeArrivalCopy, station);

        return movementCalculatorOld.getNextDateOfArrival();
    }

    // Responsible for movement simulation in isolation
    // Doesn't affect any of other classes, only their copies
    private class MovementCalculator {
        private int currentWayPoint = 0;
        private Way currentWay;
        private Way nextWay;
        private int timeBeforeArrival;
        private final TimeHandler timeHandlerSnapshot;
        private final Station desiredStation;
        private String desiredDate;

        public MovementCalculator(Way currentWay, Way nextWay, int currentWayPoint, int timeBeforeArrival, Station desiredStation) {
            this.currentWay = currentWay;
            this.nextWay = nextWay;
            this.currentWayPoint = currentWayPoint;
            this.timeBeforeArrival = timeBeforeArrival;
            this.desiredStation = desiredStation;
            timeHandlerSnapshot = timeHandler.getSilentSnapshot();
            if(timeBeforeArrival != 0) {
                String departure = getDeparture(currentWay, nextWay);
                if (departure.equals(desiredStation.getName())) {
                    desiredDate = paint(Colors.BLUE, timeHandlerSnapshot.toString());
                    return;
                }
                this.timeBeforeArrival ++; // ????
                findDesiredStation();
            } else {
                if (route.size() == 1) {
                    singleWayStart();
                } else {
                    start();
                }
            }
        }

        public String getNextDateOfArrival() {
            return desiredDate;
        }

        void findDesiredStation() {
            if (route.size() == 1) {
                if (!singleWayMove()) { // if still not found
                    timeHandlerSnapshot.nextHour();
                    findDesiredStation();
                } // else finishing
            } else {
                if (!move()) { // if still not found
                    timeHandlerSnapshot.nextHour();
                    findDesiredStation();
                } // else finishing
            }
        }

        void start() {
            currentWay = route.get(currentWayPoint);
            timeBeforeArrival = calculateTime() + 1;
            // System.out.println("TIME BEFORE ARRIVAL: " + timeBeforeArrival);
            line("-");
            nextWay = calculateNextWay();

            String departure = getDeparture(currentWay, nextWay);
            String destination = getDestination(currentWay, nextWay);

            System.out.println(Train.this.getInfo() +
                    "\nStarts from: " + departure +
                    "\nTo: " + destination);

            if (departure.equals(desiredStation.getName())) {
                desiredDate = paint(Colors.BLUE, timeHandlerSnapshot.toString());
                return;
            }

            findDesiredStation();
        }

        boolean move() {
            if (--timeBeforeArrival == 0) { // arrived
                line("-");
                String destination = getDestination(currentWay, nextWay);
                System.out.println("Arrived train: " + Train.this.getInfo() +
                        "\nCurrent date: " + timeHandlerSnapshot +
                        "\nOn station: " + destination);

                Station currentStation = Station.getStationByName(stations, destination);
                if (currentStation.equals(desiredStation)) {
                    desiredDate = paint(Colors.BLUE, timeHandlerSnapshot.toString());
                    return true;
                }

                // System.out.println("current: " + currentWay + "\nnext: " + nextWay);
                currentWay = nextWay;
                nextWay = calculateNextWay();
                timeBeforeArrival = calculateTime();

                destination = getDestination(currentWay, nextWay);
                System.out.println("Next station: " + destination +
                        "\nEstimated time on route: " + timeBeforeArrival + "h.");
                // if we arrived on the desired station
            }
            return false;
        }

        private String singleWayCurrentDeparture;
        private String singleWayCurrentDestination;

        void singleWayStart() {
            currentWay = route.get(currentWayPoint);
            timeBeforeArrival = calculateTime() + 1;
            line("-");

            singleWayCurrentDeparture = currentWay.getDeparture();
            singleWayCurrentDestination = currentWay.getDestination();

            if (singleWayCurrentDeparture.equals(desiredStation.getName())) {
                desiredDate = paint(Colors.BLUE, timeHandlerSnapshot.toString());
                return;
            }

            findDesiredStation();
        }

        boolean singleWayMove() {
            if (--timeBeforeArrival == 0) { // arrived
                timeBeforeArrival = calculateTime();
                line("-");
                System.out.println("Arrived train: " + Train.this.getInfo() +
                        "\nCurrent date: " + timeHandlerSnapshot +
                        "\nOn station: " + singleWayCurrentDestination);

                Station currentStation = Station.getStationByName(stations, singleWayCurrentDestination);
                if (singleWayCurrentDestination.equals(desiredStation.getName())) {
                    desiredDate = paint(Colors.BLUE, timeHandlerSnapshot.toString());
                    return true;
                }

                System.out.println("Next station: " + singleWayCurrentDeparture +
                        "\nEstimated time on route: " + timeBeforeArrival + "h.");

                singleWayCurrentDeparture = currentWay.getOtherStation(singleWayCurrentDeparture);
                singleWayCurrentDestination = currentWay.getOtherStation(singleWayCurrentDestination);
            }
            return false;
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
    }

    private class MovementHandler {
        private int currentWayPoint = 0;

        private Way currentWay;
        private Way nextWay;
        private int timeBeforeArrival;
        private final TimeHandler timeHandler;

        private MovementHandler(Way currentWay, TimeHandler timeHandler) {

            this.currentWay = currentWay;
            this.timeHandler = timeHandler;
        }

        private void start() {
            currentWay = route.get(currentWayPoint);
            timeBeforeArrival = calculateTime();
            line("-");
            nextWay = calculateNextWay();

            String departure = getDeparture(currentWay, nextWay);
            String destination = getDestination(currentWay, nextWay);

            System.out.println(Train.this.getInfo() +
                    "\nStarts from: " + departure +
                    "\nTo: " + destination +
                    "\nEstimated time on route: " + timeBeforeArrival + "h.");
        }

        private void move() {
            if (--timeBeforeArrival == 0) { // arrived
                line("-");
                String destination = getDestination(currentWay, nextWay);
                System.out.println("Arrived train: " + Train.this.getInfo() +
                        "\nCurrent date: " + timeHandler +
                        "\nOn station: " + destination);

                // System.out.println("current: " + currentWay + "\nnext: " + nextWay);
                currentWay = nextWay;
                nextWay = calculateNextWay();
                timeBeforeArrival = calculateTime();

                destination = getDestination(currentWay, nextWay);
                System.out.println("Next station: " + destination +
                        "\nEstimated time on route: " + timeBeforeArrival + "h.");
            }
        }

        private String singleWayCurrentDeparture;
        private String singleWayCurrentDestination;

        private void singleWayStart() {
            currentWay = route.get(currentWayPoint);
            timeBeforeArrival = calculateTime();
            line("-");

            singleWayCurrentDeparture = currentWay.getDeparture();
            singleWayCurrentDestination = currentWay.getDestination();

            System.out.println(Train.this.getInfo() +
                    "\nStarts from: " + singleWayCurrentDeparture +
                    "\nTo: " + singleWayCurrentDestination);
        }

        private void singleWayMove() {
            if (--timeBeforeArrival == 0) { // arrived
                timeBeforeArrival = calculateTime();
                line("-");
                System.out.println("Arrived train: " + Train.this.getInfo() +
                        "\nCurrent date: " + timeHandler +
                        "\nOn station: " + singleWayCurrentDestination);

                System.out.println("current: " + currentWay +
                        "\nnext: " + currentWay);

                System.out.println("Next station: " + singleWayCurrentDeparture +
                        "\nEstimated time on route: " + timeBeforeArrival + "h.");

                singleWayCurrentDeparture = currentWay.getOtherStation(singleWayCurrentDeparture);
                singleWayCurrentDestination = currentWay.getOtherStation(singleWayCurrentDestination);
            }
        }

        private Way calculateNextWay() {
            Way nextWay;
            if (currentWayPoint + 1 < route.size()) { // next
                nextWay = route.get(++currentWayPoint);
            } else { // circle
                currentWayPoint = 0;
                nextWay = route.get(currentWayPoint);
            }
            return nextWay;
        }

        private String getDestination(Way current, Way next) {
            return Way.includedInBoth(current, next);
        }

        private String getDeparture(Way current, Way next) {
            String departure = Way.includedInBoth(current, next);
            return current.getOtherStation(departure);
        }

        private int calculateTime() {
            return Math.round((float) currentWay.getDistance() / speed);
        }

        public int getCurrentWayPoint() {
            return currentWayPoint;
        }

        public Way getCurrentWay() {
            return currentWay;
        }

        public Way getNextWay() {
            return nextWay;
        }

        public int getTimeBeforeArrival() {
            return timeBeforeArrival;
        }

        public String getSingleWayCurrentDeparture() {
            return singleWayCurrentDeparture;
        }

        public String getSingleWayCurrentDestination() {
            return singleWayCurrentDestination;
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
