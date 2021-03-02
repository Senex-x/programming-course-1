package Homeworks.SecondSemester.Month03.HomeworkTo01;

public class Task2 {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i+1);
        }

        for(int item : stack) {
            System.out.print(item + " ");
        }
    }
}
