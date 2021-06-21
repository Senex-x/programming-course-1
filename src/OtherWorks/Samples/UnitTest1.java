package OtherWorks.Samples;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class UnitTest1 {
    public static final ArrayList<Integer> array = new ArrayList<>();

    private static class Tester {
        private static ArrayList<Integer> testArray;

        @Before
        public static void before() {
            testArray = new ArrayList<>(Arrays.asList(10, 20, 30));
        }

        @Test
        public static void addTest() {
            testArray.add(40);

            assertEquals(4, testArray.size());
            assertEquals(40, testArray.get(3).intValue());
        }
    }
}
