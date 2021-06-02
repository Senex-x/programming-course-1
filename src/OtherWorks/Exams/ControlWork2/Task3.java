package OtherWorks.Exams.ControlWork2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Task3 {
    private static final ArrayList<Task3.Line> lines = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new FileReader("src/OtherWorks/Exams/ControlWork2/data/task1.txt"));
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split("\\|");
                lines.add(new Task3.Line(
                        line[0],
                        line[1],
                        Integer.parseInt(line[2])
                ));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        lines.stream().collect(Collectors.toMap(line -> {
            HashMap<String, String> map = new HashMap<>();
            map.put(line.getName(), line.getProduct());
            return map;
        }, line -> line.count, (integer, integer2) -> integer += integer2)).entrySet().forEach(System.out::println);
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
