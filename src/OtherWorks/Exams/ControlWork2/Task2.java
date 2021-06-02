package OtherWorks.Exams.ControlWork2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Task2 {
    private static final ArrayList<Task2.Line> lines = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new FileReader("src/OtherWorks/Exams/ControlWork2/data/task1.txt"));
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split("\\|");
                lines.add(new Task2.Line(
                        line[1],
                        Integer.parseInt(line[2])
                ));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Map<String, Integer> map = lines.stream().collect(Collectors.toMap(line -> line.product, line -> line.count, (integer, integer2) -> integer += integer2));
        int average = map.values().stream().mapToInt(i -> i).sum() / map.values().size();
        System.out.println(average);

        map.entrySet().stream()
                .filter(stringIntegerEntry -> stringIntegerEntry.getValue() > average)
                .forEach(System.out::println);
    }

    private static class Line {
        private final String product;
        private final int count;

        public Line(String product, int count) {
            this.product = product;
            this.count = count;
        }

        public String getProduct() {
            return product;
        }

        public int getCount() {
            return count;
        }

        @Override
        public String toString() {
            return "Line{" +
                    "product='" + product + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
