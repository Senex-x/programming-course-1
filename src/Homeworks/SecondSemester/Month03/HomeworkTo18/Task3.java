package Homeworks.SecondSemester.Month03.HomeworkTo18;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class Task3 {
    private static final String WORDS_PATH = "src\\Homeworks\\SecondSemester\\Month03\\HomeworkTo18\\data\\words.txt";
    public static void main(String[] args) {
        HashMap<String, HashMap<String, Integer>> data = new HashMap<>();
        try {
            Scanner sc = new Scanner(new FileReader(WORDS_PATH));
            String current = "", next = "";
            while(sc.hasNext()) {
                if(next.isEmpty()) {
                    next = sc.next();
                    continue;
                }
                current = next;
                next = sc.next();

                if(data.containsKey(current)) {
                    if(data.get(current).containsKey(next)) {
                        data.get(current).put(next, data.get(current).get(next) + 1);
                    } else {
                        data.get(current).put(next, 1);
                    }
                } else {
                    data.put(current, new HashMap<>());
                    data.get(current).put(next, 1);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(data);
    }
}
