package Homeworks.SecondSemester.Month05.HomeworkTo13;

import Methods.Methods;

import java.util.Arrays;
import java.util.Comparator;

public class Task1 {
    public static void main(String[] args) {
        int n = Methods.getInt();
        String[] array = new String[n];
        for (int i = 0; i < n; i++) {
            array[i] = Methods.getString();
        }

        Arrays.stream(array)
                .filter(s -> s.charAt(0) == 'a')
                .sorted(Comparator.comparingInt(String::length))
                .forEach(System.out::println);

    }
}
