package Homeworks.Month12.RailwayProject;

import com.google.gson.Gson;

import static Methods.Methods.*;

import java.sql.SQLOutput;
import java.util.*;

class Test {
    public static void main(String[] args) {
        RailwaySystem system = new RailwaySystem();
        system.start();
    }
}

/**
 * TODO
 * - GENERIC TASKS
 * - Authorization (with password saving using hash code maybe)
 * - Dialog asking how long to continue simulation in loop
 * - Sign out
 * - Quit
 * -
 * - LOGGING
 * - Tickets selling logging
 * - Passenger's boardings logging
 * - Train's movements logging
 * - Save logs somewhere
 * - Possibility to delete logs
 * -
 * - PASSENGER ACCOUNT
 * - Account saving in database
 * - Buying ticket to train (unavailable before previous is not cancelled or finished)
 * - Find proper train according to demands
 * - Check timetable for appropriate trains
 * - Buy ticket to certain date
 * - Cancelling ticket
 * - Check history
 * -
 * - ADMINISTRATOR ACCOUNT privileges
 * - Possibility to add new trains
 * - Possibility to delete trains
 * - Possibility to add new stations and routes
 * - Possibility to edit train's route
 * - Possibility to edit any info about train besides of ID
 */

class RailwaySystem {
    private static final int ADMIN_ID = 108;
    TimeHandler timeHandler = new TimeHandler();
    TrainsMovementHandler trainsHandler = new TrainsMovementHandler();
    // Doing all the work referred to data files
    DatabaseHandler databaseHandler = new DatabaseHandler();
    // Contains list of all possible ways and offers different methods to work with this list
    WaysHandler waysHandler = databaseHandler.getWaysHandler();
    // Contains all trains from database
    // Singleton pattern
    ArrayList<Train> trains = databaseHandler.getTrains();
    // Singleton pattern
    ArrayList<Station> stations = DatabaseHandler.getStations();
    ArrayList<Passenger> passengers = databaseHandler.getPassengers();

    Gson gson = new Gson();

    void start() {
        Passenger passenger = null;
        timeHandler.printDate();

        Train testTrain = new Train(
                0,
                "EP2K",
                100,
                200,
                600,
                TrainType.ECONOMY,
                "null",
                new ArrayList<>(Arrays.asList(new Way("Vahitovo", "Kamaevo", 320)))
        );

        //System.out.println("testTrain: " + testTrain);

        /*
        Senex
        qwerty123
         */

        Passenger passengerTest = passengers.get(0);
        Train train = trains.get(0);
        //train = testTrain;

        Station departureTest = stationWithName("Sosnovka");
        Station destinationTest = stationWithName("Karambai");

        System.out.println("... Welcome to Electronic Railway System (ERS) ...\n" +
                "Please sign up (1) or log in (2)");
        int inp = getInt();

        // passenger = loggingInUser(inp);
        passenger = passengers.get(passengers.size() - 1);

        while (true) {
            System.out.println("If you want to exit enter (0).\n" +
                    "If you want to buy a ticket enter (1).\n" +
                    "If you want to check your trips history enter (2).");
            if (passenger.getId() == ADMIN_ID) {
                System.out.println("If you want to change train parameters enter (3).");
                System.out.println("If you want to add new train enter (4).");
            }
            inp = Integer.parseInt(getLine());
            outer: switch (inp) {
                case 0:
                    System.out.println("Thanks for using our system, shutting down ERS...");
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("Please enter following information. ");
                    displayArray(stations, 1);
                    System.out.println("Please, enter desired departure station ID: ");
                    int stationId = getInt();
                    Station departure = stationWithId(stationId);
                    System.out.println("Please, enter desired destination station ID: ");
                    stationId = getInt();
                    Station destination = stationWithId(stationId);

                    System.out.println("Chosen departure: " + departure +
                            "\nChosen destination: " + destination + "\n");

                    ArrayList<Train> suitableTrains = new ArrayList<>();
                    for (Train t : trains) {
                        if (t.route().isStationIncluded(departure) && t.route().isStationIncluded(destination)) {
                            suitableTrains.add(t);
                        }
                    }

                    System.out.println("Trains suitable for trip: ");
                    for (Train t : suitableTrains) {
                        line("-");
                        System.out.println(t);
                        System.out.println("Time before departure: " + t.route().calculateRemainingTimeTo(departure));
                        System.out.println("Time on board: " + t.route().calculateTimeBetween(departure, destination));
                    }

                    System.out.println("Please enter desired train ID: ");
                    int desiredTrainId = getInt();
                    Train chosenTrain = trainWithId(desiredTrainId);

                    System.out.println("You have picked: " + chosenTrain);

                    int timeBeforeDeparture = chosenTrain.route().calculateRemainingTimeTo(departure);
                    int timeOnBoard = chosenTrain.route().calculateTimeBetween(departure, destination);
                    Ticket newTicket = new Ticket(
                            passenger.getId(),
                            chosenTrain.calculateCost(timeOnBoard),
                            chosenTrain.getId(),
                            timeHandler.getTimeForwardedBy(timeBeforeDeparture),
                            departure,
                            destination
                    );
                    passenger.buyTicket(newTicket);
                    databaseHandler.updatePassengersDB(passenger);
                    databaseHandler.updatePassengers();

                    simulateAllTrains(chosenTrain.route().calculateRemainingTimeTo(departure));
                    System.out.println(paint(Colors.PURPLE, "Your train have arrived to departure point."));
                    simulateAllTrains(chosenTrain.route().calculateRemainingTimeTo(destination));
                    System.out.println(paint(Colors.PURPLE, "Your train have arrived to destination point."));

                    break;
                case 2:
                    for (Ticket currentTicket : passenger.getTripsHistory()) {
                        line("-");
                        System.out.println(currentTicket.getInfo(passengers, stations));
                    }
                    line("-");
                    break;
                case 3: // change parameters
                    if (passenger.getId() != ADMIN_ID) break;
                    displayArray(trains, 1);
                    System.out.println("Please enter ID of a train to change parameters of: ");
                    int trainIdInput = getInt();
                    Train chosenTrainToEdit = Train.getTrainWithId(trainIdInput, trains);
                    System.out.println("Chosen train: " + chosenTrainToEdit.getInfo());

                    System.out.println("If you want to edit speed enter (1).\n" +
                            "If you want to edit train capacity enter (2).\n" +
                            "If you want to edit train ticket cost enter (3).\n" +
                            "If you want to edit train type enter (4).\n" +
                            "If you want to edit route code of a train enter (5).\n" +
                            "If you want to cancel operation enter (0)" );
                    int trainEditInput = getInt();
                    switch (trainEditInput) {
                        case 1: //speed
                            System.out.println("Enter new train speed in km/h: ");
                            int newSpeed = getInt();
                            chosenTrainToEdit.setSpeed(newSpeed);
                            databaseHandler.updateTrains();

                            for(Train train1 : trains) {
                                System.out.println(train1.getInfo());
                            }
                            break outer;
                        case 2: // capacity
                            System.out.println("Enter new train capacity: ");
                            int newCapacity = getInt();
                            break outer;
                        case 3: // ticket cost
                            System.out.println("Enter new train ticket cost: ");
                            int newCost = getInt();
                            break outer;
                        case 4: //train type
                            System.out.println("Enter new train type: \n" +
                                    "LUXE (1), COMFORT (2), ECONOMY (3).");
                            int newType = getInt();
                            TrainType trainType = TrainType.values()[newType - 1];
                            break outer;
                        case 5: // route code
                            break outer;
                    }


                case 4: // add new train
                    if (passenger.getId() != ADMIN_ID) break;

                default:
                    System.out.println("Incorrect input, please try again.");
            }
        }
    }

    Passenger loggingInUser(int inp) {
        Passenger passenger = null;
        while (true) {
            if (inp == 1) {
                System.out.println("To sign up please enter following information.\n" +
                        "Your name: ");
                String name = getLine();
                System.out.println("Your password: ");
                String password = getLine();

                Passenger newPassenger = new Passenger(
                        name,
                        password
                );

                databaseHandler.addToTablePassengers(newPassenger);
                databaseHandler.updatePassengers();
                passenger = passengers.get(passengers.size() - 1);
                break;

            } else if (inp == 2) {
                System.out.println("To log in please enter following information.\n" +
                        "Your name: ");
                String name = getLine();
                System.out.println("Your password: ");
                String password = getLine();

                for (Passenger p : passengers) {
                    if (p.getName().equals(name) && p.getPassword().equals(password)) {
                        System.out.println("Logged in successfully.");
                        passenger = p;
                    }
                }
                if (passenger == null) {
                    System.out.println("User not found, please try again.");
                    continue;
                }
                break;
            } else {
                System.out.println("Incorrect input, try again.");
            }
        }
        return passenger;
    }

    int getInt() {
        int input;
        try {
            input = Integer.parseInt(getLine());
        } catch (Exception e) {
            System.out.println("Incorrect input, please try again.");
            input = this.getInt();
        }
        return input;
    }

    private Train trainWithId(int id) {
        return Train.getTrainWithId(id, trains);
    }

    private Station stationWithName(String name) {
        return Station.getStationByName(stations, name);
    }

    private Station stationWithId(int id) {
        return Station.getStationById(stations, id);
    }

    private void simulateAllTrains(int hours) {
        for (int i = 0; i < hours; i++) {
            line("-");
            timeHandler.nextHour();
            for (Train train : trains) {
                train.move();
            }
        }
    }

    private void simulateTrain(Train train, int hours) {
        //line(paint(Colors.RED, "SIMULATION START // "));
        //train.start(timeHandler);
        for (int i = 0; i < hours; i++) {
            line("-");
            timeHandler.nextHour();
            train.move();
        }
        //line(paint(Colors.RED, "SIMULATION END // "));
    }

    private static class TrainsMovementHandler {
        ArrayList<Train> trains;
    }

    DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }
}
