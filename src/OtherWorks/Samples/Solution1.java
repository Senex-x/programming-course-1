package OtherWorks.Samples;

import Methods.Methods;

import java.math.BigInteger;

/**
 * How many numbers between n and m (n < m) consists of
 * odd digits and how many consists of unique digits?
 *
 * Сколько чисел между n и m (n<m) состоит из нечётных
 * цифр и сколько из различных цифр?
 */

public class Solution1 {
    public static void main(String[] args) {
        BigInteger n = new BigInteger(Methods.getString());
        BigInteger m = new BigInteger(Methods.getString());
        BigInteger one = BigInteger.ONE;
        String number;
        int digit;
        boolean[] digits;
        boolean checkOdd;
        boolean checkUnique;

        int counterOdd = 0;
        int counterUnique = 0;
        while ((m = m.subtract(one)).compareTo(n) != 0) {
            number = m.toString();
            digits = new boolean[10];
            checkOdd = true;
            checkUnique = true;
            for (int i = 0; i < number.length() && (checkOdd || checkUnique); i++) {
                digit = number.charAt(i) - 48;

                if (checkOdd && digit % 2 == 0) { // even
                    checkOdd = false;
                }

                if (checkUnique && !digits[digit]) { // not appeared yet
                    digits[digit] = true;
                } else { // appeared already
                    checkUnique = false;
                }
            }
            if (checkOdd) counterOdd++;
            if (checkUnique) counterUnique++;
        }
        System.out.println("Amount of numbers with odd digits: " + counterOdd + "\n" +
                "Amount of numbers with unique digits: " + counterUnique);
    }
}


