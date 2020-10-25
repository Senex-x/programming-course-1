package ClassworkOf0210;

import java.util.*;

public class Task1 {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rand = new Random();

    public static void main(String[] args) {
        int size = sc.nextInt();
        ArrayList<Integer> array = makeRandomArray(size);
        output(array);

        array.sort((o1, o2) -> {
            if (o1 < o2) return -1;
            if (o2 < o1) return 1;
            return 0;
        });

        output(array);

    }

    private static ArrayList<Integer> makeRandomArray(int size) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            array.add(rand.nextInt(101));
        }
        return array;
    }

    private static void output(ArrayList<Integer> array) {
        System.out.println("Array of " + array.size() + " elements:");
        for (int i = 0; i < array.size(); i++) {
            System.out.print(array.get(i) + " ");
            if (i % 50 == 0 && i != 0) System.out.println();
        }
        System.out.println();
    }
}
