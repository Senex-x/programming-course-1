package Homeworks.Month10.HomeworkTo23;

import Methods.Methods;

public class Task1 {
    public static void main(String[] args) {
        int n = Methods.getInt();
        System.out.println(fact(n));
    }

    private static int fact(int n) {
        if(n==1) return 1;
        return fact(n-1) * n;
    }
}


