package Homeworks.Month12.RailwayProject;

import static Methods.Methods.*;

import java.text.SimpleDateFormat;
import java.util.*;

class Test {
    public static void main(String[] args) {
        RailwaySystem system = new RailwaySystem();
        system.start();
    }
}

/**
 * TODO
 *  - GENERIC TASKS
 *  - Authorization (with password saving using hash code maybe)
 *  - Dialog asking how long to continue simulation in loop
 *  - Sign out
 *  - Quit
 *  -
 *  - LOGGING
 *  - Tickets selling logging
 *  - Passenger's boardings logging
 *  - Train's movements logging
 *  - Save logs somewhere
 *  - Possibility to delete logs
 *  -
 *  - PASSENGER ACCOUNT
 *  - Account saving in database
 *  - Buying ticket to train (unavailable before previous is not cancelled or finished)
 *  - Cancelling ticket
 *  - Check history
 *  -
 *  - ADMINISTRATOR ACCOUNT privileges
 *  - Possibility to add new trains
 *  - Possibility to delete trains
 *  - Possibility to add new stations and routes
 *  - Possibility to edit train's route
 *  - Possibility to edit any info about train besides of ID
 */

class RailwaySystem {
    TimeHandler timeHandler = new TimeHandler();
    TrainsMovementHandler trainsHandler = new TrainsMovementHandler();
    // Doing all the work referred to data files
    DatabaseHandler databaseHandler = new DatabaseHandler();
    // Contains list of all possible ways and offers different methods to work with this list
    WaysHandler waysHandler = databaseHandler.getWaysHandler();
    // Contains all trains from database
    ArrayList<Train> trains = databaseHandler.getTrains();

    void start() {
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

        //databaseHandler.displayDatabase();
        //displayArray(trains, 1);



        for(Train train : trains) {
            train.start(timeHandler);
        }
        for (int i = 0; i < 24; i++) {
            timeHandler.nextHour();
            for(Train train : trains) {
                train.move();
            }
        }


/*
        Train train = trains.get(1);
        System.out.println(train);

        train.start(timeHandler);
        for (int i = 0; i < 12; i++) {
            timeHandler.nextHour();
            train.move();
        }

 */
    }

    class Handler {

    }

    private static class TrainsMovementHandler {
        ArrayList<Train> trains;
    }
}
