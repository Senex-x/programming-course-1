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
                for(int i=0;i<amount;i++) {
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
        for(ArrayList<Product> section : storedProducts) {
            System.out.println("Stored product: " + section.get(0).getName() + "\n" +
                    "Total amount: " + section.size());
            Methods.line("-");
            for(Product product : section) {
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
}
