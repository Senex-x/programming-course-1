package HomeworkTo1120;

import Methods.Methods;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Shop {
    private ArrayList<Product> soldProducts = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<ArrayList<Product>> storedProducts = new ArrayList<>();

    void open() {
        storedProducts = generateProductsStock(10);
        customers = generateCustomers(100);

        displayStoredProducts();

        for (Customer customer : customers) {
            for (int i = 0; i < Methods.getRandInt(0, 10) && storedProducts.size() > 0; i++) {
                int sectionIndex = Methods.getRandInt(0, storedProducts.size() - 1);
                Product product = storedProducts.get(sectionIndex).get(0);
                if (customer.canAfford(product)) {
                    buyProduct(customer, sectionIndex);
                }
            }
        }

        for (Customer customer : customers) {
            customer.displayBalanceHistory();
        }

        displaySoldProducts();
        displayStoredProducts();
    }

    void buyProduct(Customer customer, int sectionIndex) {
        ArrayList<Product> row = storedProducts.get(sectionIndex);
        int productIndex = row.size() - 1;
        Product product = row.get(productIndex);

        customer.buy(product, "11:30");
        row.remove(productIndex);
        soldProducts.add(product);
        if (storedProducts.get(sectionIndex).size() == 0) {
            Methods.line("////////////////////RAN OUT OF " + product.getName().toUpperCase());
            storedProducts.remove(sectionIndex);
        }

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

    void displaySoldProducts() {
        System.out.println("Sales history \n" +
                "Products sold total: " + soldProducts.size());
        Methods.line("-");
        for (Product product : soldProducts) {
            System.out.println("Sold product: " + product);

        }
        Methods.line("-");
    }

    private ArrayList<Customer> generateCustomers(int amount) {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("data/ShopDataStorage/customerNames.txt"));
            for (int i = 0; i < amount && scanner.hasNext(); i++) {
                customers.add(new Customer(generateCustomerId(3), scanner.nextLine(), 100 + (float) i / 100));
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

    private int productIdCounter = 0;

    private String generateProductId(int length) {
        String id = Integer.toString(productIdCounter++);
        while (id.length() != length) {
            id = "0" + id;
        }
        return "P" + id;
    }

    private int customerIdCounter = 0;

    private String generateCustomerId(int length) {
        String id = Integer.toString(customerIdCounter++);
        while (id.length() != length) {
            id = "0" + id;
        }
        return "C" + id;
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
            changeBalance(product.getCost() * -1, product);
            purchasedProducts.add(product);
            product.sellTo(this, dateOfSale);
        }

        private void changeBalance(float balanceChange, Product boughtProduct) {
            balance += balanceChange;
            balanceChangeLog.add(balanceChange, balance, boughtProduct);
        }

        boolean canAfford(Product product) {
            return balance - product.getCost() >= 0;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        void displayBalanceHistory() {
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
