package OtherWorks.Exams.CrudTask.crud;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Crud<T extends Printable & Comparable<T>> {
    ArrayList<T> elements = new ArrayList<>();

    public void create(T item) throws IllegalArgumentException {
        for (T element : elements) {
            if (element.compareTo(item) == 0) {
                throw new IllegalArgumentException();
            }
        }
        elements.add(item);
    }

    public T read(int id) throws NoSuchElementException {
        if (id >= elements.size()) {
            throw new NoSuchElementException();
        }
        return elements.get(id);
    }

    public void update(T item) throws NoSuchElementException {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).compareTo(item) == 0) {
                elements.set(i, item);
                return;
            }
        }
        throw new NoSuchElementException();
    }

    public void delete(T item) throws NoSuchElementException {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).compareTo(item) == 0) {
                elements.remove(i);
                return;
            }
        }
        throw new NoSuchElementException();
    }

    public int getCount() {
        return elements.size();
    }

    public void printAll() {
        elements.forEach(Printable::print);
    }
}
