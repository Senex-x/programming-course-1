package Homeworks.SecondSemester.Month03.HomeworkTo18;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class Task2 {
    private static final String BUYINGS_PATH = "src\\Homeworks\\SecondSemester\\Month03\\HomeworkTo18\\data\\buyings.txt";
    public static void main(String[] args) {
        HashMap<String, HashMap<String, Integer>> data = new HashMap<>();
        try {
            Scanner sc = new Scanner(new FileReader(BUYINGS_PATH));
            while(sc.hasNext()) {
                String name = sc.next();
                String item = sc.next();
                int count = sc.nextInt();

                if(data.containsKey(name)) {
                    if(data.get(name).containsKey(item)) {
                        data.get(name).put(item, data.get(name).get(item) + count);
                    } else {
                        data.get(name).put(item, count);
                    }
                } else {
                    data.put(name, new HashMap<>());
                    data.get(name).put(item, count);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(data);
    }
}
