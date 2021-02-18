package Homeworks.FirstSemester.Month12.RailwayProject;

import java.util.ArrayList;
import java.util.HashMap;

class Train implements Informative {
    private int id;
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

    // only used in trains.txt parser for initial input
    Train(String name, int speed, int capacity, int ticketCost, TrainType trainType, String routeCode) {
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

    void move() {
        route.move();
    }

    public int calculateCost(int timeOnBoard) {
        return ticketCost * timeOnBoard;
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
                System.out.println("Train " + getInfo() + " arrived on station: " + nextStation);
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

        // from departure to destination
        int calculateTimeBetween(Station departure, Station destination) {
            int currentIndex = indexOfStationInRoute(departure);
            int remainingTime = 0;

            while (!route.get(currentIndex).getStation().equals(destination)) {
                remainingTime += calculateTimeBeforeArrival(route.get(currentIndex));
                if (currentIndex + 1 == route.size()) {
                    currentIndex = 0;
                } else {
                    currentIndex++;
                }
            }

            return remainingTime;
        }

        // from current path and current timeBeforeArrival finds time to next stations
        int calculateRemainingTimeTo(Station desiredStation) {
            int currentIndex = currentStationIndex;
            int remainingTime = this.timeBeforeArrival;

            // desired station is next
            /*if (route.get(currentIndex).getStation().equals(desiredStation)) {
                return calculateTimeBeforeArrival(route.get(currentIndex));
            }
             */

            if (currentIndex + 1 == route.size()) {
                currentIndex = 0;
            } else {
                currentIndex++;
            }

            while (!route.get(currentIndex).getStation().equals(desiredStation)) {
                remainingTime += calculateTimeBeforeArrival(route.get(currentIndex));
                if (currentIndex + 1 == route.size()) {
                    currentIndex = 0;
                } else {
                    currentIndex++;
                }
            }

            return remainingTime;
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

        int calculateTimeBeforeArrival(Path path) {
            return Math.round((float) path.getDistance() / speed);
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

    static Train getTrainWithId(int id, ArrayList<Train> trains) {
        for (Train train : trains) {
            if (train.getId() == id) {
                return train;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Train: " + name + " (ID: " + id + ")";
    }

    @Override
    public String getInfo() {
        return "Train{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", speed=" + speed +
                ", capacity=" + capacity +
                ", ticketCost=" + ticketCost +
                ", trainType=" + trainType +
                ", routeCode='" + routeCode + '\'' +
                ", route=" + route +
                ", currentTickets=" + currentTickets +
                '}';
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setTicketCost(int ticketCost) {
        this.ticketCost = ticketCost;
    }

    public void setTrainType(TrainType trainType) {
        this.trainType = trainType;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public void setRoute(Route route) {
        this.route = route;
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
