package Homeworks.SecondSemester.Month05.HomeworkTo06;

import java.util.*;

public class Task1 {
    public static void main(String[] args) {
        TreeSet<String> set = new TreeSet<>((o1, o2) -> Integer.compare(o1.length(), o2.length()));

        List<String> arr = Arrays.asList("fh", "asjldnj", "asjldg0", "ajdf", "qtywe", "d");
        set.addAll(arr);
        System.out.println(set);
    }
}
