package HomeworkTo1120;

import java.util.ArrayList;

class Shop {
    private ArrayList<Product> soldProducts = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();

    private ArrayList<Customer> generateCustomers(int amount) {
        ArrayList<Customer> customers = new ArrayList<>();
        for (int i = 0; i < amount; i++) {

        }
        return customers;
    }

    void open() {
        Customer customer = new Customer(123, "Ghundam Tanaka", 999);
        Product product = new Product("Sunflower seeds", 2);
        while (customer.canAfford(product)) {
            customer.buy(new Product("Sunflower seeds", 20), "11:30");
        }
        customer.getBalanceHistory();
    }

    public static void main(String[] args) {
        Shop newShop = new Shop();
        newShop.open();
    }
}
