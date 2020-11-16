package HomeworkTo1120;

import Methods.Methods;

import java.util.ArrayList;

class Customer {
    private int id;
    private String name;
    private int balance;
    private ArrayList<Product> purchasedProducts = new ArrayList<>();
    private BalanceChangeLog balanceChangeLog;

    Customer(int id, String name, int balance) {
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

    private void changeBalance(int balanceChange) {
        balance += balanceChange;
        balanceChangeLog.add(balanceChange, balance);
    }

    boolean canAfford(Product product) {
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
        private int initialBalance;
        private ArrayList<LogItem> log = new ArrayList<>();

        private BalanceChangeLog(int initialBalance) {
            this.initialBalance = initialBalance;
        }

        private void add(int balanceChange, int balanceRemain) {
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
            private int balanceChange;
            private int balanceRemain;

            private LogItem(int number, int balanceChange, int balanceRemain) {
                this.number = number;
                this.balanceChange = balanceChange;
                this.balanceRemain = balanceRemain;
            }

            private int getBalanceChange() {
                return balanceChange;
            }

            private int getBalanceRemain() {
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
