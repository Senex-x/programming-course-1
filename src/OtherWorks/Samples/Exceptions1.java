package OtherWorks.Samples;

public class Exceptions1 {
    public static void main(String[] args) {
        try {
            //throwException(new ClassCastException());
            throwException(new MyException("MyException"));
        } catch (Exception e) {
            System.out.println("Handling exception: " + e.getClass().getSimpleName());
        }
        System.out.println("Continue work...");
    }

    static void throwException(Exception exception) throws Exception {
        System.out.println("Throwing exception: " + exception.getClass().getSimpleName());
        throw exception;
    }

    static class MyException extends Exception {
        private String desc;

        MyException(String desc) {
            this.desc = desc;
        }
    }
}
