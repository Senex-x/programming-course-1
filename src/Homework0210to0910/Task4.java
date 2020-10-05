package Homework0210to0910;

import Methods.Methods;

import java.util.*;

/**
 * Task to check if in given square matrix
 * is n/2 or more even elements in it's
 * main diagonal (or i understood so)
 */

public class Task4 {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> matrix = Methods.inIntSquareMatrix();
        int n = matrix.size() / 2;
        for (int i = 0; i < matrix.size() && n != 0; i++) {
            if (matrix.get(i).get(i) % 2 == 0) {
                n--;
            }
        }
        if (n == 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
