package HomeworkTo2909;

import java.util.*;

/**
 * Checking if one string is a permutation of another
 */

public class Task2 {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        char current;
        int index;
        String first = sc.nextLine();
        String second = sc.nextLine();

        if (first.length() != second.length()) {
            finish();
        }
        for (int i = 0; i < first.length(); i++) {
            current = first.charAt(i);
            first = deleteChar(first, i);
            index = second.indexOf(current);
            if (index == -1) {
                finish();
            }
            second = deleteChar(second, index);
        }
        System.out.println("YES");
    }

    private static void finish() {
        System.out.println("NO");
        System.exit(0);
    }

    private static String deleteChar(String string, int index) {
        return string.substring(0, index) + string.substring(index + 1);
    }
}
