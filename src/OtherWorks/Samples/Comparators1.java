package OtherWorks.Samples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class Comparators1 {
    public static void main(String[] args) {
        TreeSet<ItemComparable> sortedComparable = new TreeSet<>();
        sortedComparable.addAll(Arrays.asList(
                new ItemComparable(80, "2t24"),
                new ItemComparable(100, "aewf"),
                new ItemComparable(40, "6eeg")
                ));

        TreeSet<ItemPlain> sortedComparator = new TreeSet<>(Comparator.comparingInt(Item::getValue));
        sortedComparator.addAll(Arrays.asList(
                new ItemPlain(30, "dgjf"),
                new ItemPlain(10, "sgfg"),
                new ItemPlain(20, "2erf")
                ));

        System.out.println(sortedComparable);
        System.out.println(sortedComparator);
    }

    private static class ItemComparable extends Item implements Comparable<ItemComparable> {
        public ItemComparable(int value, String data) {
            super(value, data);
        }

        @Override
        public int compareTo(ItemComparable o) {
            return Integer.compare(getValue(), o.getValue());
        }
    }

    private static class ItemPlain extends Item {
        public ItemPlain(int value, String data) {
            super(value, data);
        }
    }

    private static class Item {
        private final int value;
        private final String data;

        public Item(int value, String data) {
            this.value = value;
            this.data = data;
        }

        public int getValue() {
            return value;
        }

        public String getData() {
            return data;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "value=" + value +
                    ", data='" + data + '\'' +
                    '}';
        }
    }
}
