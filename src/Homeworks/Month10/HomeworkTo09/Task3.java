package Homeworks.Month10.HomeworkTo09;

import Methods.Methods;

import java.util.*;

/**
 * Task to sort array of strings in the alphabetic order
 * and then evaluate the complexity of an algorithm
 *
 * Estimate complexity of sorting using quickSort via
 * ".sort(new Comparator{});" is O(n*log(n)) and on every step
 * we compare from one to each element in given strings
 * which raises the complexity to (average length of given
 * strings * (n*log(n)))
 */
public class Task3 {
    public static void main(String[] args) {
        ArrayList<String> array = Methods.getStringArray();

        array.sort((o1, o2) -> {
            for (int i = 0; i < o1.length() && i < o2.length(); i++) {
                if (o1.charAt(i) > o2.charAt(i)) return 1;
                if (o1.charAt(i) < o2.charAt(i)) return -1;
            }
            return 0;
        });

        Methods.displayArray(array, 20);
    }
}
