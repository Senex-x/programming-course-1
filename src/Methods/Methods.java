package Methods;

import java.util.*;

public abstract class Methods {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rand = new Random();

    public static int getInt() {
        return sc.nextInt();
    }

    public static String getString() {
        return sc.nextLine();
    }

    public static ArrayList<Integer> inputIntArray(int size) {
        ArrayList<Integer> array = new ArrayList<>();
        System.out.println("Enter " + size + " elements of an array: ");
        for (int i = 0; i < size; i++) {
            array.add(sc.nextInt());
        }
        return array;
    }

    public static ArrayList<Integer> inputIntArray() {
        System.out.println("Enter the size of an array: ");
        int size = sc.nextInt();
        return inputIntArray(size);
    }

    public static ArrayList<Integer> inputAnyLengthIntArray() {
        ArrayList<Integer> array = new ArrayList<>();
        System.out.println("Enter elements of an array, then finish with '-1' sign: ");
        int current = sc.nextInt();
        while (current != -1) {
            array.add(current);
            current = sc.nextInt();
        }
        return array;
    }

    public static void outputArray(ArrayList<Integer> array) {
        System.out.println("Array of " + array.size() + " elements:");
        for (int i = 0; i < array.size(); i++) {
            System.out.print(array.get(i) + " ");
            if (i % 60 == 0 && i != 0) System.out.println();
        }
        System.out.println();
    }

    public static ArrayList<Integer> makeRandomArray(int size, int bound) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            array.add(rand.nextInt(bound));
        }
        return array;
    }

    public static ArrayList<Integer> makeRandomArray(int bound) {
        System.out.println("Enter the size of a randomly filled array: ");
        int size = sc.nextInt();
        return makeRandomArray(size, bound);
    }

    public static void sort(ArrayList<Integer> array) {
        quickSort(array, 0, array.size() - 1);
    }

    private static void quickSort(ArrayList<Integer> array, int left, int right) {
        int key = array.get(left + (right - left) / 2);
        int i = left, j = right;
        while (i <= j) {
            while (array.get(i) < key)
                i++;

            while (array.get(j) > key)
                j--;

            if (i <= j) {//меняем местами
                int temp = array.get(i);
                array.set(i, array.get(j));
                array.set(j, temp);
                i++;
                j--;
            }
        }
        if (left < j)
            quickSort(array, left, j);
        if (right > i)
            quickSort(array, i, right);
    }

    public static void line() {
        System.out.println("-----------------------------------" +
                "------------------------------------------------" +
                "------------------------------------------------" +
                "------------------------------------------------");
    }
}
