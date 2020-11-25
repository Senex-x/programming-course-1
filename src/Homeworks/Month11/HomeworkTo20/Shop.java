package Homeworks.Month11.HomeworkTo20;

import Methods.Methods;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
INSERT INTO customers (name)
VALUES ('Makoto Naegi')
INSERT INTO customers (name)
VALUES ('Akane Owari');
INSERT INTO customers (name)
VALUES ('Kazuici Soda');

 */

class ShopTest {
    public static void main(String[] args) {
        Shop newShop = new Shop();
        newShop.start();
    }
}

public class Shop {
    private static final int PRODUCTS_STOCK_AMOUNT = 20;
    private static final int CUSTOMERS_MONEY_AMOUNT_MIN = 20;
    private static final int CUSTOMERS_MONEY_AMOUNT_MAX = 200;

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
        storedProducts = generateProductsStock(PRODUCTS_STOCK_AMOUNT);
        customers = generateCustomers();

        displayStoredProducts();

        do {
            startNewDay();
        } while (isOpen());

        customers.get(0).displayBalanceHistory();

        displayStoredProducts();
        displaySoldProducts();
    }

    // crutch, better to fix
    boolean isSomethingBought;

    private void startNewDay() {
        isSomethingBought = false;
        System.out.println(paint("Buyings log for " + date.getFormattedDate() + ": ", PURPLE));
        Methods.line("-");
        for (Customer customer : customers) {
            if (Methods.getRandDouble() > 0.5) continue; // skipping customer
            for (int i = 0; i < Methods.getRandInt(1, 10) && storedProducts.size() > 0; i++) {
                int sectionIndex = Methods.getRandInt(0, storedProducts.size());
                Product product = storedProducts.get(sectionIndex).get(0);
                if (customer.canAfford(product)) {
                    isSomethingBought = true;
                    System.out.println("Customer " + customer.getIdentifier() +
                            " bought " + product.getIdentifier()
                    );
                    buyProduct(customer, sectionIndex);
                } else {
                    System.out.println("Customer " + customer.getIdentifier() +
                            " don't have enough money to buy " + product.getIdentifier() +
                            " for " + product.getCost() + "$"
                    );
                }
            }
            Methods.line("-");
            date.nextDay();
        }
    }

    boolean isOpen() {
        return storedProducts.size() != 0 && isSomethingBought;
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
            System.out.println("Stored product: " + section.get(0).getIdentifier() + "\n" +
                    "Total amount: " + section.size());
            Methods.line("-");
        }
    }

    private void displaySoldProducts() {
        System.out.println(
                paint("Sales history \n" +
                        "Products sold total: " + soldProducts.size(), PURPLE)
        );
        Methods.line("-");
        for (Product product : soldProducts) {
            System.out.println("Sold product: " + product);

        }
        Methods.line("-");
    }

    private static ArrayList<Customer> generateCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("data/ShopDataStorage/customers.txt"));
            while (scanner.hasNext()) {
                customers.add(
                        new Customer(
                                generateCustomerId(3),
                                scanner.nextLine(),
                                Methods.getRandInt(CUSTOMERS_MONEY_AMOUNT_MIN, CUSTOMERS_MONEY_AMOUNT_MAX)
                        )
                );
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

    private static final String RED = Methods.Colors.RED.code();
    private static final String PURPLE = Methods.Colors.PURPLE.code();
    private static final String CYAN = Methods.Colors.CYAN.code();
    private static final String GREEN = Methods.Colors.GREEN.code();

    private String paint(String string, String colorCode) {
        return Methods.paint(string, colorCode);
    }

    private static class Customer {
        private String id;
        private String name;
        private float balance;
        private BalanceChangeLog balanceChangeLog;

        private Customer(String id, String name, float balance) {
            this.id = id;
            this.name = name;
            this.balance = balance;
            balanceChangeLog = new BalanceChangeLog(balance);
        }

        private void buy(Product product, String dateOfSale) {
            changeBalance(product.getCost() * -1, product);
            product.sellTo(this, dateOfSale);
        }

        private void changeBalance(float balanceChange, Product boughtProduct) {
            balance += balanceChange;
            balanceChangeLog.add(balanceChange, balance, boughtProduct);
        }

        private boolean canAfford(Product product) {
            return balance - product.getCost() >= 0;
        }

        public String getIdentifier() {
            return name + " (id: " + id + ")";
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
            private final ArrayList<LogItem> log = new ArrayList<>();

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

        private String getIdentifier() {
            return name + " (id: " + id + ")";
        }

        private String getName() {
            return name;
        }

        private float getCost() {
            return cost;
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

    private static class DateHolder {
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

        private String getFormattedDate() {
            return day + "." + month + "." + year;
        }

        @Override
        public String toString() {
            return "Current date: " + getFormattedDate();
        }
    }
}
