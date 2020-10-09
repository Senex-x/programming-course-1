package HomeworkTo1610;

import Methods.Methods;

import java.util.ArrayList;

public class Task3 {
    public static void main(String[] args) {
        ArrayList<Integer> array = Methods.inIntArray();
        System.out.println(maxInArray(array));
    }

    private static int maxInArray(ArrayList<Integer> array) {
        int max = Integer.MIN_VALUE;
        for (int x : array) {
            if (x > max) max = x;
        }
        return max;
    }
}
