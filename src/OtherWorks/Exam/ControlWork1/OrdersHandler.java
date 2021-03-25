package OtherWorks.Exam.ControlWork1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

class Test {
    public static void main(String[] args) {
        OrdersHandler ordersHandler = new OrdersHandler();
        System.out.println(ordersHandler.toString());
        ordersHandler.firstSort();
        ordersHandler.secondSort();
    }
}



public class OrdersHandler {
    private static final String PATH = "src/OtherWorks/Exam/ControlWork1/data/list.txt";
    ArrayList<Buyer> buyers = new ArrayList<>();

    public OrdersHandler() {
        getBuyers();
    }

    private void getBuyers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split("\\|");
                addOrder(strings[0], strings[1], strings[2], Integer.parseInt(strings[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addOrder(String name, String city, String item, int count) {
        Buyer buyer = getBuyer(name);
        buyer.addOrder(city, item, count);
    }

    Buyer getBuyer(String name) {
        for (int i = 0; i < buyers.size(); i++) {
            if (buyers.get(i).getName().equals(name)) {
                return buyers.get(i);
            }
        }

        buyers.add(new Buyer(name));
        return getBuyer(name);
    }

    @Override
    public String toString() {
        return "OrdersHandler{" +
                "buyers=" + buyers +
                '}';
    }

    private static final String PATH_SORTED_BY_NAME = "src/Homeworks/SecondSemester/Month03/HomeworkTo25/data/sortedByName.txt";
    private static final String PATH_SORTED_BY_ORDERS = "src/Homeworks/SecondSemester/Month03/HomeworkTo25/data/sortedByOrders.txt";

    public void firstSort() {
        TreeSet<Buyer> buyers = new TreeSet<>(
                new BuyerByNameComparator().thenComparing(
                        new BuyerByCityCountComparator().thenComparing(
                                new BuyerByOrdersCountComparator())));
        buyers.addAll(this.buyers);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_SORTED_BY_NAME))) {
            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
            writer.write(builder.create().toJson(buyers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void secondSort() {
        TreeSet<Buyer> buyers = new TreeSet<>(
                new BuyerByOrdersCountComparator().thenComparing(
                        new BuyerByCityCountComparator().thenComparing(
                                new BuyerByNameComparator())));
        buyers.addAll(this.buyers);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_SORTED_BY_ORDERS))) {
            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
            writer.write(builder.create().toJson(buyers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class BuyerByNameComparator implements Comparator<OrdersHandler.Buyer> {
        @Override
        public int compare(Buyer o1, Buyer o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    static class BuyerByCityCountComparator implements Comparator<OrdersHandler.Buyer> {
        @Override
        public int compare(Buyer o1, Buyer o2) {
            Integer o1Count = o1.getCitiesCount();
            Integer o2Count = o2.getCitiesCount();
            return o1Count.compareTo(o2Count);
        }
    }

    static class BuyerByOrdersCountComparator implements Comparator<OrdersHandler.Buyer> {
        @Override
        public int compare(Buyer o1, Buyer o2) {
            Integer o1Count = o1.getOrdersCount();
            Integer o2Count = o2.getOrdersCount();
            return o1Count.compareTo(o2Count);
        }
    }

    static class Buyer {
        String name;
        ArrayList<City> cities = new ArrayList<>();

        public Buyer(String name) {
            this.name = name;
        }

        void addOrder(String city, String name, int count) {
            for (City c : cities) {
                if (c.getName().equals(city)) {
                    c.addOrder(name, count);
                    return;
                }
            }

            cities.add(new City(city));
            addOrder(city, name, count);
        }

        public String getName() {
            return name;
        }

        public int getCitiesCount() {
            return cities.size();
        }

        public int getOrdersCount() {
            int count = 0;
            for(City c:cities) {
                count += c.getOrdersCount();
            }
            return count;
        }

        @Override
        public String toString() {
            return "\nBuyer{" +
                    "name='" + name + '\'' +
                    ", cities=" + cities +
                    '}';
        }

        static class City {
            String name;
            ArrayList<Order> orders = new ArrayList<>();

            public City(String name) {
                this.name = name;
            }

            void addOrder(String name, int count) {
                orders.add(new Order(name, count));
            }

            public String getName() {
                return name;
            }

            public int getOrdersCount() {
                return orders.size();
            }

            @Override
            public String toString() {
                return "\t\t\nCity{" +
                        "name='" + name + '\'' +
                        ", orders=" + orders +
                        '}';
            }

            static class Order {
                String name;
                int count;

                public Order(String name, int count) {
                    this.name = name;
                    this.count = count;
                }

                @Override
                public String toString() {
                    return "Order{" +
                            "name='" + name + '\'' +
                            ", count=" + count +
                            '}';
                }
            }
        }
    }


}
