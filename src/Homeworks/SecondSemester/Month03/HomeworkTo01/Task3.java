package Homeworks.SecondSemester.Month03.HomeworkTo01;

import java.util.Iterator;

public class Task3 {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }

        System.out.println(list.size());
        list.display();
        System.out.println(list.get(4));
        list.remove(0);
        list.remove(list.size() - 1);
        list.remove(3);
        list.display();

        for(Integer item : list) {
            System.out.print(item + " ");
        }
    }
}

class LinkedList<T> implements Iterable<T> {
    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
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

    class ListIterator implements Iterator<T> {
        Node<T> temp = head;

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
    }

    private Node<T> head;
    private Node<T> end;

    void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (head == null) {
            head = newNode;
        } else {
            end.next = newNode;
        }
        end = newNode;
    }

    T get(int ind) throws IllegalArgumentException {
        if(ind >= size())  {
            throw new IllegalArgumentException();
        }
        int c = 0;
        Node<T> temp = head;
        while (c != ind) {
            c++;
            temp = temp.next;
        }
        return temp.item;
    }

    void remove(int ind) throws IllegalArgumentException {
        if(ind >= size())  {
            throw new IllegalArgumentException();
        }
        if (ind == 0) {
            head = head.next;
            return;
        }

        int c = 0;
        Node<T> temp = head;
        while (c != ind - 1) {
            c++;
            temp = temp.next;
        }

        if (ind == size() - 1) {
            temp.next = null;
            end = temp;
        } else {
            temp.next = temp.next.next;
        }
    }

    int size() {
        int c = 0;
        Node<T> temp = head;
        while (temp != null) {
            c++;
            temp = temp.next;
        }
        return c;
    }

    void display() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.print(temp.item + " ");
            temp = temp.next;
        }
        System.out.println();
    }
}
