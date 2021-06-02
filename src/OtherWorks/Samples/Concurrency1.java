package OtherWorks.Samples;

public class Concurrency1 {
    private static final Object lock = new Object();
    private static boolean wake = false;

    public static void main(String[] args) {
        new Thread(new Runnable1()).start();
        new Thread(new Runnable2()).start();
    }

    private static class Runnable1 implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (!wake) {
                    try {
                        printMessage("waits");
                        lock.wait();
                        printMessage("continue");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class Runnable2 implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                try {
                    Thread.sleep(100);
                    printMessage("notifies");
                    wake = true;
                    lock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void printMessage(String message) {
        System.out.println(Thread.currentThread().getName() + " " + message);
    }
}
