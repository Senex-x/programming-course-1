package OtherWorks.Exams.CrudTask.crud;

public class TestObject implements Printable, Comparable<TestObject> {
    private final int value;
    private final String meta;

    public TestObject(int value, String meta) {
        this.value = value;
        this.meta = meta;
    }

    @Override
    public int compareTo(TestObject givenObject) {
        return Integer.compare(value, givenObject.getValue());
    }

    public int getValue() {
        return value;
    }

    public String getMeta() {
        return meta;
    }

    @Override
    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "value=" + value +
                ", meta='" + meta + '\'' +
                '}';
    }
}
