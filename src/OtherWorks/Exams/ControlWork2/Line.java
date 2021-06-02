package OtherWorks.Exams.ControlWork2;

class Line {
    private final String name;
    private final String product;
    private final int count;

    public Line(String name, String product, int count) {
        this.name = name;
        this.product = product;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Line{" +
                "name='" + name + '\'' +
                ", product='" + product + '\'' +
                ", count=" + count +
                '}';
    }
}
