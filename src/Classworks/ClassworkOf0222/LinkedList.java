package Classworks.ClassworkOf0222;

import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                return null;
            }
        };
    }
}

interface Iterable<T> {
    Iterator<T> iterator();
}