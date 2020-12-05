package Homeworks.Month10.HomeworkTo16;

import Methods.Methods;

import java.util.ArrayList;

public class Task4 {
    public static void main(String[] args) {
        ArrayList<Integer> first = Methods.getIntArray();
        ArrayList<Integer> second = Methods.getIntArray();
        Methods.displayArray(concat(first, second), 20);
    }

    private static <T> ArrayList<T> concat(ArrayList<T> first,
                                           ArrayList<T> second) {
        ArrayList<T> result = new ArrayList<T>(first);
        result.addAll(second);
        return result;
    }
}
