package Homeworks.Month12.RailwayProject;

import com.google.gson.Gson;

import static Methods.Methods.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

class DatabaseHandler {
    // Contains trains for easy input, not needed in final build
    private static final String TRAINS_TXT_PATH = "src/Homeworks/Month12/RailwayProject/data/trains.txt";
    private static final String PASSENGERS_TXT_PATH = "src/Homeworks/Month12/RailwayProject/data/passengers.txt";
    // Contains all station names in proper order
    private static final String STATIONS_TXT_PATH = "src/Homeworks/Month12/RailwayProject/data/stations.txt";
    // Contains all possible ways and their lengths for each station name
    // Being associated by station ID's: ID = line in waymatrix with proper ways description
    private static final String WAYMATRIX_TXT_PATH = "src/Homeworks/Month12/RailwayProject/data/waymatrix.txt";
    private static final String DB_NAME = "railway_project_data";
    private static final String TRAINS_TABLE_NAME = "timetable";
    private static final String PASSENGERS_TABLE_NAME = "passengers";
    private static final String DB_PATH_PC = "D:\\Projects\\Java\\" +
            "PolyakovV_11005\\src\\Homeworks\\Month12\\" +
            "RailwayProject\\data\\" + DB_NAME + ".db";
    private static final String DB_PATH = "C:\\Projects\\Java\\" +
            "StudyProject\\src\\Homeworks\\Month12\\" +
            "RailwayProject\\data\\" + DB_NAME + ".db";

    private static final String[] COLUMNS = {
            "id",
            "train_name",
            "train_speed",
            "train_capacity",
            "train_ticket_cost",
            "train_type",
            "train_route"
    };
    private Connection connection;
    private static final ArrayList<String> waymatrixRows = getWayMatrix();
    private static final ArrayList<String> stationNames = getStationNames();
    private static final WaysHandler waysHandler = getAllWays();
    private static final ArrayList<Station> stations = parseStations(); // requires waysHandler
    // Contains all the trains from database
    private final ArrayList<Train> trains;
    private final ArrayList<Passenger> passengers;

    DatabaseHandler() {
        openDatabase();
        waysHandler.setStations(stations); // requires stations list
        trains = getTrainsFromDB(); // requires connection and waysHandler's list of stations
        passengers = getPassengersFromDB();
    }

    private void openDatabase() {
        try {
            Class.forName("org.sqlite.JDBC"); // class loading
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    void addToDatabase(Train train) {
        String query = "INSERT INTO " + TRAINS_TABLE_NAME + " " +
                "( train_name, train_speed, train_capacity, train_ticket_cost, train_type, train_route )\n" +
                "VALUES ( " +
                train.getName() + "', " +
                train.getSpeed() + ", " +
                train.getCapacity() + ", " +
                train.getTicketCost() + ", " +
                train.getTrainType().getId() + ", '" +
                train.getRouteCode() + "' );";

        System.out.println(query);
        // executeUpdate(query);
    }

    void updateDatabase(Train train) {
        String query = "UPDATE " + TRAINS_TABLE_NAME + " SET\n" +
                COLUMNS[1] + " = '" + train.getName() + "',\n" +
                COLUMNS[2] + " = " + train.getSpeed() + ",\n" +
                COLUMNS[3] + " = " + train.getCapacity() + ",\n" +
                COLUMNS[4] + " = " + train.getTicketCost() + ",\n" +
                COLUMNS[5] + " = " + train.getTrainType().getId() + ",\n" +
                COLUMNS[6] + " = '" + train.getRouteCode() + "'\n" +
                "WHERE\n" +
                COLUMNS[0] + " = " + train.getId() + ";";

        System.out.println(query);
        // executeUpdate(query);
    }

    ArrayList<Train> getTrains() {
        return trains;
    }

    private ArrayList<Train> getTrainsFromDB() {
        ArrayList<Train> trains = new ArrayList<>();
        String query = "SELECT * FROM " + TRAINS_TABLE_NAME + ";";

        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                trains.add(new Train(
                        results.getInt(COLUMNS[0]),
                        results.getString(COLUMNS[1]),
                        results.getInt(COLUMNS[2]),
                        results.getInt(COLUMNS[3]),
                        results.getInt(COLUMNS[4]),
                        TrainType.values()[results.getInt(COLUMNS[5])],
                        results.getString(COLUMNS[6]),
                        waysHandler.getRouteForTrain(results.getString(COLUMNS[6])))
                );

            }
            results.close();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return trains;
    }

    void displayDatabase() {
        System.out.println("Total amount of elements: " + trains.size());
        line("-");
        for (Train train : trains) {
            System.out.println(train);
        }
        line("-");
    }

    void addAllTrainsFromTxtToDatabase() {
        ArrayList<Train> trains = getTrainsFromTxt();
        for (Train train : trains) {
            addToDatabase(train);
        }
    }

    // trains.txt parser used just for faster initial set of trains input
    private static ArrayList<Train> getTrainsFromTxt() {
        int idCounter = 0;
        ArrayList<Train> trains = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader(TRAINS_TXT_PATH));
            while (scanner.hasNext()) {
                trains.add(new Train(
                        idCounter++,
                        scanner.next(),
                        scanner.nextInt(),
                        scanner.nextInt(),
                        scanner.nextInt(),
                        TrainType.values()[scanner.nextInt()],
                        scanner.next())
                );
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trains;
    }

    // Get all stations and set their ways
    private static ArrayList<Station> parseStations() {
        ArrayList<Station> stations = new ArrayList<>();
        for (int i = 0; i < stationNames.size(); i++) {
            stations.add(new Station(
                    i,
                    stationNames.get(i),
                    waysHandler.getWaysForStation(stationNames.get(i))
            ));
        }
        return stations;
    }

    // stations.txt parser returns ArrayList of station names
    // To add a new station, stations.txt and waymatrix.txt updates needed
    static ArrayList<String> getStationNames() {
        ArrayList<String> stationNames = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader(STATIONS_TXT_PATH));
            while (scanner.hasNext()) {
                stationNames.add(scanner.next());
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stationNames;
    }

    // waymatrix.txt parser returns ArrayList of Strings each of which contains one row
    static ArrayList<String> getWayMatrix() {
        ArrayList<String> wayRows = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader(WAYMATRIX_TXT_PATH));
            while (scanner.hasNext()) {
                wayRows.add(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wayRows;
    }

    // waymatrixRows parser for all ways referring to each station
    private static WaysHandler getAllWays() {
        ArrayList<Way> ways = new ArrayList<>();
        for (int i = 0; i < waymatrixRows.size(); i++) {
            String wayRow = waymatrixRows.get(i);
            if (wayRow.length() == 0) continue;
            String[] splittedRow = wayRow.split(" ");
            for (int j = 0; j < splittedRow.length; j += 2) {
                ways.add(new Way(
                        stationNames.get(i),
                        stationNames.get(Integer.parseInt(splittedRow[j])),
                        Integer.parseInt(splittedRow[j + 1])
                ));
            }
        }
        return new WaysHandler(ways);
    }

    void addToTablePassengers(Passenger passenger) {
        String query = "INSERT INTO " + PASSENGERS_TABLE_NAME + " " +
                "( passenger_name, passenger_password, passenger_history )\n" +
                "VALUES ( \n\t'" +
                passenger.getName() + "', \n\t'" +
                passenger.getPassword() + "', \n\t'" +
                new Gson().toJson(passenger.getHistoryHolder()) + "'\n );";
        System.out.println(query);
        executeUpdate(query);
    }

    private ArrayList<Passenger> getPassengersFromDB() {
        ArrayList<Passenger> passengers = new ArrayList<>();
        String query = "SELECT * FROM " + PASSENGERS_TABLE_NAME + ";";
        ResultSet results = executeQuery(query);
        while (true) {
            try {
                if (!results.next()) break;
                passengers.add(new Passenger(
                        results.getInt("passenger_id"),
                        results.getString("passenger_name"),
                        results.getString("passenger_password"),
                        new Gson().fromJson(results.getString("passenger_history"), Passenger.HistoryHolder.class)
                ));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return passengers;
    }

    private ArrayList<Passenger> getPassengersFromTxt() {
        ArrayList<Passenger> passengers = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader(PASSENGERS_TXT_PATH));
            while(scanner.hasNext()) {
                String name = scanner.nextLine();
                passengers.add(new Passenger(
                        0,
                        name,
                        generatePassword(10),
                        new ArrayList<>()
                ));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return passengers;
    }

    private String generatePassword(int length) {
        StringBuilder password = new StringBuilder();
        for(int i=0;i<length;i++) {
            password.append((char)getRandInt(48, 123));
        }
        return password.toString();
    }

    void deleteRowById(String tableName, int id) {
        String query = "default";
        if (tableName.equals(PASSENGERS_TABLE_NAME)) {
            query = "DELETE FROM " + tableName + "\n" +
                    "WHERE passenger_id = " + id + ";";
        } else if (tableName.equals(TRAINS_TABLE_NAME)) {
            query = "DELETE FROM " + tableName + "\n" +
                    "WHERE id = " + id + ";";
        }
        executeUpdate(query);
    }

    ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    WaysHandler getWaysHandler() {
        return waysHandler;
    }

    static ArrayList<Station> getStations() {
        return stations;
    }

    void executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }


    // Legacy method once used to divide old route codes which was without spaces
    private void divide() {
        for (int i = 0; i < trains.size(); i++) {
            Train train = trains.get(i);
            String initialRoute = train.getRouteCode();
            StringBuilder newRoute = new StringBuilder();
            if (initialRoute.charAt(1) == ' ') continue;
            for (int j = 0; j < initialRoute.length() - 1; j++) {
                newRoute.append(initialRoute.charAt(j) - 48);
                newRoute.append(" ");
            }
            newRoute.append(initialRoute.charAt(initialRoute.length() - 1) - 48);
            train.setRouteCode(newRoute.toString());

            //updateDatabase(train);
        }
    }

    private void createTablePassengers() {
        String query = "CREATE TABLE " + PASSENGERS_TABLE_NAME + " (\n" +
                "\tpassenger_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\tpassenger_name TEXT NOT NULL,\n" +
                "\tpassenger_password INTEGER NOT NULL,\n" +
                "\tpassenger_history TEXT NOT NULL\n" +
                ");";
        executeUpdate(query);
    }
}
