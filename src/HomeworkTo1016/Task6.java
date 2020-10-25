package HomeworkTo1016;

import Methods.Methods;

import java.util.ArrayList;

public class Task6 {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> first = Methods.getIntMatrix();
        ArrayList<ArrayList<Integer>> second = Methods.getIntMatrix(first.size(), first.get(0).size());
        Methods.outMatrix(summation(first, second));
    }

    private static ArrayList<ArrayList<Integer>> summation(ArrayList<ArrayList<Integer>> first,
                                                           ArrayList<ArrayList<Integer>> second) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int i = 0; i < first.size(); i++) {
            result.add(new ArrayList<>());
            for (int j = 0; j < first.get(0).size(); j++) {
                result.get(i).add(first.get(i).get(j) + second.get(i).get(j));
            }
        }
        return result;
    }
}
