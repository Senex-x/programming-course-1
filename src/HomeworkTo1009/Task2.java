package HomeworkTo1009;

import Methods.Methods;
import java.util.*;

/**
 * Task to using binary search find an element
 */

public class Task2 {
    public static void main(String[] args) {
        ArrayList<Integer> array = Methods.getIntArray();
        Methods.sort(array);
        Methods.outArray(array);
        System.out.println("Index of wanted element in the array: " +
                binarySearch(array, Methods.getInt(), 0, array.size() - 1));
    }

    public static int binarySearch(ArrayList<Integer> array, int element, int left, int right) {
        while (left <= right) {
            int keyIndex = (left + right) / 2;
            if (array.get(keyIndex) < element) {
                left = keyIndex + 1;
            } else if (array.get(keyIndex) > element) {
                right = keyIndex - 1;
            } else if (array.get(keyIndex) == element) {
                return keyIndex;
            }
        }
        return 0;
    }
}

