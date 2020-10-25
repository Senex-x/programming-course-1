package HomeworkTo1809;

import java.util.*;

public class TasksFrom1to4 {
    private static final Scanner sc = new Scanner(System.in);

    private static int fact(int n) {
        if (n == 1) return 1;
        return fact(n - 1) * n;
    }

    private static String[] permutations(final int n) {
        int fact = fact(n);
        int min, ind = 0, buf;
        String[] perm = new String[fact];
        String prev;
        for (int i = 1; i <= n; i++)
            perm[0] = perm[0].concat(Integer.toString(i));

        for (int i = 1; i < fact; i++) { // all the permutations
            for (int j = n - 2; j >= 0; j--) { // exact permutation
                min = Integer.MAX_VALUE;
                prev = perm[i - 1];
                for (int k = j + 1; k < n; k++) { // exact element's permutation
                    int current = prev.charAt(k) - '0';
                    if (current < min && prev.charAt(j) < current) { // less than minimal in the row, but bigger than current
                        ind = k; // index of element to exchange to
                    }
                }
                buf = prev.charAt(j);
                // prev.charAt(j) = prev.charAt(ind);
                // remake with char[]

            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Task 4 \n" +
                "Enter dimensions of matrix: ");
        int h = sc.nextInt();
        int f = sc.nextInt();

        LinkedList<ArrayList<Integer>> matrix = new LinkedList<>();
        System.out.println("Enter " + h * f + " elements of matrix:");
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < f; j++) {
                if (i == 0) matrix.add(new ArrayList<>());
                matrix.get(j).add(sc.nextInt());
            }
        }

        matrix.sort((o1, o2) -> {
            for (int i = 0; i < o1.size(); i++) {
                if (o1.get(i) > o2.get(i)) return 1;
                if (o1.get(i) < o2.get(i)) return -1;
            }
            return 0;
        });

        System.out.println("Resulting matrix:");
        for (int i = 0; i < matrix.get(0).size(); i++) {
            for (int j = 0; j < matrix.size(); j++) {
                System.out.print(matrix.get(j).get(i) + " ");
            }
            System.out.println();
        }


        System.out.println("Task 3 \n" +
                "Enter dimension of matrix: ");
        int n = sc.nextInt();
        int def = 0;
        int[][] x = input(n, n);
        String[] perm = new String[]{
                "123",
                "132",
                "312",
                "213",
                "231",
                "321"
        };

        if (n == 2) {
            def = x[0][0] * x[1][1] - x[0][1] * x[1][0];
        } else {
            for (int i = 0; i < 6; i++) {
                if (i % 2 == 0) { // +
                    def += x[0][perm[i].charAt(0) - 49] * x[1][perm[i].charAt(1) - 49] * x[2][perm[i].charAt(2) - 49];
                } else { // -
                    def -= x[0][perm[i].charAt(0) - 49] * x[1][perm[i].charAt(1) - 49] * x[2][perm[i].charAt(2) - 49];
                }
            }
        }

        System.out.println(def);

        System.out.println("\nTask 2");
        String s = sc.nextLine();
        int counter = 0;
        boolean checker = true;
        if (s.length() == 1 && s.charAt(0) != ' ') {
            System.out.println("Number of words is: 1");
            checker = false;
        }

        for (int i = 1; i < s.length(); i++)
            if ((s.charAt(i) == ' ' && s.charAt(i - 1) != ' ') || (i == s.length() - 1 && s.charAt(i) != ' '))
                counter++;

        if (checker) System.out.println("Number of words is: " + counter);


        System.out.println("\nTask 1 \nEnter dimensions of first matrix: ");
        n = sc.nextInt();
        int m = sc.nextInt();
        int[][] a = input(n, m);

        System.out.println("Enter dimensions of second matrix: ");
        f = sc.nextInt();
        int k = sc.nextInt();
        int[][] b = input(f, k);

        int[][] c = new int[n][k];

        if (m != f) {
            System.out.println("Multiplication is impossible, matrices are not proper");
        } else {
            for (int i = 0; i <n; i++) {
                for (int j = 0; j < k; j++) {
                    for (int g = 0; g < m; g++ ) {
                        c[i][j] += a[i][g] * b[g][j];
                    }
                }
            }
        }
        output(c);
    }

    private static int[][] input(int n, int m) {
        int[][] a = new int[n][m];
        System.out.println("Enter " + n * m + " elements of matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = sc.nextInt();
            }
        }
        return a;
    }

    private static void output(int[][] a) {
        System.out.println("Resulting matrix:");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
}

