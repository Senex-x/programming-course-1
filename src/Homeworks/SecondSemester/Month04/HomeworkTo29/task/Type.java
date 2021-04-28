package Homeworks.SecondSemester.Month04.HomeworkTo29.task;

import java.io.*;
import java.util.LinkedList;

public class Type {
    private final String name;
    private int count;
    LinkedList<Item> items = new LinkedList<>();

    public Type(String name) {
        this.name = name;
        count = 0;
    }

    public void addItem() {
        items.add(new Item(IdHandler.getId(), name));
        count++;
    }

    public Item getItem() throws IllegalStateException {
        if (!items.isEmpty()) {
            Item item = items.get(0);
            deleteItem(item.getId());
            return item;
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isItemFound(int id) {
        for (Item i : items) {
            if (i.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void deleteItem(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
                count--;
                return;
            }
        }
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public static void resetId() {
        IdHandler.updateId(0);
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Type {'" + name + '\'' +
                ", items counter: " + count +
                ", items: " + items + "}";
    }

    static class IdHandler {
        private static final String PATH_ID = "src/Homeworks/SecondSemester/Month04/HomeworkTo29/task/data/id.txt";

        static int getId() {
            return createId();
        }

        static int createId() {
            int id = -1;
            try (BufferedReader reader = new BufferedReader(new FileReader(PATH_ID))) {
                id = Integer.parseInt(reader.readLine()) + 1;
                updateId(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return id;
        }

        static void updateId(int id) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_ID))) {
                writer.write(Integer.toString(id));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static final int ID_LENGTH = 3;

        static String formatId(int id) {
            StringBuilder formattedId = new StringBuilder();
            for (int i = 0; i < ID_LENGTH - Integer.toString(id).length(); i++) {
                formattedId.append(0);
            }
            formattedId.append(id);
            return formattedId.toString();
        }
    }
}