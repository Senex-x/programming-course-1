package OtherWorks.Samples;

public class Concurrency2 {
    private static class DeadlockExample {

        public static void main(String[] args) {
            final String resource1 = "ratan jaiswal";
            final String resource2 = "vimal jaiswal";

            // t1 tries to lock resource1 then resource2
            Thread t1 = new Thread(() -> {
                synchronized (resource1) {
                    System.out.println("Thread 1: locked resource 1");

                    synchronized (resource2) {
                        System.out.println("Thread 1: locked resource 2");
                    }
                }
            });

            // t2 tries to lock resource2 then resource1
            Thread t2 = new Thread(() -> {
                synchronized (resource2) {
                    System.out.println("Thread 2: locked resource 2");

                    synchronized (resource1) {
                        System.out.println("Thread 2: locked resource 1");
                    }
                }
            });

            t1.start();
            t2.start();
        }
    }
}
