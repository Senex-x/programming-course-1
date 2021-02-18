package OtherWorks.Samples;

public class ExamTests {
    public static void main(String[] args) {

        InterfaceFirst interfaceFirst = new InterfaceFirst() {
            @Override
            public void first() {
                System.out.println("First");
            }

            @Override
            public void second() {
                System.out.println("Second");
            }
        };
    }
}

interface InterfaceFirst extends InterfaceSecond {
    void first();
}
interface InterfaceSecond {
    void second();
}
