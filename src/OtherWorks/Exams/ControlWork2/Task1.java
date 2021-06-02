package OtherWorks.Exams.ControlWork2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Task1 {
    private static final ArrayList<Line> lines = new ArrayList<>();

    private static void printMostBoughtProductsForEachCustomer() {
        lines.stream().collect(Collectors.toMap(line -> {
            HashMap<String, String> map = new HashMap<>();
            map.put(line.getName(), line.getProduct());
            return map;
        }, line -> line.count, (integer, integer2) -> integer += integer2)).entrySet();

        lines.stream().collect(Collectors.groupingBy((Function<Line, Object>) Line::getName))
                .forEach((key, value) -> System.out.println(
                        value.stream().collect(
                                Collectors.groupingBy((Function<Line, Object>) line -> {
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put(line.getName(), line.getProduct());
                                    return map;
                                }, Collectors.counting())).entrySet()
                                .stream()
                                .max(Comparator.comparingLong(Map.Entry::getValue)).get()));
    }

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

        printMostBoughtProductsForEachCustomer();
    }

    private static class Line {
        private final String name;
        private final String product;
        private final int count;

        public Line(String name, String product, int count) {
            this.name = name;
            this.product = product;
            this.count = count;
        }

        public String getName() {
            return name;
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
                    "name='" + name + '\'' +
                    ", product='" + product + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
