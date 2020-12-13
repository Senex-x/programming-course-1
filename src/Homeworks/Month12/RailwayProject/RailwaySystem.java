package Homeworks.Month12.RailwayProject;

import com.google.gson.Gson;

import static Methods.Methods.*;

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
        timeHandler.printDate();

        Train testTrain = new Train(
                0,
                "EP2K",
                160,
                200,
                600,
                TrainType.ECONOMY,
                "0 1 2 3",
                new ArrayList<>(Arrays.asList(new Way("Vahitovo", "Kamaevo", 320)))
        );

        Passenger testPassenger = new Passenger(
                0,
                "Senex",
                "qwerty123",
                new ArrayList<>());
        // System.out.println("You are logged in as: " + testPassenger);

        Passenger passenger = new Passenger(
                0,
                "Hajime Hinata",
                "11037",
                new ArrayList<>(Arrays.asList(
                        new Ticket(0, 100),
                        new Ticket(0, 200),
                        new Ticket(0, 1300),
                        new Ticket(0, 140),
                        new Ticket(0, 50))));



        //displayArray(passengers, 1);

        /*
        System.out.println(passenger);
        String passengerJsonString = gson.toJson(passenger);
        System.out.println(passengerJsonString);

        Passenger passengerFromJson = gson.fromJson(passengerJsonString, Passenger.class);
        System.out.println(passengerFromJson);

        passenger.buyTicket(new Ticket(
                -1,
                100));

        //displayArray(passengers, 1);

/*
        displayArray(stations, 1);
        System.out.println("Choose departure station ID: ");
        Station departure = stations.get(getInt());
        System.out.println("Choose destination station ID: ");
        Station destination = stations.get(getInt());

        for(Train train : trains) {
            train.silentStart(timeHandler);
            if(train.hasStationInRoute(departure)) {
                System.out.println(train.getInfo());
                System.out.println("Will arrive at station " + departure +
                        " \nAt: " + train.calculateNextArrivalTimeAt(departure));
            }
        }

 */

        // bug
        // simulateTrain(trains.get(2), 10);

/*
        Train train = trains.get(0);
        //Train train = testTrain;
        System.out.println(train);
        train.start(timeHandler);

        System.out.println(
                train.calculateNextArrivalTimeAt(Station.getStationByName(stations, "Sosnovka")));

        simulateTrain(train, 2); // 3 -> 18
*/


        //simulateTrain(train, 10);
    }

    private void simulateAllTrains(int hours) {
        for (Train train : trains) {
            train.start(timeHandler);
        }
        for (int i = 0; i < hours; i++) {
            timeHandler.nextHour();
            for (Train train : trains) {
                train.move();
            }
        }
    }

    private void simulateTrain(Train train, int hours) {
        line(paint(Colors.RED, "SIMULATION START // "));
        System.out.println(train);
        train.start(timeHandler);
        for (int i = 0; i < hours; i++) {
            timeHandler.nextHour();
            train.move();
        }
        line(paint(Colors.RED, "SIMULATION END // "));
    }

    class Handler {

    }

    private static class TrainsMovementHandler {
        ArrayList<Train> trains;
    }

    DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

}