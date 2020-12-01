package OtherWorks.Solutions;

import java.util.*;

/*
Exam task solution
Doesn't have actual meaning
 */

public class Solution3 {
    public static int getI(int k, int m) {
        return k;
    }

    public static int getJ(int k, int m, int n) {
        return n - k - m - 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[][] a = new int[n][n];

        int m = sc.nextInt();
        for (int k = 0; k < n - m; k++) {
            a[getI(k, m)][getJ(k, m, n)] = k + 1;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
}

