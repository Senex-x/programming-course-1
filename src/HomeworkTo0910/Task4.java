package HomeworkTo0910;

import Methods.Methods;

import java.util.*;

/**
 * Task to check if in given square matrix
 * is n/2 or more even elements in it's
 * diagonals
 */

public class Task4 {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> matrix = Methods.getIntSquareMatrix();
        int n = matrix.size() / 2;
        if (matrix.size() == 1) {
            System.out.print("NO");
            return;
        }
        int counter;
        for (int i = matrix.size() - 1; i > 0; i--) {
            counter = 0;
            for (int j = 0; j < matrix.size() - i; j++) {
                if (matrix.get(i).get(j) % 2 == 0) {
                    counter++;
                }
            }
            if (counter >= n) {
                System.out.println("YES");
                return;
            }
        }
        for (int i = 0; i < matrix.size(); i++) {
            counter = 0;
            for (int j = 0; j < matrix.size() - i; j++) {
                if (matrix.get(i).get(j) % 2 == 0) {
                    counter++;
                }
            }
            if (counter >= n) {
                System.out.println("YES");
                return;
            }
        }
        for (int j = 0; j < matrix.size(); j++) {
            counter = 0;
            for (int i = 0, k = j; i <= j; i++, k--) {
                if (matrix.get(k).get(j) % 2 == 0) {
                    counter++;
                }
            }
            if (counter >= n) {
                System.out.println("YES");
                return;
            }
        }
        for (int i = 0, j = n - 1; i < matrix.size(); i++) {
            counter = 0;
            for (int k = i; i <= j; i++, k--) {
                if (matrix.get(i).get(k) % 2 == 0) {
                    counter++;
                }
            }
            if (counter >= n) {
                System.out.println("YES");
                return;
            }
        }
        System.out.println("NO");
    }
}
