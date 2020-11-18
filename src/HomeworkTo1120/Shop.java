package HomeworkTo1120;

import Methods.Methods;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

class ShopTest {
    public static void main(String[] args) {
        Shop newShop = new Shop();
        newShop.start();
    }
}

public class Shop {
    private ArrayList<Product> soldProducts = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<ArrayList<Product>> storedProducts = new ArrayList<>();
    private DateHolder date = new DateHolder();

    {
        Date currentDate = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatDate.format(currentDate);

        this.date.setState(Integer.parseInt(date.substring(0, 2)),
                Integer.parseInt(date.substring(3, 5)),
                Integer.parseInt(date.substring(6)));
    }

    public void start() {
        storedProducts = generateProductsStock(8);
        customers = generateCustomers(100);

        displayStoredProducts();

        System.out.println("Buyings log: ");
        Methods.line("-");
        for(int j =0;j<2;j++) {
            for (Customer customer : customers) {
                for (int i = 0; i < Methods.getRandInt(1, 10) && storedProducts.size() > 0; i++) {
                    int sectionIndex = Methods.getRandInt(0, storedProducts.size() - 1);
                    Product product = storedProducts.get(sectionIndex).get(0);
                    if (customer.canAfford(product)) {
                        System.out.println("Customer " + customer + " bought " + product);
                        buyProduct(customer, sectionIndex);
                    } else {
                        System.out.println("Customer " + customer + " don't have enough money to buy " + product);
                    }
                }
                Methods.line("-");
            }
            date.nextDay();
        }

        for (Customer customer : customers) {
            customer.displayBalanceHistory();
        }

        displayStoredProducts();
        displaySoldProducts();
    }

    boolean open(String date) {
        return true;
    }

    private void buyProduct(Customer customer, int sectionIndex) {
        ArrayList<Product> row = storedProducts.get(sectionIndex);
        int productIndex = row.size() - 1;
        Product product = row.get(productIndex);

        customer.buy(product, date.getFormattedDate());
        row.remove(productIndex);
        soldProducts.add(product);
        if (storedProducts.get(sectionIndex).size() == 0) {
            Methods.line(paint("////////PRODUCT \"" + product.getName().toUpperCase() + "\" RAN OUT", RED));
            storedProducts.remove(sectionIndex);
        }

    }

    private void displayStoredProducts() {
        for (ArrayList<Product> section : storedProducts) {
            System.out.println("Stored product: " + section.get(0).getName() + "\n" +
                    "Total amount: " + section.size());
            Methods.line("-");
            for (Product product : section) {
                System.out.println(product);
            }
            Methods.line("-");
        }
    }

    private void displaySoldProducts() {
        System.out.println("Sales history \n" +
                "Products sold total: " + soldProducts.size());
        Methods.line("-");
        for (Product product : soldProducts) {
            System.out.println("Sold product: " + product);

        }
        Methods.line("-");
    }

    private static ArrayList<Customer> generateCustomers(int amount) {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("data/ShopDataStorage/customers.txt"));
            for (int i = 0; i < amount && scanner.hasNext(); i++) {
                customers.add(new Customer(generateCustomerId(3), scanner.nextLine(), 100 + (float) i / 100));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private static ArrayList<ArrayList<Product>> generateProductsStock(int amount) {
        ArrayList<ArrayList<Product>> storedProducts = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("data/ShopDataStorage/products.txt"));
            while (scanner.hasNext()) {
                String product = scanner.nextLine();
                productPlacement:
                for (int i = 0; i < amount; i++) {
                    Product newProduct = new Product(
                            generateProductId(3),
                            product.substring(0, product.indexOf(' ')),
                            Float.parseFloat(product.substring(product.indexOf(' ')))
                    );
                    for (ArrayList<Product> section : storedProducts) {
                        if (section.get(0).getName().equals(newProduct.getName())) {
                            section.add(newProduct);
                            continue productPlacement;
                        }
                    }
                    ArrayList<Product> section = new ArrayList<>();
                    section.add(newProduct);
                    storedProducts.add(section);
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storedProducts;
    }

    private static int productIdCounter = 0;

    private static String generateProductId(int length) {
        String id = Integer.toString(productIdCounter++);
        while (id.length() != length) {
            id = "0" + id;
        }
        return "P" + id;
    }

    private static int customerIdCounter = 0;

    private static String generateCustomerId(int length) {
        String id = Integer.toString(customerIdCounter++);
        while (id.length() != length) {
            id = "0" + id;
        }
        return "C" + id;
    }

    private String RES = Methods.Colors.RESET.code();
    private String RED = Methods.Colors.RED.code();
    private String PURPLE = Methods.Colors.PURPLE.code();
    private String CYAN = Methods.Colors.CYAN.code();
    private String GREEN = Methods.Colors.GREEN.code();

    private String paint(String string, String colorCode) {
        return Methods.paint(string, colorCode);
    }

    private static class Customer {
        private String id;
        private String name;
        private float balance;
        private ArrayList<Product> purchasedProducts = new ArrayList<>();
        private BalanceChangeLog balanceChangeLog;

        private Customer(String id, String name, float balance) {
            this.id = id;
            this.name = name;
            this.balance = balance;
            balanceChangeLog = new BalanceChangeLog(balance);
        }

        private void buy(Product product, String dateOfSale) {
            changeBalance(product.getCost() * -1, product);
            purchasedProducts.add(product);
            product.sellTo(this, dateOfSale);
        }

        private void changeBalance(float balanceChange, Product boughtProduct) {
            balance += balanceChange;
            balanceChangeLog.add(balanceChange, balance, boughtProduct);
        }

        private boolean canAfford(Product product) {
            return balance - product.getCost() >= 0;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        private void displayBalanceHistory() {
            balanceChangeLog.display();
        }

        private class BalanceChangeLog {
            private float initialBalance;
            private ArrayList<LogItem> log = new ArrayList<>();

            private BalanceChangeLog(float initialBalance) {
                this.initialBalance = initialBalance;
            }

            private void add(float balanceChange, float balanceRemain, Product boughtProduct) {
                log.add(new LogItem(log.size() + 1, boughtProduct, balanceChange, balanceRemain));
            }

            private void display() {
                System.out.println(
                        "Balance history of customer " + name + " (id: " + id + ") \n"
                                + "Initial balance: " + initialBalance + "$"
                );
                for (LogItem logItem : log) {
                    Methods.line("-");
                    System.out.println(logItem);
                }
                Methods.line("-");
            }

            private class LogItem {
                private int number;
                private Product boughtProduct;
                private float balanceChange;
                private float balanceRemain;

                private LogItem(int number, Product boughtProduct, float balanceChange, float balanceRemain) {
                    this.number = number;
                    this.boughtProduct = boughtProduct;
                    this.balanceChange = balanceChange;
                    this.balanceRemain = balanceRemain;
                }

                private float getBalanceChange() {
                    return balanceChange;
                }

                private float getBalanceRemain() {
                    return balanceRemain;
                }

                @Override
                public String toString() {
                    return "Operation number: " + number + "\n" +
                            "Bought product: " + boughtProduct.toString() + "\n" +
                            "Balance change: " + balanceChange + "$ \n" +
                            "Balance remain: " + balanceRemain + "$";
                }
            }
        }
    }

    private static class Product {
        private String id;
        private String name;
        private float cost;
        private String dateOfSale;
        private Customer buyer;

        private Product(String id, String name, float cost) {
            this.id = id;
            this.name = name;
            this.cost = cost;
        }

        private void sellTo(Customer buyer, String dateOfSale) {
            this.buyer = buyer;
            this.dateOfSale = dateOfSale;
        }

        private String getId() {
            return id;
        }

        private Customer getBuyer() {
            return buyer;
        }

        private String getName() {
            return name;
        }

        private float getCost() {
            return cost;
        }

        private String getDateOfSale() {
            return dateOfSale;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", cost=" + cost +
                    ", dateOfSale='" + dateOfSale + '\'' +
                    ", buyer=" + buyer +
                    '}';
        }
    }

    private class DateHolder {
        private int day;
        private int month;
        private int year;

        public void nextDay() {
            if (day < 31) {
                day++;
            } else if (month < 12) {
                day = 1;
                month++;
            } else {
                day = 1;
                month = 1;
                year++;
            }
        }

        public void setState(int day, int month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        private String getFormattedDate() {
            return day + "." + month + "." + year;
        }

        @Override
        public String toString() {
            return "Current date: " + getFormattedDate();
        }
    }
}
