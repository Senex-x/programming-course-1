package OtherWorks.Exams.ControlWork2;

class LineDouble {
    private final String product;
    private final int count;

    public LineDouble(String product, int count) {
        this.product = product;
        this.count = count;
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
                "product='" + product + '\'' +
                ", count=" + count +
                '}';
    }
}

