package Homeworks.SecondSemester.Month02.HomeworkTo22;

import java.util.Iterator;

public class Task2 {
    interface Iterable<t> {
        Iterator<t> iterator();
    }

    static class Range implements Iterable<Integer>{
        private final int length;
        private int counter;
        public Range(int length) {
            this.length = length;
            counter = 0;
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
                    return counter++;
                }
            };
        }
    }

    static class RangeInterval extends Range {
        public RangeInterval(int startIndex, int endIndex) throws IllegalArgumentException {
            super(endIndex - startIndex + 1);
            if(startIndex > endIndex) {
                throw new IllegalArgumentException();
            }
        }
    }
}
