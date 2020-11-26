package OtherWorks.Samples;

import static Methods.Methods.*;

import java.sql.*;

public class DBTest {
    String DB_PATH = "C:\\Projects\\Java\\StudyProject\\data\\CustomersDB\\customers.db";
    Connection connection;

    public static void main(String[] args) {
        DBTest dbTest = new DBTest();
        dbTest.open();
        //dbTest.insert();
        dbTest.selectAll();
        String input = getLine();
        dbTest.edit(
                Integer.parseInt(input.substring(0, input.indexOf(' '))),
                input.substring(input.indexOf(' ') + 1)
        );
        dbTest.selectAll();
        dbTest.close();
    }

    private void edit(int idToEdit, String newName) {
        String query = "UPDATE customers SET name = \"" + newName +
                "\" WHERE id = " + idToEdit + ";";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void selectAll() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT id, name " +
                    "FROM customers " +
                    "ORDER BY id;";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                System.out.println("id: " + id + ", name: " + name);
            }
            results.close();
            statement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void insert() {
        System.out.println("Enter a name:");
        String name = getLine();
        if (name.length() == 0) return;
        String query = "INSERT INTO customers (name)" +
                "VALUES ('" + name + "');";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            System.out.println("Data added");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void open() {
        try {
            Class.forName("org.sqlite.JDBC"); // class loading
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + DB_PATH);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}

    /*
.cd C:\\Projects\\Java\\StudyProject\\data\\CustomersDB
.open customers.db
SELECT * FROM customers;
     */
