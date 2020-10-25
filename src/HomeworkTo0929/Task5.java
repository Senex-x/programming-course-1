package HomeworkTo0929;

import java.util.*;

/**
 * Finding all the pairs in array summation of which gives us wanted number X
 */

public class Task5 {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rand = new Random();

    public static void main(String[] args) {
        int size = sc.nextInt();
        int x = sc.nextInt();
        int[] array = makeRandomArray(size);
        output(array);

        Hashtable<Integer, Integer> elements = new Hashtable<>();

        for (int current : array) {
            elements.put(x - current, current);
            if (elements.containsKey(current)) {
                System.out.println("Pair is [" + (x - current) + " " + current + "]");
            }
        }
    }

    private static int[] makeRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(101);
        }
        return array;
    }

    private static void output(int[] array) {
        System.out.println("Array of " + array.length + " elements:");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
            if (i % 50 == 0 && i != 0) System.out.println();
        }
        System.out.println();
    }
}
