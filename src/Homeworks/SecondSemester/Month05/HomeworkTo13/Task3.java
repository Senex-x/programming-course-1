package Homeworks.SecondSemester.Month05.HomeworkTo13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Task3 {
    public static final String PATH = "src/Homeworks/SecondSemester/Month05/HomeworkTo13/data/task3.txt";

    public static void main(String[] args) {
        Map<String, Integer> res = new HashMap<>();
        try {
            res = Files.lines(Paths.get(PATH))
                    .collect(Collectors.toMap(
                            s -> s.substring(0, s.indexOf('|')),
                            s -> Integer.parseInt(s.substring(s.indexOf('|') + 1)),
                            (integer, integer2) -> integer += integer2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }
}
