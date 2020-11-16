package HomeworkTo1120;

class Product {
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
