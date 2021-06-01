package Homeworks.SecondSemester.Month05.HomeworkTo27;

import Methods.Methods;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class Task1 {
    private static volatile BigInteger result = new BigInteger("1");

    private static void fact(String threadName, int start, int end) {
        System.out.println(threadName + " has started");
        for (int i = start; i <= end; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        System.out.println(threadName + " has finished task");
    }

    private static Thread launchThread(int start, int end) {
        Thread newThread = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            fact(threadName, start, end);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        newThread.start();
        return newThread;
    }

    public static void main(String[] args) {
        int n = Methods.getInt();

        ArrayList<Thread> threads = new ArrayList<>(
                Arrays.asList(
                        launchThread(1, n / 4),
                        launchThread(n / 4 + 1, n / 2),
                        launchThread(n / 2 + 1, 3 * n / 4),
                        launchThread(3 * n / 4 + 1, n)
                )
        );

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Result: " + result);
    }
}