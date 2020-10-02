package Homework2909;

import java.util.*;

/**
 * Finding first digit one in an array of zeroes and ones
 */

public class Task6 {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rand = new Random();

    public static void main(String[] args) {
        int size = sc.nextInt();
        char[] array = makeArray(size);
        output(array);

        System.out.println("First '1' is on " + new String(array).indexOf('1') + " position");

    }

    private static char[] makeArray(int size) {
        int index = rand.nextInt(size);
        char[] array = new char[size];
        for (int i = 0; i < size; i++) {
            if (i < index) {
                array[i] = '0';
            } else {
                array[i] = '1';
            }
        }
        return array;
    }

    private static void output(char[] array) {
        System.out.println("Array of " + array.length + " elements:");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
            if (i % 50 == 0 && i != 0) System.out.println();
        }
        System.out.println();
    }
}
