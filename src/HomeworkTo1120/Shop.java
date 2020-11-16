package HomeworkTo1120;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Shop {
    private FileReader productNamesReader;
    private ArrayList<Product> soldProducts = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();

    private ArrayList<Customer> generateCustomers(int amount) {
        ArrayList<Customer> customers = null;
        try {
            FileReader customerNamesReader = new FileReader("data/ShopDataStorage/customerNames.txt");
            Scanner scanner = new Scanner(customerNamesReader);
            customers = new ArrayList<>();
            for (int i = 0; i < amount && scanner.hasNext(); i++) {
                customers.add(new Customer(i, scanner.next(), 100));
            }
            customerNamesReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    void open() {

        ArrayList<Customer> customers = generateCustomers(100);
        for(Customer customer : customers) {
            System.out.println(customer);
        }

    }

    public static void main(String[] args) {
        Shop newShop = new Shop();
        newShop.open();
    }
}
