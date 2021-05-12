package Homeworks.SecondSemester.Month04.HomeworkTo29.task;

import Methods.Methods;

import java.util.ArrayList;

class Test implements Listener {
    private static final Storage storage = new Storage();

    public static void main(String[] args) {
        String[] names = {"Bread", "Cookie", "Butter"};
        storage.addListener(new Test());
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

    @Override
    public void onUpdate() {
        storage.display();
        Methods.line("-");
        System.out.println("Amount: " + storage.getAmount() +
                "\nType name with biggest amount of items: " +
                storage.getTypeWithBiggestAmountOfItems().getName());

    }
}

public class Storage {
    private final ArrayList<Listener> listeners = new ArrayList<>();
    private final ArrayList<Type> types = new ArrayList<>();

    public int getAmount() {
        int amount = 0;
        for(Type t : types) {
            amount += t.getCount();
        }
        return amount;
    }

    public Type getTypeWithBiggestAmountOfItems() {
        int maxAmount = -1;
        Type type = null;
        for(Type t : types) {
            if (maxAmount < t.getCount()) {
                type = t;
            }
        }
        return type;
    }

    public Storage() {
        Type.resetId();
    }

    public void addItem(String name) {
        for (Type t : types) {
            // Such type already exists
            if (t.getName().equalsIgnoreCase(name)) {
                t.addItem();
                Data.update(types);

                notifyAllListeners();
                return;
            }
        }

        createType(name);
        addItem(name);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    private void notifyAllListeners() {
        for (Listener l : listeners) {
            l.onUpdate();
        }
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

        notifyAllListeners();
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

