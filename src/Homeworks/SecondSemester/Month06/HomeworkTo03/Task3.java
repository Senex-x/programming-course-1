package Homeworks.SecondSemester.Month06.HomeworkTo03;

import Methods.Methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Task3 {
    private static final ArrayList<Runnable> tasks = new ArrayList<>();

    public static void main(String[] args) {
        // Upper bound
        int n = Methods.getInt();
        // Thread counter
        int t = Methods.getInt();

        ExecutorService executor = Executors.newFixedThreadPool(t);

        for (int i = 0; i < t; i++) {
            tasks.add(new Thread(new PrimeHandler(
                    Math.round((float) n / t * i + 1),
                    Math.round((float) n / t * (i + 1))
            )));
        }
        for (Runnable runnable : tasks) {
            executor.submit(runnable);
        }

        executor.shutdown();
    }

    private static class PrimeHandler implements Runnable {
        private final int start;
        private final int end;

        private PrimeHandler(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            printMessage("started. Bounds: " + start + " -> " + end);
            getPrimeNumbersBetween(start, end).forEach(System.out::println);
            printMessage("finished.");
        }

        // Both inclusive
        private static List<Integer> getPrimeNumbersBetween(int start, int end) {
            return primeNumbers(end).stream()
                    .filter(integer -> integer >= start)
                    .collect(Collectors.toList());
        }

        private static ArrayList<Integer> primeNumbers(int end) {
            ArrayList<Integer> factors = new ArrayList<>();
            if (end < 2) {
                return factors;
            }
            if (end == 2) {
                factors.add(2);
                return factors;
            }
            int[] numbers = new int[end - 1];
            boolean[] flags = new boolean[end - 1];
            int count = 2;

            for (int i = 0; i < end - 1; i++) {
                numbers[i] = count;
                flags[i] = true;
                count++;
            }
            for (int i = 0; i < end - 1; i++) {
                if (flags[i]) {
                    factors.add(numbers[i]);
                    int loop = 2;
                    for (long j = numbers[i] * loop; j <= end; j = numbers[i]
                            * loop, loop++) {
                        flags[(int) (j - 2)] = false;
                    }
                }
            }
            return factors;
        }

        private static void printMessage(String message) {
            System.out.println(Thread.currentThread().getName() + " " + message);
        }
    }
}
