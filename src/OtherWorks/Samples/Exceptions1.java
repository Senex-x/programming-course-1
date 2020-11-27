package OtherWorks.Samples;

public class Exceptions1 {
    public static void main(String[] args) {
        try {
            throwException();
            throwException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void throwException() throws Exception {
        System.out.println("Throwing exception");
        throw new Exception();
    }
}
