package OtherWorks.Samples;

public class Concurrency1 {
    public static void main(String[] args) {
        new Thread(new Runnable1()).start();
        new Thread(new Runnable2()).start();
    }

    private static class Runnable1 implements Runnable {
        @Override
        public synchronized void run() {
            try {
                printMessage("waits");
                wait();
                printMessage("continue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Runnable2 implements Runnable {
        @Override
        public synchronized void run() {
            try {
                Thread.sleep(100);
                printMessage("notifies");
                notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printMessage(String message) {
        System.out.println(Thread.currentThread().getName() + " " + message);
    }
}
