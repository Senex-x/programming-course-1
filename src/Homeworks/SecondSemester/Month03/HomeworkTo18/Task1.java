package Homeworks.SecondSemester.Month03.HomeworkTo18;

import Methods.Methods;

import java.util.HashMap;

public class Task1 {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        String inp = Methods.getLine();

        for(String word : inp.split(" ")) {
            if(map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }

        System.out.println(map);
    }
}
