package OtherWorks.Samples;

import static Methods.Methods.*;

import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

class DataBasetest {
    public static void main(String[] args) {
        DBTest.TypeSetter typesSet = new DBTest.TypeSetter();
        typesSet.add("id", 0);
        typesSet.add("name", "");

        DBTest dbHandler = new DBTest();
        dbHandler.create("names", typesSet);
    }

}

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

    public static class TypeSetter {
        private final LinkedHashMap<String, Class<?>> values = new LinkedHashMap<>();

        public void add(String name, Integer type) {
            values.put(name, type.getClass());
        }

        public void add(String name, String type) {
            values.put(name, type.getClass());
        }

        private LinkedHashMap<String, Class<?>> getValues() {
            return values;
        }
    }

    public void create(String tableName, TypeSetter typeSetter) {
        LinkedHashMap<String, Class<?>> dataSet = typeSetter.getValues();
        String[] keySet = dataSet.keySet().toArray(new String[0]);
        String[] typeSet = new String[dataSet.size()];
        Collection<Class<?>> types = dataSet.values();
        Iterator<Class<?>> it = types.iterator();

        for(int i=0;it.hasNext();i++) {
            Class<?> type = it.next();
            if (type.getTypeName().equals(String.class.getTypeName())) {
                typeSet[i] = "TEXT";
            } else if (type.getTypeName().equals(Integer.class.getTypeName())) {
                typeSet[i] = "INTEGER";
            }
        }

        String isAutoIncrement = "";
        if(typeSet[0].equals("INTEGER")) isAutoIncrement = " AUTOINCREMENT";

        String query = "CREATE TABLE " + tableName + " (\n" +
                keySet[0] + " " + typeSet[0] + " PRIMARY KEY" + isAutoIncrement + ",\n";

        for (int i = 1; i < dataSet.size() - 1; i++) {
            query += keySet[i] + " " + typeSet[i] + ",\n";
        }

        query += keySet[dataSet.size() - 1] + " " + typeSet[dataSet.size() - 1] + "\n" +
                ");";

        System.out.println(query);
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
