package HomeworkTo2310;

import Methods.Methods;

/**
 * ATTENTION!
 * numeration from zero
 */

public class Task3 {
    public static void main(String[] args) {
        int n = Methods.getInt();
        System.out.println(fibonnacci(n - 1));
    }

    private static int fibonnacci(int n) {
        //System.out.print(n + " ");
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonnacci(n - 1) + fibonnacci(n - 2);
    }
}
