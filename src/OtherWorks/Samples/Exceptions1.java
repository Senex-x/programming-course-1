package OtherWorks.Samples;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Exceptions1 {
    public static void main(String[] args) {
        try {
            exceptionChaining();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    static void exceptionChaining() throws NullPointerException {
        try {
            throw new Exception("Initial exception");
        } catch (Exception exception) {
            NullPointerException nextException = new NullPointerException("Second exception");
            nextException.initCause(exception);
            throw nextException;
        }
    }

    static void rethrowingExceptionHandling() {
        try {
            rethrowingException();
        } catch (Exception e) {
            System.out.println("Second catch: ");
            e.printStackTrace(System.out);
            e.fillInStackTrace();
            e.printStackTrace(System.out);
        }
    }

    static void rethrowingException() throws Exception {
        try {
            throw new Exception("Simple exception");
        } catch (Exception e) {
            System.out.println("First catch: " + e);
            e.printStackTrace(System.out);
            e.fillInStackTrace();
            e.printStackTrace(System.out);
            throw e;
        }
    }

    static void optionalExceptionHandling() {
        try {
            ArrayList<Integer> array = new ArrayList<>(10);
            int a = array.get(20);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e);
            for(StackTraceElement element : e.getStackTrace()) {
                System.out.println(element);
            }
        }
    }

    static void basicExceptionHandling() {
        try {
            throwException(new MyException());
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
        private static final Logger log = Logger.getLogger("MyException");

        MyException() {
            StringWriter stringWriter = new StringWriter();
            printStackTrace(new PrintWriter(stringWriter));
            log.severe(stringWriter.toString());
            System.err.println("SEVERE END");
        }

        @Override
        public String getMessage() {
            System.out.println("StackTrace: ");
            return super.getMessage();
        }
    }
}
