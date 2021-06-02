package OtherWorks.Exams.ControlWork2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Task3 {
    private static final ArrayList<Line> lines = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new FileReader("src/OtherWorks/Exams/ControlWork2/data/task1.txt"));
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split("\\|");
                lines.add(new Line(
                        line[0],
                        line[1],
                        Integer.parseInt(line[2])
                ));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        executeTask3(lines);
    }

    static void executeTask3(ArrayList<Line> lines) {
        lines.stream().collect(Collectors.groupingBy(Line::getName)).forEach((key, value) -> {
            value.stream()
                    .collect(Collectors.toMap(l -> {
                        HashMap<String, String> m = new HashMap<>();
                        m.put(l.getName(), l.getProduct());
                        return m;
                    }, Line::getCount, (i1, i2) -> i1 < i2 ? i1 : i2)).entrySet().forEach(System.out::println);

            value.stream()
                    .collect(Collectors.toMap(l -> {
                        HashMap<String, String> m = new HashMap<>();
                        m.put(l.getName(), l.getProduct());
                        return m;
                    }, Line::getCount, (i1, i2) -> i1 < i2 ? i2 : i1)).entrySet().forEach(System.out::println);
        });
    }
}
