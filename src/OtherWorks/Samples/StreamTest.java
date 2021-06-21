package OtherWorks.Samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
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

        // array.stream().filter(s -> s.length() < 4).forEach(System.out::println);

        // "word".chars().flatMap(IntStream::of).forEach(n -> System.out.println((char)n));

        // array.stream().flatMap((Function<String, Stream<?>>) s -> s.chars().boxed()).forEach(System.out::println);

        // array.stream().map(String::toUpperCase).forEach(System.out::print);
    }
}
