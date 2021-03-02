package Homeworks.SecondSemester.Month03.HomeworkTo08;

import Methods.Methods;

import java.util.Stack;

public class PolishCalc {
    public static void main(String[] args) {
        int first, second;
        Stack<Integer> stack = new Stack<>();
        StringBuilder numberBuilder = new StringBuilder();
        String data = Methods.getLine(); // May be done as stream data processing using file input
        data = data + " ";
// 2 3 4 * +
        for (char c : data.toCharArray()) {
            if (c >= '0' && c <= '9') { // Digit
                numberBuilder.append(c);
            } else if (c == ' ' && !numberBuilder.toString().equals("")) { // Space after number
                stack.push(Integer.parseInt(numberBuilder.toString()));
                numberBuilder = new StringBuilder();
            } else if (c != ' ') { // Sign
                second = stack.pop();
                first = stack.pop();
                switch (c) {
                    case '+':
                        stack.push(first + second);
                        break;
                    case '-':
                        stack.push(first - second);
                        break;
                    case '*':
                        stack.push(first * second);
                        break;
                    case '/':
                        stack.push(first / second);
                        break;
                }
            }
        }
        System.out.println("Answer is: " + stack.pop());
    }
}
