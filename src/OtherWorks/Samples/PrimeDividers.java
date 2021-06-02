package OtherWorks.Samples;

import java.util.ArrayList;

public class PrimeDividers {
    // Разложение на простые числа
    public static ArrayList<Integer> getPrimeFactors(int n) {
        ArrayList<Integer> primeNumbers = primeNumbers((n / 2) + 1);
        ArrayList<Integer> forRemove = new ArrayList<>();
        for (Integer primeNumber : primeNumbers) {
            if (n % primeNumber != 0) {
                forRemove.add(primeNumber);
            }
        }
        primeNumbers.removeAll(forRemove);
        return primeNumbers;
    }

    // Решето эратосфена
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
}
