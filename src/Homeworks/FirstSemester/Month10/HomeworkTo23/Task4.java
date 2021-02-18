package Homeworks.FirstSemester.Month10.HomeworkTo23;

import Methods.Methods;

public class Task4 {
    public static void main(String[] args) {
        int m = Methods.getInt();
        int n = Methods.getInt();
        System.out.println(ackermannFunc(m, n));
    }

    private static int ackermannFunc(int m, int n) {
        if (m == 0) return n + 1;
        else if (m > 0 && n == 0) {
            return ackermannFunc(m - 1, 1);
        } else {
            return ackermannFunc(m - 1, ackermannFunc(m, n - 1));
        }
    }
}
