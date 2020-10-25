package HomeworkTo1023;

import Methods.Methods;

/**
 * ATTENTION!
 * numeration from one
 */

public class Task3 {
    public static void main(String[] args) {
        int n = Methods.getInt();
        System.out.println(fibonnacci(n));
    }

    private static int fibonnacci(int n) {
        if (n == 1) return 0;
        if (n == 2) return 1;
        return fibonnacci(n - 1) + fibonnacci(n - 2);
    }
}
