package OtherWorks.Samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        ArrayList<String> array = new ArrayList<>(Arrays.asList(
                "one",
                "two",
                "three",
                "four",
                "five"
        ));

        array.stream().filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() < 4;
            }
        }).forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        array.stream().filter(s -> s.length() < 4).forEach(System.out::println);

    }
}
