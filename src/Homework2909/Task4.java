package Homework2909;

import java.util.*;

/**
 * Finding a duplication of an element
 */

public class Task4 {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rand = new Random();

    public static void main(String[] args) {
        int size = sc.nextInt();
        int[] array = makeRandomArray(size);
        output(array);

        HashSet<Integer> uniqueElements = new HashSet<>();
        for (int x : array) {
            if(!uniqueElements.add(x)) {
                System.out.println("First element with duplication is " + x);
                return;
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
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
