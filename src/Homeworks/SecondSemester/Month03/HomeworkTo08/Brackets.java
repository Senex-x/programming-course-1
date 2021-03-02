package Homeworks.SecondSemester.Month03.HomeworkTo08;

import Methods.Methods;

import java.util.Stack;

public class Brackets {
    public static void main(String[] args) {
        String data = Methods.getLine();
        Stack<Character> stack = new Stack<>();

        for (char c : data.toCharArray()) {
            for(char e : stack) {
                System.out.print(e);
            }
            System.out.println();
            if (c == '(' || c == ')' ||
                    c == '[' || c == ']' ||
                    c == '{' || c == '}') {
                if(stack.empty()) {
                    stack.push(c);
                } else {
                    if(c - 1 == stack.peek() || c - 2 == stack.peek()) {
                        stack.pop();
                    } else {
                        stack.push(c);
                    }
                }
            }
        }
        if(stack.empty()) {
            System.out.println("Expression is ok");
        } else {
            System.out.println("Expression is incorrect");
        }
    }
}
