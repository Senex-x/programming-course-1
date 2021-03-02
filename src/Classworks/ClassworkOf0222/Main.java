package Classworks.ClassworkOf0222;

import java.util.ArrayList;

public class Main {
    public static <T> void asList(T... items) {
        ArrayList<T> list = new ArrayList<>();
        for(T item:items) {
            list.add(item);
        }
    }
}
