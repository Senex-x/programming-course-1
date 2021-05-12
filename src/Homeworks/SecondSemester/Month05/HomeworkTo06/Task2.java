package Homeworks.SecondSemester.Month05.HomeworkTo06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Task2 {
    private static void method(List<Integer> array, Predicate<Integer> predicate) {
        for (int i = 0; i < array.size(); i++) {
            if (!predicate.test(array.get(i))) {
                array.remove(i--);
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<>(Arrays.asList(1, 4, 6, 8, 47, 76, 45, 75, 34, 23, 0));
        method(array, integer -> integer % 2 == 0);
        System.out.println(array);
    }
}
