package Homeworks.Month12.RailwayProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHandler {
    private static final String DB_PATH =
            "D:\\Projects\\Java\\PolyakovV_11005\\src\\Homeworks\\Month12\\RailwayProject\\data\\timetable.db";
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

    /*
INSERT INTO timetable ( train_name, train_speed, train_capacity, train_ticket_cost, train_type, train_route )
VALUES ( 'Velaro EVS Sapsan', 300, 200, 2000, 0, 12345);
*/
    public void add(Train train) {
        Statement statement;
        String query = "INSERT INTO timetable " +
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

    public ArrayList<Train> getTrains() {
        ArrayList<Train> trains = new ArrayList<>();

        return trains;
    }
}
