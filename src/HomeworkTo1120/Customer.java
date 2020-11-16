package HomeworkTo1120;

import java.util.ArrayList;

class Customer {
    private int id;
    private String name;
    private int balance;
    private ArrayList<Product> purchasedProducts;
    private BalanceChangeLog balanceChangeLog;

    Customer(int id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        balanceChangeLog = new BalanceChangeLog();
    }

    void buy(Product product, String dateOfSale) {
        balance -= product.getCost();
        balanceChangeLog.add();
        purchasedProducts.add(product);
        product.sellTo(this, dateOfSale);
    }

    boolean isAffordable(Product product) {
        return balance - product.getCost() >= 0;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    ArrayList<Product> getPurchasedProducts() {
        return purchasedProducts;
    }

    void getBalanceHistory() {

    }

    private class BalanceChangeLog {
        int initialBalance;
        ArrayList<LogItem> log = new ArrayList<>();

        public BalanceChangeLog(int initialBalance) {

        }

        private void add() {

        }

        private void display() {

        }

        private class LogItem {
            private int balance;
        }
    }
}
