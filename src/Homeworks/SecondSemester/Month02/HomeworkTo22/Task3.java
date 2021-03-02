package Homeworks.SecondSemester.Month02.HomeworkTo22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Task3 {
    public static void main(String[] args) {
        FibonacciRange fibonacciRange = new FibonacciRange(10);
        Iterator<Integer> it = fibonacciRange.iterator();
        while(it.hasNext()) {
            System.out.print(it.next() + " ");
        }
    }

    interface Iterable<T> {
        Iterator<T> iterator();
    }

    static class FibonacciRange implements Iterable<Integer> {
        private final int length;
        private int counter = 0;

        public FibonacciRange(int length) {
            this.length = length;
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                @Override
                public boolean hasNext() {
                    return counter < length;
                }

                @Override
                public Integer next() {
                    counter++;
                    if (counter == 1) return 0;
                    if (counter == 2) return 1;
                    array.add(array.get(counter - 3) + array.get(counter - 2));
                    return array.get(counter - 1);
                }
            };
        }

        ArrayList<Integer> array = new ArrayList<>(Arrays.asList(0, 1));
    }
}
