package HomeworkTo0929;

import java.util.*;

/**
 * Changing values of two variables without a buffer variable
 */

public class Task3 {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int a = sc.nextInt();
        int b = sc.nextInt();

        a = a + b;
        b = a - b;
        a = a - b;

        System.out.println(a + "  " + b);
    }
}
