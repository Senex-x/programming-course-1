package Methods;

import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class Methods {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rand = new Random();
    private static final int ELEMENTS_IN_LINE = 10;

    public static int getInt() {
        return sc.nextInt();
    }

    public static int getRandInt() {
        return getRandInt(101);
    }

    public static int getRandInt(int bound) {
        return rand.nextInt(bound);
    }

    public static int getRandInt(int lower, int upper) {
        return lower + rand.nextInt(upper - lower + 1);
    }

    public static double getRandDouble() {
        return rand.nextDouble();
    }

    public static double getRandDouble(int bound) {
        return getRandDouble() * bound;
    }

    public static String getString() {
        return sc.next();
    }

    public static String getLine() {
        return sc.nextLine();
    }

    public static ArrayList<Integer> getIntArray(int size) {
        ArrayList<Integer> array = new ArrayList<>();
        System.out.println("Enter " + size + " elements of an array: ");
        for (int i = 0; i < size; i++) {
            array.add(sc.nextInt());
        }
        return array;
    }

    public static ArrayList<Integer> getIntArray() {
        System.out.println("Enter the size of an array: ");
        int size = sc.nextInt();
        return getIntArray(size);
    }

    public static ArrayList<ArrayList<Integer>> getIntMatrix(int n, int m) {
        ArrayList<ArrayList<Integer>> array = new ArrayList<>();
        System.out.println("Enter " + n * m + " elements of matrix:");
        for (int i = 0; i < n; i++) {
            array.add(new ArrayList<>());
            for (int j = 0; j < m; j++) {
                array.get(i).add(sc.nextInt());
            }
        }
        return array;
    }

    public static ArrayList<ArrayList<Integer>> getIntMatrix() {
        System.out.println("Specify dimensions of the matrix: ");
        int n = sc.nextInt();
        int m = sc.nextInt();
        return getIntMatrix(n, m);
    }

    public static ArrayList<ArrayList<Integer>> getIntSquareMatrix(int n) {
        return getIntMatrix(n, n);
    }

    public static ArrayList<ArrayList<Integer>> getIntSquareMatrix() {
        System.out.println("Specify size of the square matrix: ");
        int n = sc.nextInt();
        return getIntMatrix(n, n);
    }

    public static ArrayList<Integer> getAnyLengthIntArray() {
        ArrayList<Integer> array = new ArrayList<>();
        System.out.println("Enter elements of an array, then finish with '-1' sign: ");
        int current = sc.nextInt();
        while (current != -1) {
            array.add(current);
            current = sc.nextInt();
        }
        return array;
    }

    public static ArrayList<String> getStringArray(int size) {
        ArrayList<String> array = new ArrayList<>();
        System.out.println("Enter " + size + " elements of an array: ");
        for (int i = 0; i < size; i++) {
            array.add(sc.next());
        }
        return array;
    }

    public static ArrayList<String> getStringArray() {
        System.out.println("Enter the size of an array: ");
        int size = sc.nextInt();
        return getStringArray(size);
    }

    public static <T> void outArray(ArrayList<T> array) {
        System.out.println("Array of " + array.size() + " elements:");
        for (int i = 0; i < array.size(); i++) {
            System.out.print(array.get(i) + " ");
            if (i % ELEMENTS_IN_LINE == 0 && i != 0) System.out.println();
        }
        System.out.println();
    }

    public static <T> void outMatrix(ArrayList<ArrayList<T>> array) {
        int n = array.size();
        int m = array.get(0).size();
        System.out.println("Matrix of " + array.size() * array.get(0).size() + " elements:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(array.get(i).get(j) + " ");
                if (j % ELEMENTS_IN_LINE == 0 && j != 0) System.out.println();
            }
            System.out.println();
        }
    }

    public static ArrayList<Integer> getRandArray(int size, int bound) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            array.add(rand.nextInt(bound));
        }
        return array;
    }

    public static ArrayList<Integer> getRandArray(int bound) {
        System.out.println("Enter the size of a randomly filled array: ");
        int size = sc.nextInt();
        return getRandArray(size, bound);
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

            if (i <= j) {
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

    public static void line(String lineItem) {
        for (int i = 0; i < 400; i++) {
            System.out.print(lineItem);
        }
        System.out.println();
    }

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String paint(String string, String code) {
        return code + string + Colors.RESET.code();
    }

    public static String paint(String string, Colors color) {
        return paint(string, color.code());
    }

    public enum Colors {
        RESET("\u001B[0m"),
        RED("\u001B[31m"),
        BLUE("\u001B[34m"),
        PURPLE("\033[0;95m"),
        CYAN("\033[0;96m"),
        GREEN("\033[0;92m");

        private String code;

        Colors(String code) {
            this.code = code;
        }

        public String code() {
            return code;
        }

    }
}

