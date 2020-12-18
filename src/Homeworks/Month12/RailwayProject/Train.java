package Homeworks.Month12.RailwayProject;

import static Methods.Methods.*;

import java.util.ArrayList;
import java.util.HashMap;

class Train {
    private final int id;
    private final String name;
    private int speed;
    private int capacity;
    private int ticketCost;
    private TrainType trainType;
    private String routeCode;
    private ArrayList<Station> stations;
    private Route route;

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

    Train(int id, String name, int speed, int capacity, int ticketCost, TrainType trainType, String routeCode, ArrayList<Way> wayMap) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.capacity = capacity;
        this.ticketCost = ticketCost;
        this.trainType = trainType;
        this.routeCode = routeCode;
        stations = DatabaseHandler.getStations();
        this.route = new Route(wayMap);
    }

    void start(TimeHandler timeHandler) {
        /*
        this.timeHandler = timeHandler;
        movementHandler = new MovementHandler(route.get(0), timeHandler);
        if (route.size() == 1) {
            movementHandler.singleWayStart();
        } else {
            movementHandler.start();
        }
         */
    }

    void silentStart(TimeHandler timeHandler) {
        this.timeHandler = timeHandler;
    }

    void move() {
        route.move();
    }

    class Route {
        ArrayList<Path> route = new ArrayList<>();
        Path currentPath;
        Station currentStation;
        Station nextStation;
        int timeBeforeArrival;
        int currentStationIndex;

        Route(ArrayList<Way> wayMap) {
            if (wayMap.size() == 1) {
                Station departure = Station.getStationByName(stations, wayMap.get(0).getDeparture());
                Station destination = Station.getStationByName(stations, wayMap.get(0).getDestination());
                route.add(new Path(
                        departure,
                        wayMap.get(0).getDistance()));
                route.add(new Path(
                        destination,
                        wayMap.get(0).getDistance()));

                currentStation = departure;
                currentStationIndex = 0;
                nextStation = destination;
                currentPath = route.get(0);
                timeBeforeArrival = calculateTimeBeforeArrival();
                return;
            }

            wayMap.add(wayMap.get(0));
            for (int i = 0; i < wayMap.size() - 1; i++) {
                String destination = Way.includedInBoth(wayMap.get(i), wayMap.get(i + 1));
                String departure = wayMap.get(i).getOtherStation(destination);
                route.add(new Path(
                        Station.getStationByName(stations, departure),
                        wayMap.get(i).getDistance()));
            }

            currentStation = route.get(0).getStation();
            currentStationIndex = 0;
            nextStation = route.get(1).getStation();
            currentPath = route.get(0);
            timeBeforeArrival = calculateTimeBeforeArrival();
        }

        void move() {
            if (--timeBeforeArrival == 0) { // arrived
                System.out.println("Train " + getInfo() + " arrived on station: " + currentStation);
                if (currentStationIndex + 1 == route.size()) { // next loop
                    currentStationIndex = 0;
                    nextStation = route.get(currentStationIndex + 1).getStation();
                } else if (currentStationIndex + 2 == route.size()) { // last in route handling
                    currentStationIndex++;
                    nextStation = route.get(0).getStation();
                } else {
                    currentStationIndex++;
                    nextStation = route.get(currentStationIndex + 1).getStation();
                }
                currentPath = route.get(currentStationIndex);
                currentStation = route.get(currentStationIndex).getStation();
                timeBeforeArrival = calculateTimeBeforeArrival();
            }
        }

        // from current path and current timeBeforeArrival finds time to next stations
        int calculateRemainingTimeTo(Station desiredStation) {
            int currentIndex = currentStationIndex;
            int remainingTime = this.timeBeforeArrival;
            //int timeBeforeArrival = this.timeBeforeArrival;
            Station currentStationBuffer = currentStation;
            Station nextStationBuffer = nextStation;

            while (true) {
                if(timeBeforeArrival-- == calculateTimeBeforeArrival()) { // arrived now

                } else {

                }
                break;
            }
            int ind = indexOfStationInRoute(desiredStation);
            int startInd = currentStationIndex;

            return remainingTime;
        }

        class Calculator {
            Path currentPath;
            Station currentStation;
            Station nextStation;
            int timeBeforeArrival;
            int currentStationIndex;


        }


        int indexOfStationInRoute(Station station) {
            for (int i = 0; i < route.size(); i++) {
                if (route.get(i).getStation().equals(station)) {
                    return i;
                }
            }
            return -1;
        }

        boolean isStationIncluded(Station station) {
            for (Path path : route) {
                if (path.getStation().equals(station)) {
                    return true;
                }
            }
            return false;
        }


        int calculateTimeBeforeArrival() {
            return Math.round((float) currentPath.getDistance() / speed);
        }

        public Path getCurrentPath() {
            return currentPath;
        }

        public Station getCurrentStation() {
            return currentStation;
        }

        public Station getNextStation() {
            return nextStation;
        }

        public int getTimeBeforeArrival() {
            return timeBeforeArrival;
        }

        String getStateDescription() {
            return "Current path: " + currentPath +
                    "\nCurrent station: " + currentStation +
                    "\nNextStation: " + nextStation +
                    "\nTime before arrival: " + timeBeforeArrival;
        }

        @Override
        public String toString() {
            return "Route: " + route;
        }

        private class Path {
            Station station;
            int distance;

            public Path(Station station, int distance) {
                this.station = station;
                this.distance = distance;
            }

            Station getStation() {
                return station;
            }

            int getDistance() {
                return distance;
            }

            @Override
            public String toString() {
                return "Path: Start: " + station + ", distance to next station: " + distance;
            }
        }
    }


    // Returns the date of the first meet with desired station
    String calculateNextArrivalTimeAt(Station station) {
        int currentWayPointCopy = movementHandler.getCurrentWayPoint();
        Way currentWayCopy = movementHandler.getCurrentWay();
        Way nextWayCopy = movementHandler.getNextWay();
        int timeBeforeArrivalCopy = movementHandler.getTimeBeforeArrival();

        MovementCalculator movementCalculator = new MovementCalculator(
                currentWayCopy, nextWayCopy, currentWayPointCopy, timeBeforeArrivalCopy, station);

        return movementCalculator.getNextDateOfArrival();
    }

    boolean hasStationInRoute(Station desiredStation) {
        /*
        for(Way way : route) {
            if(way.isIncluded(desiredStation)) return true;
        }
         */
        return false;
    }


    // Responsible for movement simulation in isolation
    // Doesn't affect any of other classes, only their copies
    private class MovementCalculator {
        private int currentWayPoint;
        private Way currentWay;
        private Way nextWay;
        private int timeBeforeArrival;
        private final TimeHandler timeHandlerSnapshot;
        private final Station desiredStation;
        private String desiredDate;

        private MovementCalculator(Way currentWay, Way nextWay, int currentWayPoint, int timeBeforeArrival, Station desiredStation) {
            this.currentWay = currentWay;
            this.nextWay = nextWay;
            this.currentWayPoint = currentWayPoint;
            this.timeBeforeArrival = timeBeforeArrival;
            this.desiredStation = desiredStation;
            timeHandlerSnapshot = timeHandler.getSilentSnapshot();
            /*
            if (timeBeforeArrival != movementHandler.calculateTime()) { // train in move
                this.timeBeforeArrival++; // ????
                findDesiredStation(); // continue separate simulation with copied state
            } else { // train currently on station
                if (route.size() == 1) {
                    singleWayStart();
                } else {
                    String departure = getDeparture(currentWay, nextWay);
                    if(desiredStation.getName().equals(departure)) {
                        setDesiredDate();
                        return;
                    }
                    start();
                }
            }

             */
        }

        private String getNextDateOfArrival() {
            return desiredDate;
        }

        private void findDesiredStation() {
            /*
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

             */
        }

        private void start() {
            /*
            currentWay = route.get(currentWayPoint);

             */
            timeBeforeArrival = calculateTime() + 1;
            // System.out.println("TIME BEFORE ARRIVAL: " + timeBeforeArrival);
            //line("-");
            nextWay = calculateNextWay();

            String departure = getDeparture(currentWay, nextWay);
            String destination = getDestination(currentWay, nextWay);
/*
            System.out.println(Train.this.getInfo() +
                    "\nStarts from: " + departure +
                    "\nTo: " + destination);
*/
            if (departure.equals(desiredStation.getName())) {
                setDesiredDate();
                return;
            }

            findDesiredStation();
        }

        private void setDesiredDate() {
            desiredDate = paint(Colors.BLUE, timeHandlerSnapshot.toString());
        }

        private boolean move() {
            if (--timeBeforeArrival == 0) { // arrived
                //line("-");
                String destination = getDestination(currentWay, nextWay);
                /*
                System.out.println("Arrived train: " + Train.this.getInfo() +
                        "\nCurrent date: " + timeHandlerSnapshot +
                        "\nOn station: " + destination);
*/
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
                /*
                System.out.println("Next station: " + destination +
                        "\nEstimated time on route: " + timeBeforeArrival + "h.");
                        */
                // if we arrived on the desired station
            }
            return false;
        }

        private String singleWayCurrentDeparture;
        private String singleWayCurrentDestination;

        private void singleWayStart() {
            /*
            currentWay = route.get(currentWayPoint);

             */
            timeBeforeArrival = calculateTime() + 1;
            // line("-");

            singleWayCurrentDeparture = currentWay.getDeparture();
            singleWayCurrentDestination = currentWay.getDestination();

            if (singleWayCurrentDeparture.equals(desiredStation.getName())) {
                desiredDate = paint(Colors.BLUE, timeHandlerSnapshot.toString());
                return;
            }

            findDesiredStation();
        }

        private boolean singleWayMove() {
            if (--timeBeforeArrival == 0) { // arrived
                timeBeforeArrival = calculateTime();
                line("-");
                /*
                System.out.println("Arrived train: " + Train.this.getInfo() +
                        "\nCurrent date: " + timeHandlerSnapshot +
                        "\nOn station: " + singleWayCurrentDestination);
*/
                Station currentStation = Station.getStationByName(stations, singleWayCurrentDestination);
                if (singleWayCurrentDestination.equals(desiredStation.getName())) {
                    desiredDate = paint(Colors.BLUE, timeHandlerSnapshot.toString());
                    return true;
                }
/*
                System.out.println("Next station: " + singleWayCurrentDeparture +
                        "\nEstimated time on route: " + timeBeforeArrival + "h.");
*/
                singleWayCurrentDeparture = currentWay.getOtherStation(singleWayCurrentDeparture);
                singleWayCurrentDestination = currentWay.getOtherStation(singleWayCurrentDestination);
            }
            return false;
        }

        private Way calculateNextWay() {
            /*
            Way nextWay;
            if (currentWayPoint + 1 < route.size()) { // next
                nextWay = route.get(++currentWayPoint);
            } else { // circle
                currentWayPoint = 0;
                nextWay = route.get(currentWayPoint);
            }

             */
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
            silentStart();

            // Verbose things
            line("-");
            String departure = getDeparture(currentWay, nextWay);
            String destination = getDestination(currentWay, nextWay);
            System.out.println(Train.this.getInfo() +
                    "\nStarts from: " + departure +
                    "\nTo: " + destination +
                    "\nEstimated time on route: " + timeBeforeArrival + "h.");
        }

        private void silentStart() {
            /*
            currentWay = route.get(currentWayPoint);
            timeBeforeArrival = calculateTime();
            nextWay = calculateNextWay();

             */
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
            /*
            currentWay = route.get(currentWayPoint);
             */
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

                System.out.println("Next station: " + singleWayCurrentDeparture +
                        "\nEstimated time on route: " + timeBeforeArrival + "h.");

                singleWayCurrentDeparture = currentWay.getOtherStation(singleWayCurrentDeparture);
                singleWayCurrentDestination = currentWay.getOtherStation(singleWayCurrentDestination);
            }
        }

        private Way calculateNextWay() {
            /*
            Way nextWay;
            if (currentWayPoint + 1 < route.size()) { // next
                nextWay = route.get(++currentWayPoint);
            } else { // circle
                currentWayPoint = 0;
                nextWay = route.get(currentWayPoint);
            }
            */
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

        private int getCurrentWayPoint() {
            return currentWayPoint;
        }

        private Way getCurrentWay() {
            return currentWay;
        }

        private Way getNextWay() {
            return nextWay;
        }

        private int getTimeBeforeArrival() {
            return timeBeforeArrival;
        }

        private String getSingleWayCurrentDeparture() {
            return singleWayCurrentDeparture;
        }

        private String getSingleWayCurrentDestination() {
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
        //this.route = route;
    }

    Route route() {
        return route;
    }

    String getRouteString() {
        return route.toString();
    }

    int getTimeBeforeArrival() {
        return route.getTimeBeforeArrival();
    }
}
