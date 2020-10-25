package HomeworkTo1016;

import Methods.Methods;

public class Task1 {
    public static void main(String[] args) {
        int x = Methods.getInt();
        System.out.println(ranks(x));
    }

    private static int ranks(int num) {
        int counter = 0;
        while(num != 0) {
            counter++;
            num/=10;
        }
        return counter;
    }
}
