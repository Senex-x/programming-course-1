package Homeworks.Month12.RailwayProject;

import java.sql.*;
import java.util.ArrayList;

class DatabaseHandler {
    private static final String TABLE_NAME = "timetable";
    private static final String DB_PATH = "D:\\Projects\\Java\\" +
            "PolyakovV_11005\\src\\Homeworks\\Month12" +
            "\\RailwayProject\\data\\" + TABLE_NAME + ".db";
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


    DatabaseHandler() {
        openDatabase();
    }

    private void openDatabase() {
        try {
            Class.forName("org.sqlite.JDBC"); // class loading
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    void add(Train train) {

        String query = "INSERT INTO " + TABLE_NAME + " " +
                "( train_name, train_speed, train_capacity, train_ticket_cost, train_type, train_route )\n" +
                "VALUES ( '" +
                train.getName() + "', " +
                train.getSpeed() + ", " +
                train.getCapacity() + ", " +
                train.getTicketCost() + ", " +
                train.getTrainType().getId() + ", '" +
                train.getRouteCode() +
                "' );";

        System.out.println(query);

        try {
            Statement statement = connection.createStatement();
            //statement.executeUpdate(query);
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    void update(Train train) {
        String query = "UPDATE " + TABLE_NAME + " SET\n" +
                COLUMNS[1] + " = '" + train.getName() + "',\n" +
                COLUMNS[2] + " = " + train.getSpeed() + ",\n" +
                COLUMNS[3] + " = " + train.getCapacity() + ",\n" +
                COLUMNS[4] + " = " + train.getTicketCost() + ",\n" +
                COLUMNS[5] + " = " + train.getTrainType().getId() + ",\n" +
                COLUMNS[6] + " = '" + train.getRouteCode() + "'\n" +
                "WHERE\n" +
                COLUMNS[0] + " = " + train.getId() + ";";
        System.out.println(query);

        try {
            Statement statement = connection.createStatement();
            //statement.executeUpdate(query);
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    void displayDatabase() {
        String query = "SELECT * FROM " + TABLE_NAME + ";";

        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                System.out.println(new Train(
                        results.getInt(COLUMNS[0]),
                        results.getString(COLUMNS[1]),
                        results.getInt(COLUMNS[2]),
                        results.getInt(COLUMNS[3]),
                        results.getInt(COLUMNS[4]),
                        TrainType.values()[results.getInt(COLUMNS[5])],
                        results.getString(COLUMNS[6]))
                );
            }
            results.close();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    // Database parser
    ArrayList<Train> getTrains() {
        ArrayList<Train> trains = new ArrayList<>();

        return trains;
    }
}
