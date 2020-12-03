package Homeworks.Month12.RailwayProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHandler {
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


    public DatabaseHandler() {
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

    public void add(Train train) {
        Statement statement;
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
            statement = connection.createStatement();
            //statement.executeUpdate(query);
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(Train train) {
        String query = "UPDATE " + TABLE_NAME + " SET\n" +
                COLUMNS[1] + " = '" + train.getName() + "',\n" +
                COLUMNS[2] + " = " + train.getSpeed()+ ",\n" +
                COLUMNS[3] + " = " + train.getCapacity()+ ",\n" +
                COLUMNS[4] + " = " + train.getTicketCost()+ ",\n" +
                COLUMNS[5] + " = " + train.getTrainType().getId()+ ",\n" +
                COLUMNS[6] + " = '" + train.getRouteCode() + "'\n" +
                "WHERE\n" +
                COLUMNS[0] + " = " + train.getId() + ";";

        System.out.println(query);
    }

    public ArrayList<Train> getTrains() {
        ArrayList<Train> trains = new ArrayList<>();

        return trains;
    }
}
