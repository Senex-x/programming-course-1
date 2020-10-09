package HomeworkTo1610;

import Methods.Methods;

public class Task2 {
    public static void main(String[] args) {
        String s = Methods.getString();
        System.out.println(reverse(s));
    }

    private static String reverse(String s) {
        String reverse = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            reverse += s.charAt(i);
        }
        return reverse;
    }
}
