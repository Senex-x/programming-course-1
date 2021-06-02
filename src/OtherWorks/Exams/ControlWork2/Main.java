package OtherWorks.Exams.ControlWork2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    private static final ArrayList<LineTriple> lineTriples = new ArrayList<>();
    private static final ArrayList<LineDouble> lineDoubles = new ArrayList<>();

    public static void main(String[] args) {
        parseTextTriple();
        parseTextDouble();

        executeTask1();
        executeTask2();
        executeTask3();
    }

    private static void executeTask1() {
        lineTriples.stream().collect(Collectors.groupingBy((Function<LineTriple, Object>) LineTriple::getName))
                .forEach((key, value) -> System.out.println(
                        value.stream().collect(
                                Collectors.groupingBy((Function<LineTriple, Object>) lineTriple -> {
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put(lineTriple.getName(), lineTriple.getProduct());
                                    return map;
                                }, Collectors.counting())).entrySet()
                                .stream()
                                .max(Comparator.comparingLong(Map.Entry::getValue)).get()));
    }

    private static void executeTask2() {
        Map<String, Integer> map =
                lineDoubles.stream()
                        .collect(Collectors.toMap(
                                LineDouble::getProduct,
                                LineDouble::getCount,
                                (integer, integer2) -> integer += integer2));
        int average = map.values().stream().mapToInt(i -> i).sum() / map.values().size();
        System.out.println(average);

        map.entrySet().stream()
                .filter(stringIntegerEntry -> stringIntegerEntry.getValue() > average)
                .forEach(System.out::println);
    }

    static void executeTask3() {
        lineTriples.stream().collect(Collectors.groupingBy(LineTriple::getName)).forEach((key, value) -> {
            value.stream()
                    .collect(Collectors.toMap(l -> {
                        HashMap<String, String> m = new HashMap<>();
                        m.put(l.getName(), l.getProduct());
                        return m;
                    }, LineTriple::getCount, (i1, i2) -> i1 < i2 ? i1 : i2)).entrySet().forEach(System.out::println);

            value.stream()
                    .collect(Collectors.toMap(l -> {
                        HashMap<String, String> m = new HashMap<>();
                        m.put(l.getName(), l.getProduct());
                        return m;
                    }, LineTriple::getCount, (i1, i2) -> i1 < i2 ? i2 : i1)).entrySet().forEach(System.out::println);
        });
    }

    private static void parseTextTriple() {
        try {
            Scanner sc = new Scanner(new FileReader("src/OtherWorks/Exams/ControlWork2/data/task1.txt"));
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split("\\|");
                lineTriples.add(new LineTriple(
                        line[0],
                        line[1],
                        Integer.parseInt(line[2])
                ));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void  parseTextDouble() {
        try {
            Scanner sc = new Scanner(new FileReader("src/OtherWorks/Exams/ControlWork2/data/task1.txt"));
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split("\\|");
                lineDoubles.add(new LineDouble(
                        line[1],
                        Integer.parseInt(line[2])
                ));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
