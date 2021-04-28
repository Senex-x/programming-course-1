package Homeworks.SecondSemester.Month04.HomeworkTo29.task;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

class Test {
    public static void main(String[] args) {
        String[] names = {"Bread", "Cookie", "Butter"};
        Storage storage = new Storage();
        storage.createType("Milk");
        storage.addItem("Milk");
        storage.deleteItem(storage.getItem("milk"));
        storage.getItem("milk");
        for (int j = 0; j < names.length; j++)
            for (int i = 0; i < 10; i++) {
                storage.addItem(names[j]);
            }
        storage.display();
    }
}

public class Storage {
    private final ArrayList<Type> types = new ArrayList<>();

    public Storage() {
        Type.resetId();
    }

    public void addItem(String name) {
        for (Type t : types) {
            // Such type already exists
            if (t.getName().equalsIgnoreCase(name)) {
                t.addItem();
                Data.update(types);
                return;
            }
        }

        createType(name);
        addItem(name);
    }

    public Item getItem(String name) {
        for (Type t : types) {
            if (t.getName().equalsIgnoreCase(name)) {
                try {
                    return t.getItem();
                } catch (IllegalStateException e) {
                    System.err.println("No such item as " + name + " in storage exception");
                    return null;
                }

            }
        }
        System.err.println("No such item as " + name + " in storage exception");
        return null;
    }

    public void deleteItem(Item item) {
        deleteItem(item.getId());
    }

    public void deleteItem(int id) {
        for (Type t : types) {
            if (t.isItemFound(id)) {
                t.deleteItem(id);
            }
        }
        Data.update(types);
    }

    void createType(String name) {
        types.add(new Type(name));
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public void display() {
        System.out.println(toString());
    }

    public int getTypesCount() {
        return types.size();
    }

    @Override
    public String toString() {
        return "Storage: " + types;
    }


}

