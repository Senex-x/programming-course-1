package Homework0210to0910;

import Methods.Methods;
import java.util.*;

/**
 * Task to quicksort array
 */

public class Task1 {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Integer> array = Methods.inputIntArray();
        quickSort(array, 0, array.size() - 1);
        Methods.outputArray(array);
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
}

