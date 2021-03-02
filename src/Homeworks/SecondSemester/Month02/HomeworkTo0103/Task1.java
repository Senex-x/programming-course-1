package Homeworks.SecondSemester.Month02.HomeworkTo0103;

import Methods.Methods;

import java.util.Iterator;

public class Task1 {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < 10; i++) {
            stack.push(i+1);
        }

        stack.displayAll();

        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }
}

class Stack<T> implements Iterable<T> {

    abstract class StackIterator implements Iterator<T> {

    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator() {
            Node<T> temp = head.next;

            @Override
            public boolean hasNext() {
                return temp != null;
            }

            @Override
            public T next() {
                T item = temp.item;
                temp = temp.next;
                return item;
            }
        };
    }

    private static class Node<T> {
        T item;
        Node<T> next;

        Node() {
        }

        Node(T item) {
            this.item = item;
        }

        Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }

        boolean isEnd() {
            return item == null;
        }
    }

    private Node<T> head = new Node<>();

    void push(T item) {
        Node<T> newNode = new Node<>(item);
        if (head.next != null) {
            newNode.next = head.next;
        }
        head.next = newNode;
    }

    T pop() throws IllegalStateException {
        if (head.next == null) {
            throw new IllegalStateException();
        }
        T item = head.next.item;
        if (head.next.next != null) {
            head.next = head.next.next;
        } else {
            head.next = null;
        }
        return item;
    }

    T peek() throws IllegalStateException {
        if(head.next == null) throw new IllegalStateException();
        return head.next.item;
    }

    void displayAll() {
        Node<T> temp = head;
        if (temp.next == null) {
            return;
        }
        temp = temp.next;
        while (temp != null) {
            System.out.print(temp.item + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    boolean isEmpty() {
        return head.next == null;
    }
}



