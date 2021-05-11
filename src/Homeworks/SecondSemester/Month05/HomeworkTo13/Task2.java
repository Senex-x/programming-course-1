package Homeworks.SecondSemester.Month05.HomeworkTo13;

import Methods.Methods;

import java.util.Arrays;
import java.util.Comparator;

public class Task2 {
    public static void main(String[] args) {
        Integer[] array = Methods.getRandArray(100, 101).toArray(new Integer[0]);

        Arrays.stream(array)
                .sorted((o1, o2) -> {
                    while (o1 != 0 && o2 != 0) {
                        if (o1 % 10 > o2 % 10) {
                            return 1;
                        } else if (o1 % 10 < o2 % 10) {
                            return -1;
                        }
                        o1 /= 10;
                        o2 /= 10;
                    }
                    return o1 == 0 ? -1 : 1;
                })
                .map(x -> x /= 10)
                .distinct()
                .forEach(System.out::println);

    }
}
