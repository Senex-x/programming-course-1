package HomeworkTo1120;

class Product {
    private String name;
    private int cost;
    private String dateOfSale;
    private Customer buyer;

    Product(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    void sellTo(Customer buyer, String dateOfSale) {
        this.buyer = buyer;
        this.dateOfSale = dateOfSale;
    }

    Customer getBuyer() {
        return buyer;
    }

    String getName() {
        return name;
    }

    int getCost() {
        return cost;
    }

    String getDateOfSale() {
        return dateOfSale;
    }
}
