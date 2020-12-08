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
        movementHandler = new MovementHandler(route, route.get(0), timeHandler);
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

    String calculateNextArrivalTimeAt(Station station) {
        line("-");
        MovementCalculator movementCalculatorOld = new MovementCalculator(route, route.get(0), station);
        return movementCalculatorOld.getNextDateOfArrival();
        /*
        Way current = movementHandler.getCurrentWa  y();
        Way next = movementHandler.getNextWay();
        String nextStationName = movementHandler.getDestination(current, next);
        System.out.println(nextStationName);
*/

    }

    /*
    private class MovementCalculatorOld {
        private int currentWayPoint;
        private Way currentWay;
        private Way nextWay;
        private Station currentStation;
        private Station nextStation;
        private final Station desiredStation;
        private String desiredDate;
        private TimeHandler timeHandlerSnapshot;
        private int timeBeforeArrival;

        public MovementCalculatorOld(Way currentWay, Station desiredStation) {
            this.currentWay = currentWay;
            this.desiredStation = desiredStation;
            timeHandlerSnapshot = timeHandler.getSilentSnapshot();
            currentWayPoint = route.indexOf(currentWay);
            nextWay = calculateNextWay(); // change available
            timeBeforeArrival = calculateTime();

            findDesiredStation();
        }

        void findDesiredStation() {
            if (!stationCheck()) { // if still not found
                timeHandlerSnapshot.nextHour();
                findDesiredStation();
            }
        }

        boolean stationCheck() {
            String nextStationName = Way.includedInBoth(currentWay, nextWay);
            String currentStationName = currentWay.getOtherStation(nextStationName);

            System.out.println(paint(Colors.PURPLE,
                    "Current " + currentStationName +
                    "\nNext " + nextStationName));

            currentStation = Station.getStationByName(stations, currentStationName);
            nextStation = Station.getStationByName(stations, nextStationName);

            // if we arrived on the desired station
            if (currentStation.equals(desiredStation)) {
                desiredDate = paint(Colors.BLUE, timeHandlerSnapshot.toString());
                return true;
            }



            nextWay = calculateNextWay(); // change available

            line("-");
            return false;
        }

        String getNextDateOfArrival() {
            return desiredDate;
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
     */

    private class MovementCalculator {
        private int currentWayPoint = 0;
        private final ArrayList<Way> route;
        private Way currentWay;
        private Way nextWay;
        private int timeBeforeArrival;
        private final TimeHandler timeHandlerSnapshot;
        private final Station desiredStation;
        private String desiredDate;

        public MovementCalculator(ArrayList<Way> route, Way currentWay, Station desiredStation) {
            this.route = route;
            this.currentWay = currentWay;
            this.desiredStation = desiredStation;
            timeHandlerSnapshot = timeHandler.getSilentSnapshot();
            start();
        }

        public String getNextDateOfArrival() {
            return desiredDate;
        }

        void start() {
            currentWay = route.get(currentWayPoint);
            timeBeforeArrival = calculateTime() + 1;
            System.out.println("TIME BEFORE ARRIVAL: " + timeBeforeArrival);
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

        void findDesiredStation() {
            if (!move()) { // if still not found
                timeHandlerSnapshot.nextHour();
                findDesiredStation();
            } // else finishing
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
            timeBeforeArrival = calculateTime();
            line("-");

            singleWayCurrentDeparture = currentWay.getDeparture();
            singleWayCurrentDestination = currentWay.getDestination();

            System.out.println(Train.this.getInfo() +
                    "\nStarts from: " + singleWayCurrentDeparture +
                    "\nTo: " + singleWayCurrentDestination);
        }

        public void singleWayMove() {
            if (--timeBeforeArrival == 0) { // arrived
                timeBeforeArrival = calculateTime();
                line("-");
                System.out.println("Arrived train: " + Train.this.getInfo() +
                        "\nCurrent date: " + timeHandlerSnapshot +
                        "\nOn station: " + singleWayCurrentDestination);

                System.out.println("current: " + currentWay +
                        "\nnext: " + currentWay);

                System.out.println("Next station: " + singleWayCurrentDeparture +
                        "\nEstimated time on route: " + timeBeforeArrival + "h.");

                singleWayCurrentDeparture = currentWay.getOtherStation(singleWayCurrentDeparture);
                singleWayCurrentDestination = currentWay.getOtherStation(singleWayCurrentDestination);
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



    private class MovementHandler {
        private int currentWayPoint = 0;
        private final ArrayList<Way> route;
        private Way currentWay;
        private Way nextWay;
        private int timeBeforeArrival;
        private final TimeHandler timeHandler;

        public MovementHandler(ArrayList<Way> route, Way currentWay, TimeHandler timeHandler) {
            this.route = route;
            this.currentWay = currentWay;
            this.timeHandler = timeHandler;
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

        void singleWayStart() {
            currentWay = route.get(currentWayPoint);
            timeBeforeArrival = calculateTime();
            line("-");

            singleWayCurrentDeparture = currentWay.getDeparture();
            singleWayCurrentDestination = currentWay.getDestination();

            System.out.println(Train.this.getInfo() +
                    "\nStarts from: " + singleWayCurrentDeparture +
                    "\nTo: " + singleWayCurrentDestination);
        }

        public void singleWayMove() {
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
