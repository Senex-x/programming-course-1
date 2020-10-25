package HomeworkTo1023;

import Methods.Methods;

public class Task2 {
    public static void main(String[] args) {
        int a = Methods.getInt();
        int b = Methods.getInt();
        recOutGap(a, b);
    }

    private static void recOutGap(int a, int b) {
        System.out.print(a + " ");
        if(a == b) return;
        recOutGap(++a, b);
    }
}
