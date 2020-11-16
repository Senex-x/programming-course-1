package HomeworkTo1120;

import Methods.Methods;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Shop {
    private FileReader productNamesReader;
    private ArrayList<Product> soldProducts = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<ArrayList<Product>> storedProducts = new ArrayList<>();

    private ArrayList<Customer> generateCustomers(int amount) {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("data/ShopDataStorage/customerNames.txt"));
            for (int i = 0; i < amount && scanner.hasNext(); i++) {
                customers.add(new Customer("XXX", scanner.nextLine(), 100 + (float) i / 100));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }


    private ArrayList<ArrayList<Product>> generateProductsStock(int maxAmount) {
        ArrayList<ArrayList<Product>> storedProducts = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("data/ShopDataStorage/productNames.txt"));
            while (scanner.hasNext()) {
                String product = scanner.nextLine();
                int amount = Methods.getRandInt(1, maxAmount);

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

    void open() {
        storedProducts = generateProductsStock(10);
        customers = generateCustomers(100);
        for (Customer customer : customers) {
            System.out.println(customer);
        }

        displayStoredProducts();
    }

    void displayStoredProducts() {
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

    private int idCounter = 0;

    private String generateProductId(int length) {
        String id = Integer.toString(idCounter++);
        while (id.length() != length) {
            id = "0" + id;
        }
        return "P" + id;
    }

    public static void main(String[] args) {
        Shop newShop = new Shop();
        newShop.open();
    }

    private class Customer {
        private String id;
        private String name;
        private float balance;
        private ArrayList<Product> purchasedProducts = new ArrayList<>();
        private BalanceChangeLog balanceChangeLog;

        Customer(String id, String name, float balance) {
            this.id = id;
            this.name = name;
            this.balance = balance;
            balanceChangeLog = new BalanceChangeLog(balance);
        }

        void buy(Product product, String dateOfSale) {
            changeBalance(product.getCost() * -1);
            purchasedProducts.add(product);
            product.sellTo(this, dateOfSale);
        }

        private void changeBalance(float balanceChange) {
            balance += balanceChange;
            balanceChangeLog.add(balanceChange, balance);
        }

        boolean canAfford(Product product) {
            return balance - product.getCost() >= 0;
        }

        String getId() {
            return id;
        }

        String getName() {
            return name;
        }

        float getBalance() {
            return balance;
        }

        ArrayList<Product> getPurchasedProducts() {
            return purchasedProducts;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", balance=" + balance +
                    '}';
        }

        void getBalanceHistory() {
            balanceChangeLog.display();
        }

        private class BalanceChangeLog {
            private float initialBalance;
            private ArrayList<LogItem> log = new ArrayList<>();

            private BalanceChangeLog(float initialBalance) {
                this.initialBalance = initialBalance;
            }

            private void add(float balanceChange, float balanceRemain) {
                log.add(new LogItem(log.size() + 1, balanceChange, balanceRemain));
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
                private float balanceChange;
                private float balanceRemain;

                private LogItem(int number, float balanceChange, float balanceRemain) {
                    this.number = number;
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
                            "Balance change: " + balanceChange + "$ \n" +
                            "Balance remain: " + balanceRemain + "$";
                }
            }
        }
    }

    private class Product {
        private String id;
        private String name;
        private float cost;
        private String dateOfSale;
        private Customer buyer;

        Product(String id, String name, float cost) {
            this.id = id;
            this.name = name;
            this.cost = cost;
        }

        void sellTo(Customer buyer, String dateOfSale) {
            this.buyer = buyer;
            this.dateOfSale = dateOfSale;
        }

        public String getId() {
            return id;
        }

        Customer getBuyer() {
            return buyer;
        }

        String getName() {
            return name;
        }

        float getCost() {
            return cost;
        }

        String getDateOfSale() {
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
}
