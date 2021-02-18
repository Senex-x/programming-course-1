package OtherWorks.Exam;

import java.util.Scanner;

public class Exam {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean[] check = new boolean[128];
        char[] checkPool = {'a', 'e', 'i', 'j', 'o', 'u', 'y'};
        for (int i = 0; i < checkPool.length; i++) {
            check[checkPool[i]] = true;
            check[Character.toUpperCase(checkPool[i])] = true;
        }

        // Input section
        int n = scanner.nextInt();
        String[] array = new String[n];
        String[] res = new String[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.next();
        }

        int counter = 0;
        int additionsCounter = 0;
        outer:
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length(); j++) {
                char c = array[i].charAt(j);
                if ((c < 97 || c > 122) && (c < 65 || c > 90)) { // not a letter
                    continue outer; // next word
                }
                if (check[c]) { // ok
                    counter++;
                }
            }
            if (counter <= 3) {
                // adding string
                res[additionsCounter++] = stringHandler(array[i], check);
            }
            // going to next word
            counter = 0;
        }

        // result printing
        for (int i = 0; i < res.length && res[i] != null; i++) {
            System.out.print(res[i] + " ");
        }
    }

    static String stringHandler(String origin, boolean[] check) {
        String result = "";
        for (int i = 0; i < origin.length(); i++) {
            char c = origin.charAt(i);
            if (check[c]) { // proper
                if (c >= 97) { // cursive
                    result = result.concat("a");
                } else { // capital
                    result = result.concat("A");
                }
                continue;
            }
            // do nothing
            result = result.concat(Character.toString(c));
        }
        return result;
    }

}

/*
5
aduyUFUHshd hsk\\lg UGYC 8394 JKDAAas
 */