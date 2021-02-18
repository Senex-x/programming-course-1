package Homeworks.FirstSemester.Month10.HomeworkTo16;

import Methods.Methods;

import java.util.ArrayList;

public class Task5 {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> matrix = Methods.getIntMatrix();
        matrix = transposition(matrix);
        Methods.displayMatrix(matrix);
    }

    private static ArrayList<ArrayList<Integer>> transposition(ArrayList<ArrayList<Integer>> matrix) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        int n = matrix.size();
        int m = matrix.get(0).size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i==0) {
                    result.add(new ArrayList<>());
                }
                result.get(j).add(matrix.get(i).get(j));
            }
        }
        return result;
    }
}

/*
2 3
1 2 3
4 5 6
 */
