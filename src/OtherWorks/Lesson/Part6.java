package OtherWorks.Lesson;

import Methods.Methods;

import java.util.Scanner;

public class Part6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        System.out.println(fact(n));
    }

    private static int fact(int n) {
        if (n == 1) return 1;
        return fact(n - 1) * n;
    }
}
