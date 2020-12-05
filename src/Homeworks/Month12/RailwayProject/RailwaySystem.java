package Homeworks.Month12.RailwayProject;

import static Methods.Methods.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

class Test {
    public static void main(String[] args) {
        RailwaySystem system = new RailwaySystem();
        system.start();
    }
}

class RailwaySystem {
    TimeHandler timeHandler = new TimeHandler();
    TrainsMovementHandler trainsHandler = new TrainsMovementHandler();
    // Doing all the work referred to data files
    DatabaseHandler databaseHandler = new DatabaseHandler();
    // Contains list of all possible ways and offers different methods to work with this list
    WaysHandler waysHandler = databaseHandler.getWaysHandler();
    // Contains all trains from database
    ArrayList<Train> trains = databaseHandler.getTrains();

    public void start() {
        Train testTrain = new Train(
                0,
                "EP2K",
                160,
                200,
                600,
                TrainType.ECONOMY,
                "0 1 2 3"
        );

        databaseHandler.displayDatabase();
    }

    class Handler {

    }

    private static class TrainsMovementHandler {
        ArrayList<Train> trains;
    }


    private static class TimeHandler {
        private final GregorianCalendar calendar;
        private final SimpleDateFormat formatter;
        private String formattedDate;

        private TimeHandler() {
            Calendar currentDate = Calendar.getInstance();
            calendar = new GregorianCalendar(
                    currentDate.get(Calendar.YEAR),
                    currentDate.get(Calendar.MONTH),
                    currentDate.get(Calendar.DAY_OF_MONTH));
            formatter = new SimpleDateFormat("HH 'ч.' dd MMMM yyyy", Locale.getDefault());
            updateFormattedDate();
        }

        private void nextHour() {
            calendar.add(Calendar.HOUR, 1);
            updateFormattedDate();
        }

        private void updateFormattedDate() {
            formattedDate = formatter.format(calendar.getTime());
        }

        private int getHour() {
            return calendar.get(Calendar.HOUR);
        }

        private int getDay() {
            return calendar.get(Calendar.MONTH);
        }

        @Override
        public String toString() {
            return formattedDate;
        }
    }
}
